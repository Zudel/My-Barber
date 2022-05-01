package first_view.pickers;

import application_controller.LoginController;
import engineering.bean.AccessInfoBean;
import engineering.bean.UserBean;
import engineering.exception.NotExistentUserException;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleAction;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;

public class CredentialsPicker extends Dialog<UserBean> {

    private static final String CREDENTIALS_PICKER_RES = "first_view/pickers/credentials_picker.fxml";
    private static final String EMAIL_FIELD_ID = "emailField" ;
    private static final String PASSWORD_FIELD_ID = "passwordField" ;

    private final LoginController loginController ;
    private final TextField emailField ;
    private final PasswordField passwordField ;

    public CredentialsPicker() throws IOException {

        loginController = new LoginController() ;

        DialogPane dialogPane = (new FXMLLoader(getClass().getClassLoader().getResource(CREDENTIALS_PICKER_RES))).load() ;
        this.setDialogPane(dialogPane);
        this.setTitle("Inserire Credenziali");

        emailField = (TextField) dialogPane.lookup("#" + EMAIL_FIELD_ID);
        passwordField = (PasswordField) dialogPane.lookup("#" + PASSWORD_FIELD_ID) ;

        this.resultConverterProperty().set(param -> {
            UserBean loggedUser = null ;
            if (param == ButtonType.OK) {
                loggedUser = login() ;
            }
            this.setResult(loggedUser);
            return loggedUser ;
        });

    }

    private UserBean login() {
        String email = emailField.getText() ;
        String password = passwordField.getText() ;
        AccessInfoBean accessInfoBean = new AccessInfoBean(email,password) ;
        UserBean loggedUser;
        try {
            loggedUser = loginController.verifyUser(accessInfoBean);
        } catch (NotExistentUserException ignored) {
            loggedUser = null;
        }
        return loggedUser ;
    }


    public UserBean doLogin() throws NotExistentUserException {
        this.showAndWait() ;
        if (this.getResult() == null) throw new NotExistentUserException("L'UTENTE INDICATO NON ESISTENTE") ;
        else return this.getResult() ;
    }
}
