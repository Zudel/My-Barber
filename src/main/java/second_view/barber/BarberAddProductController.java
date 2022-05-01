package second_view.barber;

import application_controller.ManageProductController;
import engineering.bean.UserBean;
import engineering.bean.ProductBean;
import engineering.exception.DuplicatedProductException;
import engineering.exception.InvalidInsertProductException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import second_view.general.ScreenChanger;

import java.util.Objects;

import static engineering.other_classes.NumericVerify.isNumeric;

public class BarberAddProductController {

    public BarberAddProductController() {

        goToHomeFlag = true;

    }

    @FXML private TextField addProductCommandLine;
    @FXML private TextField nameAddProductField;
    @FXML private TextField descriptionAddProductField;
    @FXML private TextField addProductPriceField;
    @FXML private Label addProductExceptionLabelSecondView;

    private static final String SET_PRODUCT_NAME_COMMAND = "set name";
    private static final String SET_PRODUCT_DESCRIPTION_COMMAND = "set description";
    private static final String SET_PRICE_PRODUCT_COMMAND = "set price";

    private boolean goToHomeFlag;

    @FXML
    public void onCommand(ActionEvent event) {

        String addProductCommand = addProductCommandLine.getText();
        ManageProductController addProductController;
        addProductCommandLine.setText("");
        addProductCommandLine.setStyle(null);
        addProductExceptionLabelSecondView.setText("");

        if(addProductCommand.compareTo("back") == 0) {

            ScreenChanger.getInstance().onBack(event);
            return;

        }
        else if(addProductCommand.startsWith("set")) {

            if(manageSetCommand(addProductCommand)){
                return;
            }

        }
        else if(addProductCommand.compareTo("add") == 0 && controlValueField()) {

            ProductBean productBean = new ProductBean(-1, nameAddProductField.getText(), descriptionAddProductField.getText(), Double.parseDouble(addProductPriceField.getText()));
            UserBean userBean = ScreenChanger.getInstance().getLoggedUser();
            addProductController = new ManageProductController();

            try {

                addProductController.addProduct(productBean, userBean);

            } catch (InvalidInsertProductException e) {

                addProductExceptionLabelSecondView.setText("Invalid insert product");
                goToHomeFlag = false;

            } catch (DuplicatedProductException e) {

                addProductExceptionLabelSecondView.setText("Insert product already exist!");
                goToHomeFlag = false;

            }

            if(goToHomeFlag) {

                ScreenChanger.getInstance().goToHome(event);
                return;

            }

        }

        addProductCommandLine.setStyle("-fx-border-color: red");
    }

    private boolean manageSetCommand(String addProductCommand) {

        String newField;

        if(addProductCommand.startsWith(SET_PRODUCT_NAME_COMMAND)) {

            newField = addProductCommand.replace(SET_PRODUCT_NAME_COMMAND + " ", "");
            nameAddProductField.setText(newField);
            return true;

        }
        else if(addProductCommand.startsWith(SET_PRODUCT_DESCRIPTION_COMMAND)) {

            newField = addProductCommand.replace(SET_PRODUCT_DESCRIPTION_COMMAND + " ", "");
            descriptionAddProductField.setText(newField);
            return true;

        }
        else if(addProductCommand.startsWith(SET_PRICE_PRODUCT_COMMAND)) {

            newField = addProductCommand.replace(SET_PRICE_PRODUCT_COMMAND + " ", "");
            if(newField.compareTo(SET_PRICE_PRODUCT_COMMAND) != 0 &&isNumeric(newField)) {

                addProductPriceField.setText(newField);
                return true;

            }
            else addProductExceptionLabelSecondView.setText("Incorrect insert price!");

        }

        return false;

    }

    private boolean controlValueField() {

        return (nameAddProductField.getText() != null && !Objects.equals(nameAddProductField.getText(), "")) &&
                (descriptionAddProductField.getText() != null && !Objects.equals(descriptionAddProductField.getText(), "")) &&
                (addProductPriceField.getText() != null && !Objects.equals(addProductPriceField.getText(), "0.00"));

    }


}
