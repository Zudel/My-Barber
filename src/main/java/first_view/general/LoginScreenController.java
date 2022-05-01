package first_view.general;

import application_controller.LoginController;
import engineering.bean.AccessInfoBean;
import engineering.bean.UserBean;
import engineering.exception.NotExistentUserException;
import first_view.pickers.CredentialsPicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import javax.annotation.Nullable;
import java.io.IOException;

import static first_view.general.RegisterScreenController.CLIENT_MENU_SCREEN_NAME;

public class LoginScreenController {

    private static final String REGISTER_SCREEN_NAME = "first_view/general/register_screen.fxml";

    @FXML private Button loginButton ;
    @FXML private Button registerButton ;
    @FXML private Button continueWithoutAccessButton ;
    @FXML private TextField emailTextField ;
    @FXML private TextField passwordTextField ;


    @FXML
    public void onBackClicked(ActionEvent event) {
        BackController.getInstance().onBackClick((Node)event.getSource());
    }


    @FXML
    public void onButtonClicked(ActionEvent event) throws  IOException {
        Node eventSource = (Node)event.getSource() ;
        BackController.getInstance().pushPrevScene(eventSource.getScene());

        Stage stage = (Stage) (eventSource).getScene().getWindow();

        if (eventSource == loginButton) {
            if (emailTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Complete the field before!") ;
                alert.showAndWait() ;
                return ;
            }

            UserBean userBean = login();
            if (userBean != null) InternalBackController.getInternalBackControllerInstance().enterAsUser(userBean, stage);

        }
        else if (eventSource == continueWithoutAccessButton) {
            InternalBackController.getInternalBackControllerInstance().enterAsUser(null, stage);
        }
        else if (eventSource == registerButton) {
            BackController.getInstance().pushPrevScene(eventSource.getScene());
            FXMLLoader registerLoader = new FXMLLoader(getClass().getClassLoader().getResource(REGISTER_SCREEN_NAME)) ;

            stage.setScene(new Scene(registerLoader.load()));

        }

    }

    @Nullable
    private UserBean login()  {
        String userEmail = emailTextField.getText() ;
        String password = passwordTextField.getText() ;
        AccessInfoBean accessInfoBean = new AccessInfoBean(userEmail, password) ;
        LoginController loginController = new LoginController() ;

        UserBean loggedUser = null ;
        try {
            loggedUser = loginController.verifyUser(accessInfoBean);
        } catch (NotExistentUserException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage()) ;
            alert.showAndWait() ;
        }
        return loggedUser ;
    }

}
