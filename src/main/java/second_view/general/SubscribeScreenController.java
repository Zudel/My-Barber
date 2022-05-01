package second_view.general;

import application_controller.RegisterController;
import engineering.bean.UserBean;
import engineering.exception.DuplicatedUserException;
import engineering.exception.InvalidCredentialsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public class SubscribeScreenController {


    @FXML private TextField subscribeCommandLine;
    @FXML private TextField nameField ;
    @FXML private TextField surnameField ;
    @FXML private TextField subscribeEmailField ;
    @FXML private PasswordField subscribePasswordField ;
    @FXML private TextField userTypeField ;


    private Map<String, TextField> textFieldMap ;
    private int type;

    private void initMap() {
        textFieldMap = Map.of(
                "set name", nameField,
                "set surname", surnameField,
                "set email", subscribeEmailField,
                "set password", subscribePasswordField) ;
    }



    @FXML
    public void onCommand(ActionEvent event) throws IOException {
        String commandText = subscribeCommandLine.getText();

        subscribeCommandLine.setStyle(null);
        subscribeCommandLine.setText("");

        if (commandText.startsWith("set") && setCommand(commandText)) {
            return;
        }
        else if (commandText.startsWith("type") && typeCommand(commandText)) {
            return ;
        }
        else if (commandText.startsWith("register") && registerCommand(commandText)) {

            if (userTypeField.getText().compareTo("B") == 0) type=1;
            else if(userTypeField.getText().compareTo("C")==0) type =0;

            UserBean userBean;
            try {
                userBean = retrieveInfo();
                RegisterController registerController = new RegisterController();
                registerController.register(userBean);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Correctly Subscribed!!") ;
                alert.showAndWait() ;
                ScreenChanger.getInstance().onBack(event);
                ScreenChanger.getInstance().setLoggedUser(userBean);

            }catch (InvalidCredentialsException | DuplicatedUserException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }

            return;

        }
        else if (commandText.compareTo("back") == 0) {
            ScreenChanger.getInstance().onBack(event);
            return ;
        }

        subscribeCommandLine.setStyle("-fx-border-color: red");
    }

    private UserBean retrieveInfo() throws InvalidCredentialsException {
        UserBean userBeanInfo = new UserBean();
        userBeanInfo.setName(nameField.getText());
        userBeanInfo.setSurname(surnameField.getText());
        userBeanInfo.setUserType(type);
        userBeanInfo.setUserEmail(subscribeEmailField.getText());
        userBeanInfo.setPass(subscribePasswordField.getText());

        if(nameField.getText().isEmpty() || userTypeField.getText().isEmpty() || subscribePasswordField.getText().isEmpty() || surnameField.getText().isEmpty() || subscribeEmailField.getText().isEmpty()) {
            throw new InvalidCredentialsException("Set Valid Info!!");

        }
        return userBeanInfo;
    }

    private boolean setCommand(String insertedCommand) {
        initMap();
        for (Entry<String, TextField> entry : textFieldMap.entrySet()) {
            String command = entry.getKey();
            if (insertedCommand.startsWith(command)) {
                String input = insertedCommand.replace(command + " ", "");
                if (input.compareTo(command) != 0) {
                    entry.getValue().setText(input);
                    return true;
                }
            }
        }
        return false ;
    }

    private boolean typeCommand(String command) {
        String input = command.replace("type ", "") ;
        if (input.compareTo("B") == 0 || input.compareTo("C") == 0) {
            userTypeField.setText(input);
            return true;
        }
        return false ;
    }

    private boolean registerCommand(String command) {
        String registerString = "register" ;
        boolean handled = false ;
        if ((command.compareTo(registerString) == 0) || (command.compareTo(registerString + " google") == 0 || (command.compareTo(registerString + " facebook") == 0)) ) {
            handled = true ;
        }
        return handled;
    }

}
