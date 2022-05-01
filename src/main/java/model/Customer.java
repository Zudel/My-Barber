package model;

import engineering.bean.UserBean;

import java.io.Serializable;

public class Customer extends User implements Serializable {

    private String phone;
    private Integer cardPoints ;


    public Customer(String email, String pass, String name, String surname, String phone){

        super(email, pass, name, surname);
        setPhone(phone);

    }

    public Customer(String email, String pass, String name, String surname, String phone, Integer cardPoints){
        super(email, pass, name, surname);
        setPhone(phone);
        setCardPoints(cardPoints);

    }

    public Customer(String email, String pass, String name, String surname, Integer cardPoints) {

        super(email, pass, name, surname);
        setCardPoints(cardPoints);

    }

    public Customer() {
        super();
    }

    public Customer(String name, String surname, String email, String pass) {
        super(email, pass, name, surname);
        setCardPoints(0);
        setPhone("");
    }

    public Customer(String cName, String cSurname) {
        super(cName,cSurname);
    }


    public String getPhone() {

        return phone;

    }

    public void setPhone(String phone) {

        this.phone = phone;

    }

    public Integer getCardPoints() {

        return cardPoints;

    }

    public void setCardPoints(Integer cardPoints) {
        this.cardPoints = cardPoints;

    }


}
