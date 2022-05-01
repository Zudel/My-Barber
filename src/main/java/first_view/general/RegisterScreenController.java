package first_view.general;

import application_controller.RegisterController;
import engineering.bean.UserBean;
import engineering.exception.DuplicatedUserException;
import engineering.exception.InvalidCredentialsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import static first_view.general.EnterAsUserTypeController.BARBER_TYPE;
import static first_view.general.EnterAsUserTypeController.CLIENT_TYPE;

public class RegisterScreenController {



    public static final String CLIENT_MENU_SCREEN_NAME = "first_view/client/client_menu.fxml";
    public static final String BARBER_MENU_SCREEN_NAME = "first_view/barber/barber_menu.fxml"; //con final indico la variabile è costante

    @FXML
    private Button registerButton;

    @FXML
    private ToggleGroup userTypeRadioGroup;

    @FXML
    private RadioButton clientTypeRadioButton;

    @FXML
    private RadioButton barberTypeRadioButton;
    @FXML
    private TextField nameText;
    @FXML
    private TextField surnameText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField passText;
    @FXML
    private TextField confirmPassText;
    @FXML
    private Button googleButton;
    @FXML
    private Button facebookButton;
    private int userType;



    @FXML
    public void onBackClicked(ActionEvent event) {
        BackController.getInstance().onBackClick((Node)event.getSource());
    }


    @FXML
    public void onButtonClicked(ActionEvent event) throws  IOException {
        Node sourceNode = (Node)event.getSource() ;
        UserBean userBean;

        try {
            userBean = retrieveInfo();
            RegisterController registerController = new RegisterController();
            registerController.register(userBean);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "registration successful") ;
            alert.showAndWait() ;
            BackController.getInstance().onBackClick(sourceNode);

        } catch (InvalidCredentialsException | DuplicatedUserException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    private UserBean retrieveInfo() throws InvalidCredentialsException {
        UserBean userBeanInfo = new UserBean();
        userBeanInfo.setUserEmail(emailText.getText());
        userBeanInfo.setName(nameText.getText());
        userBeanInfo.setPass(passText.getText());
        userBeanInfo.setSurname(surnameText.getText());
        userBeanInfo.setUserType(userType);

        if(emailText.getText().isEmpty() || nameText.getText().isEmpty() || passText.getText().isEmpty() || surnameText.getText().isEmpty())
            throw new InvalidCredentialsException("Fields not valid!");
        InternalBackController.getInternalBackControllerInstance().setLoggedUser(userBeanInfo);
        return userBeanInfo;
    }

}
