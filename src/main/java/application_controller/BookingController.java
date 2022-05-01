package application_controller;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

import engineering.bean.*;
import engineering.dao.BookingDAO;
import engineering.dao.CustomerDAO;
import engineering.dao.SaloonDAO;
import engineering.dao.ServiceDAO;
import engineering.exception.SaloonNotFoundException;
import engineering.exception.ServiceNotFoundException;
import engineering.time.ScheduleTime;
import engineering.time.TimeSlot;
import javafx.scene.control.Alert;
import model.Booking;
import model.Customer;
import model.Saloon;
import model.Service;

public class BookingController {

    private Saloon saloon;

    public List<SaloonBean> searchByCitySaloon(SaloonBean saloonBean) throws SaloonNotFoundException {

        List<SaloonBean> saloonBeanList = new ArrayList<>();
        String saloonCity = saloonBean.getCity();
        SaloonDAO saloonDAO = new SaloonDAO();

        List<Saloon> listSaloon = saloonDAO.retrieveByCityName(saloonCity);

        for (Saloon saloonItem: listSaloon){
            SaloonBean saloonBean2 = new SaloonBean();
            saloonBean2.setName(saloonItem.getName());
            saloonBean2.setAddress(saloonItem.getAddress());
            saloonBean2.setCity(saloonItem.getCity());
            saloonBean2.setPhone(saloonItem.getPhone());
            saloonBean2.setSeatNumber(saloonItem.getSeatNumber());
            saloonBean2.setSlotTime(saloonItem.getSlotTime());
            saloonBeanList.add(saloonBean2);
        }
        return saloonBeanList;
    }


    public SaloonBean searchByNameSaloon(SaloonBean saloonBean){

        SaloonDAO saloonDAO = new SaloonDAO();
        Saloon saloonByName = saloonDAO.retrieveByNameOfSaloon(saloonBean.getName());

        saloonBean.setName(saloonByName.getName());
        saloonBean.setCity(saloonByName.getCity());
        saloonBean.setAddress(saloonByName.getAddress());
        saloonBean.setPhone(saloonByName.getPhone());


        return  saloonBean;

    }

    public List<TimeSlotBean> searchTimeSlots(SaloonBean saloonBean) {
        List<TimeSlotBean> saloonBeanTimeSlots;
        SaloonDAO saloonDAO = new SaloonDAO();
        String saloonName = saloonBean.getName();

        try {

            saloon = saloonDAO.retrieveTimeSlots(saloonName);

        } catch (Exception e) {
            e.printStackTrace();

        }
        List<TimeSlot> saloonTimeSlots= new ScheduleTime(saloon).createSlotTime();
        saloonBeanTimeSlots = new ArrayList<>();
        for (TimeSlot timeSlot : saloonTimeSlots){
            TimeSlotBean timeSlotBean = new TimeSlotBean();
            timeSlotBean.setFromTime(timeSlot.getFromTime());
            timeSlotBean.setToTime(timeSlot.getToTime());
            timeSlotBean.setSeatAvailable(timeSlot.getSeatAvailable());
            saloonBeanTimeSlots.add(timeSlotBean);
        }

        return saloonBeanTimeSlots;
    }

    public List<ServiceBean> searchServices(SaloonBean saloonBean) throws ServiceNotFoundException {
        ServiceDAO serviceDAO = new ServiceDAO();
        List<ServiceBean> servicesBeanList = new ArrayList<>();
        List<Service> servicesList = serviceDAO.retrieveService(saloonBean.getName());
        for (Service service: servicesList){
            try {
                ServiceBean serviceBean = new ServiceBean();
                serviceBean.setNameInfo(service.getServiceName());
                serviceBean.setDescriptionInfo(service.getServiceDescription());
                serviceBean.setPriceInfo(String.valueOf(service.getServicePrice()));
                servicesBeanList.add(serviceBean);
            } catch (Exception e) {
                throw new ServiceNotFoundException(e.getMessage());
            }

        }

        return servicesBeanList;
    }

    public boolean saveBooking(List<ServiceBean> serviceListSelected, SaloonBean saloonInfo, UserBean userBean, TimeSlotBean timeSlotBean, Date date) {
        String userEmail = userBean.getUserEmail();
        String[] services = new String[serviceListSelected.size()];
        TimeSlot timeSlot = new TimeSlot();
        List<Service> serviceList = new ArrayList<>();
        for (int i=0; i< serviceListSelected.size();i++){
            Service service = new Service();
            service.setServiceName(serviceListSelected.get(i).getNameInfo());
            service.setServiceDescription(serviceListSelected.get(i).getDescriptionInfo());
            service.setServicePrice(serviceListSelected.get(i).getPriceInfo());
            serviceList.add(service);
        }
        Time fromTime = timeSlotBean.getFromTime();
        Time toTime= timeSlotBean.getToTime();
        timeSlot.setFromTime(fromTime);
        timeSlot.setToTime(toTime);
        timeSlot.setSeatAvailable(timeSlotBean.getSeatAvailable());
        
        for (int i=0; i< serviceListSelected.size(); i++){
            services[i] = serviceListSelected.get(i).getNameInfo();
        }
        CustomerDAO customerDAO = new CustomerDAO();
        BookingDAO bookingDAO = new BookingDAO();
        Customer customer = customerDAO.retrieveInfoCustomer(userEmail);
        new Booking(customer,saloon,serviceList,timeSlot) ;
        bookingDAO.insertBooking(userEmail, saloonInfo.getName(), services, fromTime, toTime, date);

        return false;
    }

    public boolean checkDateHour(BookingBean bookingBean) throws  SaloonNotFoundException {
        SaloonDAO saloonDAO = new SaloonDAO();
        boolean result = saloonDAO.checkDateSaloon(bookingBean.getSaloonName() , String.valueOf(bookingBean.getDate()));
        if (result){
            String mess="data non disponibile, il Salone è chiuso!";
            throw new SaloonNotFoundException(mess);
        }


        return result;
    }

}
