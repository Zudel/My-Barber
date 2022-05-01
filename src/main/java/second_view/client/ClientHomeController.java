package second_view.client;

import second_view.general.ScreenChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ClientHomeController {

    @FXML
    private TextField commandLine ;

    @FXML
    public void onCommand(ActionEvent event) throws IOException {
        String command = commandLine.getText() ;
        commandLine.setStyle(null);
        commandLine.setText("");
        String appointmentString = "appointment" ;
        String productString = "product" ;
        if (command.compareTo("exit") == 0) {
            ScreenChanger.getInstance().onBack(event);
            return ;
        }
        else if (command.startsWith(appointmentString)) {
            if (command.compareTo(appointmentString + " " + "take") == 0) {
                ScreenChanger.getInstance().changeScreen(event, ScreenChanger.CLIENT_SEARCH_SALOON_SCREEN);
                return ;
            }
            else if (command.compareTo(appointmentString + " " + "see") == 0) {
                ScreenChanger.getInstance().changeScreen(event, ScreenChanger.CLIENT_SEE_APPOINTMENT_SCREEN);
                return ;
            }
        }
        else if (command.startsWith(productString)) {
            if (command.compareTo(productString + " " + "buy") == 0) {
                ScreenChanger.getInstance().changeScreen(event, ScreenChanger.CLIENT_BUY_PRODUCT_SCREEN);
                return ;
            }
            else if (command.compareTo(productString + " " + "cart") == 0) {
                ScreenChanger.getInstance().changeScreen(event, ScreenChanger.CLIENT_CART_SCREEN);
                return ;
            }

        }
        else if (command.compareTo("fidelity") == 0) {
            ScreenChanger.getInstance().changeScreen(event, ScreenChanger.CLIENT_FIDELITY_CARD_SCREEN) ;
            return ;
        }
        else if (command.compareTo("userarea") == 0) {
            return ;
        }

        commandLine.setStyle("-fx-border-color: red");

    }
}
