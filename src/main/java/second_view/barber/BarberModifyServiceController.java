package second_view.barber;

import application_controller.ManageServiceController;
import engineering.bean.ServiceBean;
import engineering.exception.IncorrectFormatException;
import engineering.exception.InsertNegativePriceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import second_view.general.ScreenChanger;


public class BarberModifyServiceController {

    @FXML private TextField modifyServiceCommandLine;
    @FXML private TextField modifyServicePriceTextField;
    @FXML private TextField modifyServiceDescriptionTextField;
    @FXML private TextField modifyServiceNameOfUsedProductTextField;
    @FXML private TextField modifyServiceNameTextField;
    @FXML private Label modifyServiceExceptionLabel;

    private static final String OVERWRITE_COMMAND = "overwrite";
    private static final String OVERWRITE_NAME_COMMAND = "overwrite name";
    private static final String OVERWRITE_PRICE_COMMAND = "overwrite price";
    private static final String OVERWRITE_DESCRIPTION_COMMAND = "overwrite description";
    private static final String OVERWRITE_NAME_OF_USED_PRODUCT_COMMAND = "overwrite product";

    private ServiceBean serviceBeanToModify;


    @FXML
    public void onCommand(ActionEvent event) {

        String modifyService = modifyServiceCommandLine.getText();
        modifyServiceCommandLine.setText("");
        modifyServiceCommandLine.setStyle(null);
        modifyServiceExceptionLabel.setText("");

        if(modifyService.compareTo("back") == 0) {
            ScreenChanger.getInstance().onBack(event);
            return;
        }
        else if(modifyService.startsWith(OVERWRITE_COMMAND)) {

            if(handlerOverwriteCommand(modifyService)) {

                return;

            }

        }
        else if(modifyService.compareTo("modify") == 0) {

            boolean go = true;
            ManageServiceController manageServiceController = new ManageServiceController();
            ServiceBean updateServiceBean;

            try {

                updateServiceBean = new ServiceBean(modifyServiceNameTextField.getText(), modifyServiceDescriptionTextField.getText(), modifyServiceNameOfUsedProductTextField.getText(), modifyServicePriceTextField.getText());
                manageServiceController.modifyService(serviceBeanToModify, updateServiceBean, ScreenChanger.getInstance().getLoggedUser());

            } catch (IncorrectFormatException e) {

                modifyServiceExceptionLabel.setText("Incorrect insert price!");
                modifyServicePriceTextField.setText("");
                go = false;

            } catch (InsertNegativePriceException exception) {

                modifyServiceExceptionLabel.setText("Insert price is negative!");
                modifyServicePriceTextField.setText("");
                go = false;

            }

            if(go){
                ScreenChanger.getInstance().goToHome(event);
            }

        }


        modifyServiceCommandLine.setStyle("-fx-border-color: red");
    }

    public void setServiceBeanToModify(ServiceBean serviceBeanToModify) {

        this.serviceBeanToModify = serviceBeanToModify;
        modifyServiceNameTextField.setText(this.serviceBeanToModify.getNameInfo());
        modifyServiceDescriptionTextField.setText(this.serviceBeanToModify.getDescriptionInfo());
        modifyServicePriceTextField.setText(String.valueOf(this.serviceBeanToModify.getPriceInfo()));
        modifyServiceNameOfUsedProductTextField.setText(this.serviceBeanToModify.getNameOfUsedProductInfo());

    }

    private boolean handlerOverwriteCommand(String modifyService) {

        boolean flag = false;
        String newField;

        if(modifyService.startsWith(OVERWRITE_NAME_COMMAND)) {
            newField = modifyService.replace(OVERWRITE_NAME_COMMAND + " ", "");
            modifyServiceNameTextField.setText(newField);
            flag = true;
        }
        else if(modifyService.startsWith(OVERWRITE_PRICE_COMMAND)){
            newField = modifyService.replace(OVERWRITE_PRICE_COMMAND + " ", "");
            modifyServicePriceTextField.setText(newField);
            flag = true;
        }
        else if(modifyService.startsWith(OVERWRITE_DESCRIPTION_COMMAND)) {
            newField = modifyService.replace(OVERWRITE_DESCRIPTION_COMMAND + " ", "");
            modifyServiceDescriptionTextField.setText(newField);
            flag = true;
        }
        else if(modifyService.startsWith(OVERWRITE_NAME_OF_USED_PRODUCT_COMMAND)) {
            newField = modifyService.replace(OVERWRITE_NAME_OF_USED_PRODUCT_COMMAND + " ", "");
            modifyServiceNameOfUsedProductTextField.setText(newField);
            flag = true;
        }


        return flag;
    }

}