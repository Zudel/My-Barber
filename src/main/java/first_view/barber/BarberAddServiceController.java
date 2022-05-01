package first_view.barber;

import engineering.bean.ServiceBean;
import engineering.exception.IncorrectFormatException;
import first_view.general.InternalBackController;
import first_view.pickers.PricePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class BarberAddServiceController {

    private static final String BARBER_CONFIRM_ADD_SERVICE_SCREEN_NAME = "first_view/barber/barber_confirm_add_service.fxml" ;

    @FXML private Button continueButton ;
    @FXML private TextField priceTextField ;
    @FXML private TextField nameAddServiceTextField ;
    @FXML private TextField descriptionTextFiledAddService ;
    @FXML private TextField nameOfUsedProductTextField ;
    @FXML private Label exceptionAddServiceLabel;

    @FXML
    public void onButtonClicked(ActionEvent event) throws IOException {
        exceptionAddServiceLabel.setText("");
        Node sourceNode = (Node) event.getSource();
        BarberConfirmAddServiceController barberConfirmAddServiceController;


        if (sourceNode == continueButton) {
            InternalBackController.getInternalBackControllerInstance().onNextScreen(sourceNode);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(BARBER_CONFIRM_ADD_SERVICE_SCREEN_NAME));
            BorderPane myBorderPane = (BorderPane) sourceNode.getScene().getRoot();
            myBorderPane.setCenter(fxmlLoader.load());
            barberConfirmAddServiceController = fxmlLoader.getController();

            try {

                ServiceBean serviceBean = new ServiceBean(nameAddServiceTextField.getText(),
                        descriptionTextFiledAddService.getText(),
                        nameOfUsedProductTextField.getText(),
                        priceTextField.getText());


                barberConfirmAddServiceController.display(serviceBean);

            }catch (IncorrectFormatException | NumberFormatException e) {
                exceptionAddServiceLabel.setText("Prezzo inserito non valido!");
            }

        }
    }

    @FXML
    public void onPricePicked(MouseEvent event) throws IOException {
        TextField sourceTextField = (TextField) event.getSource();
        if(sourceTextField == priceTextField){
            PricePicker pricePicker = new PricePicker(0, 0.0);
            priceTextField.setText(pricePicker.getPrice());
        }
    }

}
