package test;

import application_controller.ManageServiceController;
import engineering.bean.ServiceBean;
import engineering.bean.UserBean;
import engineering.container.ServiceCatalogue;
import engineering.exception.DuplicatedServiceException;
import engineering.exception.IncorrectFormatException;
import engineering.exception.InsertNegativePriceException;
import model.Service;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class TestManageServiceController {
    /*@author Testing:  Alessandro Cortese
                        Matricola 0267355
    */

    private static final String SERVICE_TEST_NAME = "Taglio";
    private static final String SERVICE_TEST_DESCRIPTION = "Taglio dei capelli";
    private static final String SERVICE_TEST_NEW_DESCRIPTION = "Taglio della barba dopo il taglio dei capelli";
    private static final String BARBER_EMAIL_TEST = "barber";

    private ServiceBean firstServiceBean;
    private ServiceBean secondServiceBean;

    @Test
    public void testDeleteService(){

        /*

            All'interno del database esiste l'utente con email 'barber',
            l'intento del test è di verificare che se venisse chiesto di
            elimnare un servizio, tale servizio venga effettivamente
            eliminato.

         */


        UserBean userBean = new UserBean(BARBER_EMAIL_TEST);
        boolean flag = true;
        ManageServiceController manageServiceController = new ManageServiceController();
        ServiceCatalogue serviceCatalogue = new ServiceCatalogue();

        try{
            this.createServiceBean();
        }catch(IncorrectFormatException exception){
            flag = false;
        }

        List<Service> services;
        Service deletedService = new Service(SERVICE_TEST_NAME, SERVICE_TEST_NEW_DESCRIPTION, 9.0);

        secondServiceBean.setDescriptionInfo(SERVICE_TEST_NEW_DESCRIPTION);
        manageServiceController.deleteService(secondServiceBean, userBean);

        services = serviceCatalogue.getServices();

        for(Service service : services) {

            if(service.getServiceName().compareTo(deletedService.getServiceName()) == 0 && service.getServiceDescription().compareTo(deletedService.getServiceDescription()) == 0 && Objects.equals(service.getServicePrice(), deletedService.getServicePrice())) {
                flag = false;
            }

        }

        assertTrue(flag);
    }

    @Test
    public void testAddService(){

        /*

          All'interno del database esiste l'utente con email 'barber',
          l'intento del test è quello di controllare se il nuovo servizio
          venga effettivamente inserito.

         */

        UserBean barber = new UserBean(BARBER_EMAIL_TEST);
        boolean flag = true;
        ManageServiceController manageServiceController = new ManageServiceController();
        ServiceCatalogue serviceCatalogue = new ServiceCatalogue();
        Service newService = new Service(SERVICE_TEST_NAME, "Taglio della barba", 7.0);
        List<Service> serviceList;

        try {
            firstServiceBean = new ServiceBean(SERVICE_TEST_NAME, "Taglio della barba", "", "7.0");
        } catch (IncorrectFormatException e) {
            flag = false;
        }

        try {
            manageServiceController.addService(firstServiceBean, barber);
        } catch (DuplicatedServiceException | InsertNegativePriceException e) {
            flag = false;
        }

        serviceList = serviceCatalogue.getServices();

        for(Service service : serviceList) {

            if(service.getServiceName().compareTo(newService.getServiceName()) == 0 && service.getServiceDescription().compareTo(newService.getServiceDescription()) == 0 && Objects.equals(service.getServicePrice(), newService.getServicePrice())) {
                flag = false;
            }

        }

        assertTrue(flag);

    }

    @Test
    public void testModifyService() {

        /*

            All'interno del database esiste l'utente con email 'barber',
            l'intento del test è verificare che servizio venga effettivamente
            modificato.

         */

        UserBean barber = new UserBean(BARBER_EMAIL_TEST);
        boolean modifiedServiceFlag = true;
        ServiceCatalogue serviceCatalogue = new ServiceCatalogue();
        List<Service> services;
        try {
            this.createServiceBean();
        } catch (IncorrectFormatException e) {
            modifiedServiceFlag = false;
        }
        Service modifiedService = new Service(SERVICE_TEST_NAME, SERVICE_TEST_NEW_DESCRIPTION, 9.0);
        ManageServiceController manageServiceController = new ManageServiceController();


        try {
            secondServiceBean.setPriceInfo("9.0");
            secondServiceBean.setDescriptionInfo(SERVICE_TEST_NEW_DESCRIPTION);
        } catch (IncorrectFormatException e) {
            modifiedServiceFlag = false;
        }

        try {
            manageServiceController.modifyService(firstServiceBean, secondServiceBean, barber);
        } catch (InsertNegativePriceException e) {
            modifiedServiceFlag = false;
        }

        services = serviceCatalogue.getServices();

        for(Service service : services) {

            if(service.getServiceName().compareTo(modifiedService.getServiceName()) != 0 && service.getServiceDescription().compareTo(modifiedService.getServiceDescription()) != 0 && !Objects.equals(service.getServicePrice(), modifiedService.getServicePrice())) {

                modifiedServiceFlag = false;

            }

        }

        assertTrue(modifiedServiceFlag);
    }


    private void createServiceBean() throws IncorrectFormatException {

        firstServiceBean = new ServiceBean(SERVICE_TEST_NAME, SERVICE_TEST_DESCRIPTION, "", "10.0");
        secondServiceBean = new ServiceBean(SERVICE_TEST_NAME, SERVICE_TEST_DESCRIPTION, "", "10.0");

    }

}
