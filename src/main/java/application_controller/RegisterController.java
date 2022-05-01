package application_controller;

import engineering.bean.UserBean;
import engineering.dao.BarberDAO;
import engineering.dao.CustomerDAO;
import engineering.exception.DuplicatedUserException;
import engineering.exception.InvalidCredentialsException;
import engineering.pattern.factory.Factory;
import first_view.general.InternalBackController;
import javafx.scene.control.Alert;
import model.User;

public class RegisterController {

    Factory factory;

    public RegisterController(){
        factory = new Factory();

    }

    public void register(UserBean userBean) throws InvalidCredentialsException, DuplicatedUserException {
        User customer;
        User barber ;
        int type = userBean.getUserType();
        String name = userBean.getName();
        String surname = userBean.getSurname();
        String email = userBean.getUserEmail();
        String pass = userBean.getPass();
        if(type == 1) {
            barber = factory.createBarber(name, surname, email, pass);
            BarberDAO barberDAO = new BarberDAO();
            barberDAO.insertBarber(barber);

        }
        else {
            customer = factory.createCustomer(name, surname, email, pass);
            CustomerDAO customerDAO = new CustomerDAO();
            customerDAO.insertCustomer(customer);
        }
    }



}
