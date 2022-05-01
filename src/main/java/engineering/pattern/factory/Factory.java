package engineering.pattern.factory;

import model.Barber;
import model.Customer;
import model.User;

public class Factory {


    public User createCustomer(String name, String surname, String email, String pass) {
        return new Customer(name, surname, email,pass);
    }

    public User createBarber(String name, String surname, String email, String pass) {
        return new Barber(email, pass, name, surname);
    }
}
