package first_view.barber;

import application_controller.BarberSeeAppointmentsController;
import engineering.bean.BookingBean;
import engineering.exception.BookingNotFoundExcption;
import engineering.exception.SaloonNotFoundException;
import first_view.list_cell_factories.BarberAppointmentsListCellFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.util.List;

public class BarberListAppointmentsController {

    @FXML private ListView<BookingBean> appointmentsListView;
    @FXML private TextField saloonText;
    @FXML private Button confirmButton;
    @FXML private DatePicker date;






    @FXML
    public void onConfirm(ActionEvent event){
        appointmentsListView.setCellFactory(param -> new BarberAppointmentsListCellFactory());
        List<BookingBean> bookingBeanList;
        BarberSeeAppointmentsController barberSeeAppointmentsController = new BarberSeeAppointmentsController();
        String saloonName = saloonText.getText();
        Date dateBook = Date.valueOf(date.getValue());
        BookingBean bookingBean = new BookingBean(dateBook,saloonName);


        try {
            bookingBeanList = barberSeeAppointmentsController.retrieveAppointment(bookingBean);
        } catch (BookingNotFoundExcption  e) {
            showException(e.getMessage());
            return;
        } catch (SaloonNotFoundException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, ex.getMessage());
            alert.showAndWait();
            return;
        }
        appointmentsListView.setItems(FXCollections.observableList(bookingBeanList));

    }
    private void showException(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.showAndWait();
    }




}

