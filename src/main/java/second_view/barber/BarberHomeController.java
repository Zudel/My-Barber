package second_view.barber;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import second_view.general.ScreenChanger;
import javafx.scene.control.TextField;

import java.io.IOException;


public class BarberHomeController {


    @FXML
    private TextField barberHomeCommandLine;


    @FXML
    public void onCommand(ActionEvent event) throws IOException {

        String commandLine = barberHomeCommandLine.getText();
        barberHomeCommandLine.setText("");
        barberHomeCommandLine.setStyle(null);

        if (commandLine.compareTo("exit") == 0 ) {
            ScreenChanger.getInstance().onBack(event) ;
            return ;
        }

        else if (commandLine.compareTo("userarea") == 0) {
            ScreenChanger.getInstance().changeScreen(event, ScreenChanger.BARBER_USER_AREA_SCREEN);
            return ;
        }
        else if (commandLine.compareTo("add service") == 0) {
            ScreenChanger.getInstance().changeScreen(event, ScreenChanger.BARBER_ADD_SERVICE_SCREEN_NAME);
            return ;
        }
        else if (commandLine.compareTo("manage appointments") == 0) {
            ScreenChanger.getInstance().changeScreen(event, ScreenChanger.BARBER_VIEW_APPOINTMENTS_SCREEN_NAME);
            return ;
        }
        else if (commandLine.compareTo("manage service") == 0) {
            ScreenChanger.getInstance().changeScreen(event, ScreenChanger.BARBER_MANAGE_SERVICE) ;
            return ;
        }
        else if (commandLine.compareTo("manage saloon") == 0) {
            ScreenChanger.getInstance().changeScreen(event, ScreenChanger.BARBER_MANAGE_SALOON_SCREEN_NAME);
            return ;
        }
        else if (commandLine.compareTo("manage order") == 0) {
            ScreenChanger.getInstance().changeScreen(event, ScreenChanger.BARBER_SEE_ORDER_SCREEN) ;
            return ;
        }
        else if (commandLine.compareTo("add product") == 0) {
            ScreenChanger.getInstance().changeScreen(event, ScreenChanger.BARBER_ADD_PRODUCT_SCREEN_NAME);
            return;
        }

        barberHomeCommandLine.setStyle("-fx-border-color: red");

    }
}
