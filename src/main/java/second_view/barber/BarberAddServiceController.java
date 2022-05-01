package second_view.barber;

import application_controller.ManageServiceController;
import engineering.bean.ServiceBean;
import engineering.exception.DuplicatedServiceException;
import engineering.exception.IncorrectFormatException;
import engineering.exception.InsertNegativePriceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import second_view.general.ScreenChanger;
import java.io.IOException;
import java.util.Objects;


public class BarberAddServiceController {

    public BarberAddServiceController(){

        goBackToHome = true;

    }

    @FXML private TextField addServiceCommandLine;
    @FXML private TextField nameAddServiceField;
    @FXML private TextField descriptionAddServiceField;
    @FXML private TextField addServicePriceField;
    @FXML private TextField addServiceUsedProductNameField;
    @FXML private Label addServiceExceptionLabelSecondView;

    private static final String SET_NAME_COMMAND = "set name" ;
    private static final String SET_DESCRIPTION_COMMAND = "set description" ;
    private static final String SET_PRICE_COMMAND = "set price" ;
    private static final String SET_USED_PRODUCT_COMMAND = "set product used" ;

    private boolean goBackToHome;
    private boolean invalidField;

    @FXML
    public void onCommand(ActionEvent event) throws IOException {

        String addServiceCommand = addServiceCommandLine.getText();
        addServiceExceptionLabelSecondView.setText("");
        addServiceCommandLine.setText("");
        addServiceCommandLine.setStyle(null);

        if(addServiceCommand.compareTo("back") == 0) {

            ScreenChanger.getInstance().onBack(event) ;
            return ;

        }

        else if(addServiceCommand.startsWith("set")){

            if(handlerSetCommand(addServiceCommand)){

                return ;

            }

        }

        else if(addServiceCommand.compareTo("add") == 0 && getterTex()){

            try {

                ServiceBean serviceBean = new ServiceBean(nameAddServiceField.getText(), descriptionAddServiceField.getText(), addServiceUsedProductNameField.getText(), addServicePriceField.getText());

                ManageServiceController manageServiceController = new ManageServiceController();
                manageServiceController.addService(serviceBean, ScreenChanger.getInstance().getLoggedUser());

            } catch (DuplicatedServiceException e) {

                addServiceExceptionLabelSecondView.setText("This service already exist!");
                goBackToHome = false;

            } catch (IncorrectFormatException e) {

                addServiceExceptionLabelSecondView.setText("Invalid insert price!");
                goBackToHome = false;
                addServicePriceField.setText("");

            } catch (InsertNegativePriceException exception) {

                addServiceExceptionLabelSecondView.setText("Insert negative price!");
                goBackToHome = false;
                addServicePriceField.setText("");

            }

            if(goBackToHome) {

                ScreenChanger.getInstance().goToHome(event);

            }

            return ;

        }

        if(invalidField) {

            addServiceExceptionLabelSecondView.setText("Invalid entered values!");

        }

        addServiceCommandLine.setStyle("-fx-border-color: red");

    }

    private boolean getterTex(){

        boolean flag = !Objects.equals(nameAddServiceField.getText(), "") && !Objects.equals(addServicePriceField.getText(), "") && !Objects.equals(descriptionAddServiceField.getText(), "");
        invalidField = true;
        return flag;

    }

    private boolean handlerSetCommand(String insertCommand){

        String addServiceNewField;
        boolean flag = false;

        if(insertCommand.startsWith(SET_NAME_COMMAND)) {
            addServiceNewField = insertCommand.replace(SET_NAME_COMMAND + " ", "") ;
            if (addServiceNewField.compareTo(SET_NAME_COMMAND) != 0){
                nameAddServiceField.setText(addServiceNewField) ;
                flag =  true;
            }
        }
        else if(insertCommand.startsWith(SET_DESCRIPTION_COMMAND)) {
            addServiceNewField = insertCommand.replace(SET_DESCRIPTION_COMMAND + " ", "") ;
            if(addServiceNewField.compareTo(SET_DESCRIPTION_COMMAND) != 0){
                descriptionAddServiceField.setText(addServiceNewField) ;
                flag =  true;
            }
        }
        else if(insertCommand.startsWith(SET_PRICE_COMMAND)) {
            addServiceNewField = insertCommand.replace(SET_PRICE_COMMAND + " ", "") ;
            if(addServiceNewField.compareTo(SET_PRICE_COMMAND) != 0){
                addServicePriceField.setText(addServiceNewField);
                flag =  true;
            }
        }
        else if(insertCommand.startsWith(SET_USED_PRODUCT_COMMAND)) {
            addServiceNewField = insertCommand.replace(SET_USED_PRODUCT_COMMAND + " ", "");
            if(addServiceNewField.compareTo(SET_USED_PRODUCT_COMMAND) != 0) {
                addServiceUsedProductNameField.setText(addServiceNewField);
                flag =  true;
            }
        }

        return flag;

    }

}
