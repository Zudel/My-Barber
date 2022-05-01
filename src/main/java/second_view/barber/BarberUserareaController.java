package second_view.barber;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import second_view.general.ScreenChanger;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class BarberUserareaController {

    @FXML private TextField barberUsereaCommandLine;
    @FXML private TextField addressBarberUserareaField;
    @FXML private TextField barberUsernameField;
    @FXML private TextField barberEmailField;
    @FXML private PasswordField barberPasswordField;

    @FXML
    public void onCommand (ActionEvent event) {

        String userAreaCommand = barberUsereaCommandLine.getText();
        barberUsereaCommandLine.setText("");
        barberUsereaCommandLine.setStyle(null);

        if(userAreaCommand.compareTo("back") == 0 ) {
            ScreenChanger.getInstance().onBack(event);
            return ;
        }
        else if (userAreaCommand.compareTo("logout") == 0 ) {
            System.exit(0);
        }
        else if (userAreaCommand.startsWith("overwrite") && overwriteCommand(userAreaCommand)) {
            return ;

        }
        else if(userAreaCommand.compareTo("save") == 0 ){
            return ;
        }

        barberUsereaCommandLine.setStyle("-fx-border-color: red");


    }

    private boolean overwriteCommand (String command) {

        String newField = "";

        if(command.startsWith("overwrite address")) {
            newField = command.replace("overwrite address" + " ", "");
            addressBarberUserareaField.setText(newField);
            return true;
        }
        else if(command.startsWith("overwrite username")) {
            newField = command.replace("overwrite username" + " ", "");
            barberUsernameField.setText(newField);
            return true;
        }
        else if(command.startsWith("overwrite email")){
            newField = command.replace("overwrite email" + " ", "");
            barberEmailField.setText(newField);
            return true;
        }
        else if(command.startsWith("overwrite password")) {
            newField = command.replace("overwrite password" + " ", "");
            barberPasswordField.setText(newField);
            return true;
        }
        else{
            return false;
        }
    }

}