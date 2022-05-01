package first_view.client;

import application_controller.BookingController;
import engineering.bean.SaloonBean;
import engineering.bean.ServiceBean;
import engineering.bean.TimeSlotBean;
import engineering.bean.UserBean;
import engineering.exception.IncorrectFormatException;
import engineering.exception.ServiceNotFoundException;
import first_view.general.InternalBackController;
import first_view.list_cell_factories.ServiceListCellFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleBookingGraphicController{

    @FXML
    private Button bookedButton;

    @FXML
    private ImageView saloonImage;

    @FXML
    private ListView<ServiceBean> serviceListView;
    @FXML
    private Label dateLabel;

     @FXML
     private Label hourLabelInit;
     @FXML
     private Label hourLabelFinal;

     @FXML
     private Label nameSaloonLabel;
     @FXML
     private Label citySaloonLabel;
     @FXML
     private Label addressSaloonLabel;
     @FXML
     private Label phoneSaloonLabel;


    private static final String CLIENT_BOOKED_SCREEN_NAME = "first_view/client/client_booked.fxml";
    private static final String CLIENT_SERVICE_ITEM ="first_view/list_item/client_service_item.fxml";
    private SaloonBean saloonInfo;
    private TimeSlotBean timeSlotInfo;
    private LocalDate dateBooking;
    @FXML
    private Label serviceNameLabel;

    @FXML
    private  Label servicePriceLabel;
    private List<ServiceBean> serviceBeans;
    private List<ServiceBean> serviceListSelected;

    public ScheduleBookingGraphicController(){
        saloonInfo = new SaloonBean();
        timeSlotInfo = new TimeSlotBean();
        serviceListSelected = new ArrayList<>();
        serviceBeans = new ArrayList<>();

    }




    @FXML
    void selectServiceOnListView(MouseEvent event) throws IncorrectFormatException {
      serviceBeans.add(serviceListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void onButtonSaloonClicked(ActionEvent event) throws IOException{
        Button sourceButton = (Button) event.getSource();
        FXMLLoader fxmlLoaderNode = new FXMLLoader(getClass().getClassLoader().getResource(CLIENT_BOOKED_SCREEN_NAME));
        Parent newCenterNode = fxmlLoaderNode.load();
        Scene myScene = sourceButton.getScene();
        BorderPane borderPane = (BorderPane) myScene.getRoot();
        borderPane.setCenter(newCenterNode);
        BookedGraphicController bookedGraphicController = fxmlLoaderNode.getController();
        bookedGraphicController.injectServicesList(serviceBeans);

        BookingController bookingController = new BookingController();

        UserBean userBean = InternalBackController.getInternalBackControllerInstance().getLoggedUser();
        Date date = Date.valueOf(dateBooking);
        bookingController.saveBooking(serviceListSelected,saloonInfo,userBean,timeSlotInfo,date);

    }

    public void injectServiceSaloon() throws ServiceNotFoundException {
        boolean firstView= false;
        serviceListView.setCellFactory(param -> new ServiceListCellFactory(CLIENT_SERVICE_ITEM,firstView));

        BookingController bookingController = new BookingController();
        List<ServiceBean> servicesSaloonList = bookingController.searchServices(saloonInfo);

        serviceListView.setItems(FXCollections.observableList(servicesSaloonList));

        phoneSaloonLabel.setText(saloonInfo.getPhone());
        nameSaloonLabel.setText(saloonInfo.getName());
        citySaloonLabel.setText(saloonInfo.getCity());
        addressSaloonLabel.setText(saloonInfo.getAddress());
        hourLabelInit.setText(String.valueOf(timeSlotInfo.getFromTime()));
        hourLabelFinal.setText(String.valueOf(timeSlotInfo.getToTime()));
        dateLabel.setText(String.valueOf(dateBooking));

        }


    public void displaySaloon(SaloonBean saloonBean, TimeSlotBean timeSlot, LocalDate date) {


        this.saloonInfo.setName(saloonBean.getName());
        this.saloonInfo.setAddress(saloonBean.getAddress());
        this.saloonInfo.setCity(saloonBean.getCity());
        this.saloonInfo.setPhone(saloonBean.getPhone());
        this.saloonInfo.setSeatNumber(saloonBean.getSeatNumber());
        this.timeSlotInfo.setFromTime(timeSlot.getFromTime());
        this.timeSlotInfo.setToTime(timeSlot.getToTime());
        this.dateBooking = date;
    }

}

