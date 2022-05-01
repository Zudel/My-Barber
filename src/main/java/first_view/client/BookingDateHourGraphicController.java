package first_view.client;

import application_controller.BookingController;
import engineering.bean.BookingBean;
import engineering.bean.SaloonBean;
import engineering.bean.TimeSlotBean;
import engineering.exception.SaloonNotFoundException;
import engineering.exception.ServiceNotFoundException;
import first_view.general.InternalBackController;
import first_view.list_cell_factories.SaloonTimeSlotsListCellFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class BookingDateHourGraphicController {

        private static final String CLIENT_SALOON_CENTER_SCREEN_NAME = "first_view/client/client_saloon_center.fxml";

        private TimeSlotBean timeSlotBean;

        @FXML
        private Button confirmDateHourButton;

        @FXML
        private Label exceptionDateLabel;

        @FXML
        private Label exceptionHourLabel;

        @FXML
        private DatePicker dateBooking;

        @FXML
        private Label  saloonNameLabel;


        @FXML
        private ImageView saloonImage;

        @FXML
        private ListView<TimeSlotBean> timeSlotListView;
        @FXML
        private Label hourLabel;

        private List<TimeSlotBean> timeSlotSaloon;

        @FXML
        private Label initTime;
        @FXML
        private Label finalTime;
        @FXML
        private Label seatNumberLabel;

        private SaloonBean timeSlotSaloonInfo;
        private LocalDate date;
        public BookingDateHourGraphicController(){
                timeSlotListView = new ListView<>();
        }

        @FXML
        void onButtonClicked(ActionEvent event) throws IOException, ServiceNotFoundException {
                Button sourceButton = (Button) event.getSource();
                InternalBackController.getInternalBackControllerInstance().onNextScreen(sourceButton);
                FXMLLoader fxmlLoaderNode = new FXMLLoader(getClass().getClassLoader().getResource(CLIENT_SALOON_CENTER_SCREEN_NAME));
                Parent newCenterNode = fxmlLoaderNode.load();
                Scene myScene = sourceButton.getScene();
                BorderPane borderPane = (BorderPane) myScene.getRoot();
                borderPane.setCenter(newCenterNode);


                ScheduleBookingGraphicController scheduleBookingGraphicController = fxmlLoaderNode.getController();

                scheduleBookingGraphicController.displaySaloon(timeSlotSaloonInfo, timeSlotBean,date );
                scheduleBookingGraphicController.injectServiceSaloon();
        }
        

        @FXML
        public void confirmHour(ActionEvent event) {
                date = dateBooking.getValue();
                if(date == null){
                        Alert alert= new Alert(Alert.AlertType.ERROR,"insert a valid date!");
                        alert.showAndWait();
                        return;
                }
                String date1 = String.valueOf(date);
                if(date1.isEmpty())
                        try {
                                throw new SaloonNotFoundException("Insert a valid date!");
                        } catch (SaloonNotFoundException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                                alert.showAndWait();
                                return;
                        }

                Date dateBook = Date.valueOf(date);

                BookingController bookingController = new BookingController();
                BookingBean bookingBean = new BookingBean(timeSlotSaloonInfo.getName(), dateBook);

                try {
                        bookingController.checkDateHour(bookingBean);
                        String message = "confirmed";
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
                        alert.showAndWait();
                } catch (SaloonNotFoundException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                        alert.showAndWait();
                }
        }



        public void display(SaloonBean saloonBean) {
                timeSlotSaloonInfo = new SaloonBean();

                this.timeSlotSaloonInfo.setName(saloonBean.getName());
                this.timeSlotSaloonInfo.setCity(saloonBean.getCity());
                this.timeSlotSaloonInfo.setPhone(saloonBean.getPhone());
                this.timeSlotSaloonInfo.setAddress(saloonBean.getAddress());
        }

        public void injectSaloonIntoDateHour() {
                timeSlotListView.setCellFactory(param -> new SaloonTimeSlotsListCellFactory(true));

                searchTimeSlots();

                timeSlotListView.setItems(FXCollections.observableList(timeSlotSaloon));

        }

        private void searchTimeSlots() {
                SaloonBean saloonBean = new SaloonBean(timeSlotSaloonInfo.getName());
                saloonNameLabel.setText(timeSlotSaloonInfo.getName());

                BookingController bookingController = new BookingController();
                timeSlotSaloon = bookingController.searchTimeSlots(saloonBean);//FAR RITORNARE PURE GLI I SEAT TIME
        }

        @FXML
        public void slotTimeSelected(){
                timeSlotBean = timeSlotListView.getSelectionModel().getSelectedItem();
        }

}

