package second_view.client;

import application_controller.BookingController;
import engineering.bean.BookingBean;
import engineering.bean.SaloonBean;
import engineering.bean.TimeSlotBean;
import engineering.exception.InvalidIndexSelected;
import engineering.exception.SaloonNotFoundException;
import engineering.time.TimeSlot;
import first_view.list_cell_factories.SaloonTimeSlotsListCellFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import second_view.general.ScreenChanger;

import java.io.IOException;
import java.sql.Date;
import java.util.List;


public class ClientBookingDateHourGraphicContr{

    @FXML
    private TextField commandLine;

    @FXML
    private Button enterButton;

    @FXML
    private Label initTime;
    @FXML
    private Label finalTime;
    @FXML
    private Label indexLabel;
    @FXML
    private Label seatNumberLabel;

    @FXML
    private ListView<TimeSlotBean> hourListView;

    @FXML
    private TextField slotTimeField;

    @FXML
    private TextField dateField;

    @FXML
    private  TextArea descriptionTextArea;

    @FXML
    private TextField saloonName;

    private SaloonBean saloonBean;
    private TimeSlotBean timeSlotInfo;
    private String date;

    @FXML
    void onCommand(ActionEvent event) throws IOException {
        String command = commandLine.getText() ;
        commandLine.setStyle(null);
        commandLine.setText("");
        try {

            if (command.compareTo("back") == 0) {
                ScreenChanger.getInstance().onBack(event);
                return;
            } else if (command.matches("set date \\d{4}-\\d{2}-\\d{2}")) {
                String dateB = command.replace("set date ", "");
                dateField.setText(dateB);
                return;
            } else if (command.matches("select slot time [0-9]+")) {
                String index = command.replace("select slot time ", "");
                int in = Integer.parseInt(index);
                verifyIndexSlotTime(in);
                slotTimeField.setText(index);
                return;

            } else if (command.compareTo("confirm") == 0) { //manda una notifica al controllore per ottenere le informazioni necessarie
                date = dateField.getText();
                if(date.isEmpty())
                    throw new SaloonNotFoundException("Inserire la data correttamente ");
                Date dateBook = Date.valueOf(date);

                BookingController bookingController = new BookingController();
                BookingBean bookingBean = new BookingBean(saloonBean.getName(), dateBook);

                bookingController.checkDateHour(bookingBean);
                String message = "conferma effettuta";
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
                alert.showAndWait();
                return;
            } else if (command.compareTo("continue booking") == 0) {
                ClientBookAppointmentController clientBookAppointmentController = (ClientBookAppointmentController) ScreenChanger.getInstance().changeScreen(event, ScreenChanger.CLIENT_BOOK_APPOINTMENT_SCREEN);
                clientBookAppointmentController.injectDateHourSaloonInfo(timeSlotInfo,saloonBean,date);
                return;
            }
        } catch (InvalidIndexSelected e) {
            e.printStackTrace();
        } catch (SaloonNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        commandLine.setStyle("-fx-border-color: red");
    }

    private void verifyIndexSlotTime(int ind) throws InvalidIndexSelected {
        boolean flag = false;

            if (ind <= hourListView.getItems().size()) {
                flag = true;
                timeSlotInfo = new TimeSlotBean();
                TimeSlotBean timeSlotBean = hourListView.getItems().get(ind);
                timeSlotInfo.setFromTime(timeSlotBean.getFromTime());
                timeSlotInfo.setToTime(timeSlotBean.getToTime());
                timeSlotInfo.setSeatAvailable(timeSlotBean.getSeatAvailable());

            }
        if (!flag)
            throw new InvalidIndexSelected("slot time non valido");
    }

    public void injectSaloonInfo(SaloonBean saloonBeanInfo) {
        saloonBean = new SaloonBean();
        saloonBean.setName(saloonBeanInfo.getName());
        saloonBean.setCity(saloonBeanInfo.getCity());
        saloonBean.setAddress(saloonBeanInfo.getAddress());
        saloonBean.setPhone(saloonBeanInfo.getPhone());

        hourListView.setCellFactory(param -> new SaloonTimeSlotsListCellFactory(false));
        SaloonBean saloonBean2 = new SaloonBean(saloonBeanInfo.getName());
        saloonName.setText(saloonBeanInfo.getName());

        BookingController bookingController = new BookingController();
        List<TimeSlotBean> timeSlotSaloon = bookingController.searchTimeSlots(saloonBean2);

        hourListView.setItems(FXCollections.observableList(timeSlotSaloon));

    }



}

