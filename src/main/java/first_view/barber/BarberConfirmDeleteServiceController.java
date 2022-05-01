package first_view.barber;

import application_controller.ManageServiceController;
import engineering.bean.ServiceBean;
import first_view.general.InternalBackController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class BarberConfirmDeleteServiceController {

    @FXML private TextField nameDeleteServiceTextField;
    @FXML private TextField priceDeleteServiceTextField;
    @FXML private TextField descriptionDeleteServiceTextField;
    @FXML private TextField nameOfServiceToDeleteTextField;
    @FXML private Button deleteConfirmButton;

    private ServiceBean serviceBeanToDelete;


    @FXML
    public void onButtonClicked(ActionEvent event) throws IOException {

        Node deleteEvent = (Node) event.getSource();

        if(deleteEvent == deleteConfirmButton) {

            ManageServiceController manageServiceController = new ManageServiceController();

            manageServiceController.deleteService(serviceBeanToDelete, InternalBackController.getInternalBackControllerInstance().getLoggedUser());

            InternalBackController.getInternalBackControllerInstance().backToHome(deleteEvent);
        }

    }

    public void displayServiceToDelete(ServiceBean serviceBean) {

        this.serviceBeanToDelete = serviceBean;

        nameDeleteServiceTextField.setText(serviceBean.getNameInfo());
        priceDeleteServiceTextField.setText(Double.toString(serviceBean.getPriceInfo()));
        descriptionDeleteServiceTextField.setText(serviceBean.getDescriptionInfo());
        nameOfServiceToDeleteTextField.setText(serviceBean.getNameOfUsedProductInfo());

    }



}
