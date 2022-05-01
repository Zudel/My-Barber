package second_view.barber;

import application_controller.BarberSeeAppointmentsController;
import engineering.bean.BookingBean;
import engineering.exception.BookingNotFoundExcption;
import engineering.exception.SaloonNotFoundException;
import first_view.list_cell_factories.BarberAppointmentsListCellFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import second_view.general.ScreenChanger;

import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;


public class BarberViewAppointmentsController {

    private static final String SELECT_DATE_COMMAND = "select date";
    private static final String SELECT_SALOON_COMMAND = "select saloon";
    private  BookingBean bookingBean;

    @FXML private TextField viewAppointmentsCommandLine;
    @FXML private TextField saloonNameFieldViewAppointments;
    @FXML private TextField dateTextField;
    @FXML private ListView<BookingBean> appointmentListView;


    private DatePicker datePickerViewAppointments;

    public BarberViewAppointmentsController(){
        bookingBean = new BookingBean();
    }

    @FXML
    public void onCommand(ActionEvent event) {


        String viewAppointmentsCommand = viewAppointmentsCommandLine.getText();
        viewAppointmentsCommandLine.setText("");
        viewAppointmentsCommandLine.setStyle(null);

        if(viewAppointmentsCommand.compareTo("back") == 0) {
            ScreenChanger.getInstance().onBack(event);
            return ;
        }
        else if(viewAppointmentsCommand.startsWith("select")) {
            if(selectCommand(viewAppointmentsCommand))
                return ;
        }

        else if(viewAppointmentsCommand.compareTo("confirm") == 0) {
            appointmentListView.setCellFactory(param -> new BarberAppointmentsListCellFactory());
            BarberSeeAppointmentsController barberSeeAppointmentsController = new BarberSeeAppointmentsController();
            List<BookingBean> bookingBeanList;
            try {
                bookingBeanList = barberSeeAppointmentsController.retrieveAppointment(bookingBean);
            } catch (SaloonNotFoundException e) {
                showException(e.getMessage());
                return;
            } catch (BookingNotFoundExcption e) {
                showException(e.getMessage());

                return;
            }

            appointmentListView.setItems(FXCollections.observableList(bookingBeanList));


            return ;

        }

        viewAppointmentsCommandLine.setStyle("-fx-border-color: red");

    }

    private void showException(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.showAndWait();
    }


    private boolean selectCommand(String insertCommand){

        String viewAppointmentsField;

        boolean handled = false;

        if(insertCommand.startsWith(SELECT_SALOON_COMMAND)) {

            viewAppointmentsField = insertCommand.replace(SELECT_SALOON_COMMAND + " ", "");
            saloonNameFieldViewAppointments.setText(viewAppointmentsField);
            bookingBean.setSaloonName(viewAppointmentsField);


            handled = true;

        }
        else if (insertCommand.startsWith(SELECT_DATE_COMMAND)) {

            datePickerViewAppointments = new DatePicker();
            viewAppointmentsField = insertCommand.replace(SELECT_DATE_COMMAND + " ", "");
            if (manageInsertDate(viewAppointmentsField)) {
                handled = true;
                LocalDate date = datePickerViewAppointments.getValue();
                dateTextField.setText(date.toString());
                bookingBean.setDate(Date.valueOf(date));
            }

        }

        return handled;

    }

    private boolean manageInsertDate(String date) {

        boolean dateHandled = false;
        String[] valueOfInsertDate;
        valueOfInsertDate = date.split("-");

        try{

            datePickerViewAppointments.setValue(LocalDate.of(Integer.parseInt(valueOfInsertDate[0]), Integer.parseInt(valueOfInsertDate[1]), Integer.parseInt(valueOfInsertDate[2])));
            dateHandled = true;

        }catch (DateTimeException e){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid entered Date!" );
            alert.showAndWait();

        }

        return dateHandled;

    }
}