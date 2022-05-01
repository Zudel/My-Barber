package second_view.general;

import application_controller.LoginController;
import engineering.bean.AccessInfoBean;
import engineering.bean.UserBean;
import engineering.exception.NotExistentUserException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import static first_view.general.EnterAsUserTypeController.CLIENT_TYPE;

public class LoginScreenController {


    @FXML private TextField commandLine ;
    @FXML private TextField emailField ;
    @FXML private PasswordField passwordField ;

    @FXML private Button enterButton ;

    private final LoginController loginController ;

    public LoginScreenController() {
        loginController = new LoginController() ;
    }

    @FXML
    public void onCommand(ActionEvent event) throws IOException {
        String commandText = commandLine.getText() ;
        commandLine.setStyle(null);
        commandLine.setText("");
        if (commandText.matches("set email .*")) {
            String username = commandText.replace("set email ", "") ;
            emailField.setText(username);
            return ;
        }
        else if (commandText.matches("set password .*")) {
            String password = commandText.replace("set password ", "") ;
            passwordField.setText(password);
            return ;
        }
        else if (commandText.compareTo("not login") == 0) {
            ScreenChanger.getInstance().changeScreen(event, ScreenChanger.CLIENT_HOME_SCREEN);
            ScreenChanger.getInstance().setLoggedUser(null);
            return ;
        }
        else if (commandText.compareTo("subscribe") == 0) {
            ScreenChanger.getInstance().changeScreen(event, ScreenChanger.SUBSCRIBE_SCREEN);
            return ;
        }
        else if (commandText.compareTo("submit") == 0) {
            String userEmail = emailField.getText() ;
            String userPassword = passwordField.getText() ;
            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Alert completeAlert = new Alert(Alert.AlertType.WARNING, "COMPLETARE TUTTI I CAMPI") ;
                completeAlert.showAndWait() ;
            }
            else {
                UserBean loggedUser = login(userEmail, userPassword) ;
                enterAsUser(loggedUser, event) ;
            }
            return ;
        }

        commandLine.setStyle("-fx-border-color: red") ;

    }

    private void enterAsUser(UserBean user, ActionEvent event) throws IOException {
        if (user != null) {
            Integer userType = user.getUserType() ;
            if (userType == CLIENT_TYPE) {
                ScreenChanger.getInstance().changeScreen(event, ScreenChanger.CLIENT_HOME_SCREEN);
            }
            else {
                ScreenChanger.getInstance().changeScreen(event, ScreenChanger.BARBER_HOME_SCREEN) ;
            }
        }
    }

    private UserBean login(String userEmail, String userPassword) {
        AccessInfoBean accessInfo = new AccessInfoBean(userEmail, userPassword) ;
        UserBean loggedUser = null;
        try {
            loggedUser = loginController.verifyUser(accessInfo);
            ScreenChanger.getInstance().setLoggedUser(loggedUser);
        }
        catch (NotExistentUserException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage()) ;
            alert.showAndWait() ;
        }
        return loggedUser ;
    }

}