package boundary;

import engineering.bean.ServiceBean;
import engineering.bean.UserBean;
import engineering.pattern.observer.Observer;

import java.io.*;
import java.util.List;

public class ManageServiceBoundarySendEmail implements Observer {

    private ServiceBean serviceBean;
    private String barberEmail;
    private List<UserBean> userBeans;

    public ManageServiceBoundarySendEmail(ServiceBean serviceBean, String barberEmail){

        this.serviceBean = serviceBean;
        this.serviceBean.attach(this);
        this.barberEmail = barberEmail;

    }

    @Override
    public void update()  {

        try(PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter( "EmailAddressFile.txt")))){

            printWriter.print("Barber mail address:    ");
            printWriter.println(barberEmail);
            printWriter.println("");
            printWriter.print("New service added:   ");
            printWriter.println(serviceBean.getNameInfo());
            printWriter.println("");
            printWriter.println("Send to customers' mail address:");
            printWriter.println("");

            for(UserBean userBean : userBeans) {

                printWriter.println(userBean.getUserEmail());

            }


        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setUserBeans(List<UserBean> userBeans) {

        this.userBeans = userBeans;

    }

    public List<UserBean> getUserBeans() {

        return userBeans;

    }

    public ServiceBean getServiceBean() {

        return serviceBean;

    }

    public void setServiceBean(ServiceBean serviceBean) {

        this.serviceBean = serviceBean;

    }

}