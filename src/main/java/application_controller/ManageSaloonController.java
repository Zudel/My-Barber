package application_controller;

import engineering.bean.SaloonBean;
import engineering.bean.UserBean;
import engineering.container.SaloonCatalogue;
import engineering.dao.SaloonDAO;
import model.Saloon;

import java.util.ArrayList;
import java.util.List;

public class ManageSaloonController {

    private UserBean userBean;

    public void setUserBean(UserBean userBean) {

        this.userBean = userBean;

    }

    public List<SaloonBean> getAllSaloon() {

        SaloonDAO saloonDAO = new SaloonDAO();
        saloonDAO.setBarberEmail(userBean.getUserEmail());

        SaloonCatalogue saloonCatalogue = saloonDAO.loadAllSaloon();
        List<Saloon> saloons = saloonCatalogue.getSaloonList();

        ArrayList<SaloonBean> saloonBeanArrayList = new ArrayList<>();

        for(Saloon saloon : saloons) {

            saloonBeanArrayList.add(new SaloonBean(saloon.getName(), saloon.getPhone(), saloon.getAddress(), saloon.getCity()));

        }

        return  saloonBeanArrayList;

    }

}
