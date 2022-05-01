package second_view.client;

import second_view.general.ScreenChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ClientSeeAppointmentController {

    @FXML TextField commandLine ;


    @FXML
    public void onCommand(ActionEvent event) {
        String command = commandLine.getText() ;
        commandLine.setStyle(null);
        commandLine.setText("");

        String revokeString = "revoke" ;
        String saloonString = "saloon" ;

        if (command.compareTo("back") == 0) {
            ScreenChanger.getInstance().onBack(event);
            return;
        }
        else if (command.startsWith(revokeString)) {
            String appointmentIndex = command.replace(revokeString + " ", "") ;
            if (appointmentIndex.matches("[0-9]+")) {
                return ;
            }
        }
        else if (command.startsWith(saloonString)) {
            String appointmentIndex = command.replace(saloonString + " ", "") ;
            if (appointmentIndex.matches("[0-9]+")) {
                return ;
            }
        }

        commandLine.setStyle("-fx-border-color: red") ;
    }
}
