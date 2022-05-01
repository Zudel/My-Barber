package engineering.bean;

public class UserBean {

    private String userEmail ;
    private String name;
    private String surname;
    private String pass;
    private Integer userType ;

    public UserBean(){}

    public UserBean(String userEmail, Integer userType) {
        setUserEmail(userEmail);
        setUserType(userType);

    }

    public UserBean(String userEmail){
        setUserEmail(userEmail);

    }

    public String getUserEmail() {

        return userEmail;

    }

    public void setUserEmail(String userEmail) {

        this.userEmail = userEmail;

    }

    public Integer getUserType() {

        return userType;

    }

    public void setUserType(Integer userType) {

        this.userType = userType;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}

