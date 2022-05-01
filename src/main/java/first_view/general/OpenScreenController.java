package first_view.general;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OpenScreenController {

    private static final String LOGIN_SCREEN_NAME = "first_view/general/login_screen.fxml";

    @FXML
    public void onStartClick(ActionEvent event) throws IOException {
        //Aggiunta sul BackStack
        Node sourceNode = (Node) event.getSource() ;
        BackController backController = BackController.getInstance() ;
        backController.pushPrevScene(sourceNode.getScene());

        //Cambio Scena
        FXMLLoader root = new FXMLLoader(getClass().getClassLoader().getResource(LOGIN_SCREEN_NAME));
        Stage stage = (Stage) (sourceNode.getScene().getWindow());
        Scene scene = new Scene(root.load());
        stage.setScene(scene) ;
    }
}