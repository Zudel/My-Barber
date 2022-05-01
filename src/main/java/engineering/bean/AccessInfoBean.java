package engineering.bean;

public class AccessInfoBean {

    private String userEmail ;
    private String userPassword ;

    public AccessInfoBean(String userEmail, String userPassword) {
        setUserEmail(userEmail);
        setUserPassword(userPassword);
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
