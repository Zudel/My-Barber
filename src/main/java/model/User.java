package model;

import java.io.Serializable;

public abstract class User implements Serializable {
    private  String email;
    private  String pass;
    private String name;
    private String surname;

    protected User(String email, String pass, String name, String surname){
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.surname = surname;
    }

    protected User() {}

    protected User(String cName, String cSurname) {
        this.name = cName;
        this.surname = cSurname;
    }


    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public String getSurname(){
        return this.surname;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPass(){
        return pass;
    }
    public void setPass(String pass){
        this.pass = pass;
    }

}
