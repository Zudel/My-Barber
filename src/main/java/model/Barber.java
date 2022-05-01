package model;

import java.io.Serializable;

public class Barber extends User implements Serializable {

    public Barber(String email, String pass, String name, String surname) {
        super(email, pass, name, surname);
    }

}
