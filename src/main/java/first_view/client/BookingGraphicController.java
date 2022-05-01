package first_view.client;

import application_controller.BookingController;
import engineering.bean.SaloonBean;
import engineering.exception.SaloonNotFoundException;
import first_view.general.InternalBackController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;

public class BookingGraphicController{


    public static final String CLIENT_TAKE_SALOON_LIST_SCREEN_NAME = "first_view/client/client_take_saloon.fxml";
    private static final String CLIENT_DATE_HOUR = "first_view/client/client_appointment_Hour&Date.fxml";

    @FXML
    private Button saloonButton;

    @FXML
    private Button saloonNextButton;

    @FXML
    private TextField searchSaloonName;

    @FXML
    private TextField searchCity;

    @FXML
    private Label labelSaloonItem;


    private Parent newCenterNode;
    private Button sourceButton;
    private FXMLLoader fxmlLoader;


    @FXML
    public void onButtonSaloonCityClicked(ActionEvent event) throws IOException, SaloonNotFoundException {


        sourceButton = (Button) event.getSource();
        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(CLIENT_TAKE_SALOON_LIST_SCREEN_NAME));
        String saloonCity = searchCity.getText();

        InternalBackController.getInternalBackControllerInstance().onNextScreen(sourceButton);

        newCenterNode = fxmlLoader.load();
        Scene myScene = sourceButton.getScene();
        BorderPane borderPane = (BorderPane) myScene.getRoot();
        borderPane.setCenter(newCenterNode);
        SaloonBean saloonBean = new SaloonBean(false, saloonCity);
        BookingController bookingController = new BookingController();

        List<SaloonBean> saloonBeanList = bookingController.searchByCitySaloon(saloonBean);
       ClientListViewController clientListViewController;
       clientListViewController = fxmlLoader.getController();
       clientListViewController.injectSaloonList(saloonBeanList);


    }


    @FXML
    public void onButtonSaloonClicked(ActionEvent actionEvent) throws IOException {
        newCenterNode = null;
        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(CLIENT_DATE_HOUR));
        sourceButton = (Button) actionEvent.getSource();
        Scene myScene = sourceButton.getScene();
        BorderPane borderPane = (BorderPane) myScene.getRoot();
        newCenterNode = fxmlLoader.load();
        borderPane.setCenter(newCenterNode);



        if(sourceButton == saloonNextButton){

            String saloon = searchSaloonName.getText();

            SaloonBean saloonBean = new SaloonBean(saloon);


            BookingController bookingController = new BookingController();
            bookingController.searchByNameSaloon(saloonBean);


        }

        if (sourceButton == saloonButton) {
            String saloonName = labelSaloonItem.getText();

            SaloonBean saloonBean = new SaloonBean(true, saloonName);

            BookingController bookingController = new BookingController();
            SaloonBean saloonBeanName = bookingController.searchByNameSaloon(saloonBean); //ritona una saloonBean
            BookingDateHourGraphicController bookingDateHourGraphicController = fxmlLoader.getController();
            bookingDateHourGraphicController.display(saloonBeanName);
            bookingDateHourGraphicController.injectSaloonIntoDateHour();


        }
    }
}