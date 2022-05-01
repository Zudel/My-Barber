package application_controller;


import boundary.ManageServiceBoundarySendEmail;
import engineering.bean.ServiceBean;
import engineering.bean.UserBean;
import engineering.container.ServiceCatalogue;
import engineering.dao.*;
import engineering.exception.DuplicatedServiceException;
import engineering.exception.IncorrectFormatException;
import engineering.exception.InsertNegativePriceException;
import engineering.exception.ProductNotFoundException;
import model.Barber;
import model.Customer;
import model.Service;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageServiceController {


    public List<ServiceBean> getAllService(UserBean userBean) throws IncorrectFormatException {

        ServiceDAO serviceDAO = new ServiceDAO();
        BarberDAO barberDAO = new BarberDAO();
        Barber barber = barberDAO.loadBarber(userBean.getUserEmail());
        ServiceCatalogue catalogueService = serviceDAO.loadAllServiceByBarber(barber.getEmail());
        List<Service> services = catalogueService.getServices();
        String nameOfUsedProduct;
        ArrayList<ServiceBean> serviceBeanList = new ArrayList<>();

        for (Service service : services){

            if (service.getServiceUsedProduct() != null) {
                nameOfUsedProduct = service.getServiceUsedProduct().getName();
            } else {
                nameOfUsedProduct = "";
            }

            serviceBeanList.add(new ServiceBean(service.getServiceName(), service.getServiceDescription(), nameOfUsedProduct, String.valueOf(service.getServicePrice())));
        }

        return serviceBeanList;

    }

    public void deleteService(ServiceBean serviceBeanToDelete, UserBean userBean) {

        ServiceDAO serviceDAO = new ServiceDAO();
        ProductDAO productDAO;
        BarberDAO barberDAO = new BarberDAO();
        int serviceKey;
        Product product = null;

        Barber barber = barberDAO.loadBarber(userBean.getUserEmail());

        if(!serviceBeanToDelete.getNameOfUsedProductInfo().equals("")) {

            productDAO = new ProductDAO();

            try {

                product = productDAO.loadProductByName(serviceBeanToDelete.getNameOfUsedProductInfo(), barber.getEmail());


            } catch (ProductNotFoundException e) {

                product = null;

            }

        }

        serviceKey = serviceDAO.loadServiceId(serviceBeanToDelete.getNameInfo(), barber.getEmail());

        if(product != null) {

            serviceDAO.deleteServiceProduct(serviceKey, product.getIsbn());

        }

        serviceDAO.deleteService(serviceKey, barber.getEmail());

    }

    public void addService(ServiceBean serviceBean, UserBean userBean) throws DuplicatedServiceException, InsertNegativePriceException {

        SaloonDAO saloonDAO = new SaloonDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        ProductDAO productDAO = new ProductDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        BarberDAO barberDAO = new BarberDAO();

        this.controlServiceBean(serviceBean);

        Product localProduct;
        Service newService;
        Barber barber = barberDAO.loadBarber(userBean.getUserEmail());

        int serviceKey;

        ManageServiceBoundarySendEmail addServiceBoundarySendEmail = new ManageServiceBoundarySendEmail(serviceBean, userBean.getUserEmail());
        ServiceCatalogue serviceCatalogue;


        Integer saloonId = saloonDAO.loadIdOfSaloon(userBean.getUserEmail());

        List<UserBean> userBeans = new ArrayList<>();
        List<Customer> customers;

        customers = customerDAO.loadCustomerFromFavoriteSaloon(saloonId);
        serviceCatalogue = serviceDAO.loadAllServiceByBarber(barber.getEmail());

        try{

            localProduct = productDAO.loadProductByName(serviceBean.getNameOfUsedProductInfo(), barber.getEmail());

        }catch (ProductNotFoundException e){

            localProduct = null;
            serviceBean.setNameOfUsedProductInfo("");

        }

        for(Customer customer : customers) {

            userBeans.add(new UserBean(customer.getEmail()));

        }

        newService = new Service(serviceBean.getNameInfo(), serviceBean.getDescriptionInfo(), serviceBean.getPriceInfo(), localProduct);
        newService.setBarber(barber);

        for(Service service : serviceCatalogue.getServices()){

            if(controlDuplicatedService(service, newService)){

                throw new DuplicatedServiceException("Servizio già esistente!");

            }

        }

        serviceKey = serviceDAO.insertService(newService, barber.getEmail()) ;

        if(!(serviceBean.getNameOfUsedProductInfo().equals("")) && localProduct != null) {

            serviceDAO.insertServiceProduct(serviceKey, localProduct.getIsbn());

        }

        if(serviceKey != -1){

            serviceCatalogue.addService(newService);

            addServiceBoundarySendEmail.setUserBeans(userBeans);
            serviceBean.notifyChanges();

        }

    }

    public void modifyService(ServiceBean oldServiceBean, ServiceBean updateServiceBean, UserBean userBean) throws InsertNegativePriceException {

        this.controlServiceBean(updateServiceBean);

        ServiceDAO serviceDAO = new ServiceDAO();
        ProductDAO productDAO = new ProductDAO();
        BarberDAO barberDAO = new BarberDAO();
        Product product;
        int serviceKey;
        boolean modify;

        Barber barber = barberDAO.loadBarber(userBean.getUserEmail());
        serviceKey = serviceDAO.loadServiceId(oldServiceBean.getNameInfo(), barber.getEmail());


        modify = !this.controlDuplicatedService(oldServiceBean, updateServiceBean);

        
        if(modify) {

            serviceDAO.updateService(serviceKey, updateServiceBean.getNameInfo(), updateServiceBean.getDescriptionInfo(), updateServiceBean.getPriceInfo());

        }

        if(oldServiceBean.getNameOfUsedProductInfo().compareTo("") != 0 && updateServiceBean.getNameOfUsedProductInfo().compareTo("") == 0 && modify) {
            try {

                product = productDAO.loadProductByName(oldServiceBean.getNameOfUsedProductInfo(), barber.getEmail());
                serviceDAO.deleteServiceProduct(serviceKey, product.getIsbn());

            } catch (ProductNotFoundException e) {
                e.printStackTrace();
            }

        }
        else if(oldServiceBean.getNameOfUsedProductInfo().compareTo("") == 0 && updateServiceBean.getNameOfUsedProductInfo().compareTo("") != 0 && modify) {

            try {

                product = productDAO.loadProductByName(updateServiceBean.getNameOfUsedProductInfo(), barber.getEmail());

            } catch (ProductNotFoundException e) {
                product = null;
            }

            if(product != null) {
                serviceDAO.insertServiceProduct(serviceKey, product.getIsbn());
            }

        }
        else if(oldServiceBean.getNameOfUsedProductInfo().compareTo(updateServiceBean.getNameOfUsedProductInfo()) != 0) {

            this.updateServiceProduct(productDAO, serviceDAO, serviceKey, updateServiceBean, barber);
           
        }

    }
    
    private void updateServiceProduct(ProductDAO productDAO, ServiceDAO serviceDAO, int serviceKey, ServiceBean serviceBean, Barber barber){

        Product localProduct;

        try {

            localProduct = productDAO.loadProductByName(serviceBean.getNameOfUsedProductInfo(), barber.getEmail());

        } catch (ProductNotFoundException e) {
            localProduct = null;
        }

        if(localProduct != null) {
            serviceDAO.updateServiceProduct(serviceKey, localProduct.getIsbn());
        }

    }

    private void controlServiceBean(ServiceBean serviceBean) throws InsertNegativePriceException {

        if(serviceBean.getPriceInfo() <= 0.0){
            throw new InsertNegativePriceException();
        }

    }

    private boolean controlDuplicatedService(Service localService, Service service) {
        return Objects.equals(localService.getServiceName(), service.getServiceName()) && Objects.equals(localService.getServiceDescription(), service.getServiceDescription()) &&
                Objects.equals(localService.getServicePrice(), service.getServicePrice()) && Objects.equals(localService.getServiceUsedProduct(), service.getServiceUsedProduct());
    }

    private boolean controlDuplicatedService(ServiceBean localService, ServiceBean service) {
        return Objects.equals(localService.getNameInfo(), service.getNameInfo()) && Objects.equals(localService.getDescriptionInfo(), service.getDescriptionInfo()) &&
                Objects.equals(localService.getPriceInfo(), service.getPriceInfo()) && Objects.equals(localService.getNameOfUsedProductInfo(), service.getNameOfUsedProductInfo());
    }

}
