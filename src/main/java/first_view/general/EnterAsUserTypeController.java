package first_view.general;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class EnterAsUserTypeController {

    public static final int CLIENT_TYPE = 0 ;
    public static final int BARBER_TYPE = 1 ;

    public static final String BARBER_HOME_SCREEN = "first_view/barber/barber_home.fxml";
    public static final String CLIENT_HOME_SCREEN = "first_view/client/client_home.fxml";


    public void enterAsUser(int userType, Scene mainScene) throws IOException {
        BorderPane container = (BorderPane) mainScene.getRoot() ;
        String mainScreen = null;
        if (userType == CLIENT_TYPE) {
            mainScreen = CLIENT_HOME_SCREEN ;
        }
        else if (userType == BARBER_TYPE){
            mainScreen = BARBER_HOME_SCREEN ;
        }
        if (container != null && mainScreen != null) {
            FXMLLoader userHomeScreen = new FXMLLoader(getClass().getClassLoader().getResource(mainScreen)) ;
            container.setCenter(userHomeScreen.load());
        }
    }
}
