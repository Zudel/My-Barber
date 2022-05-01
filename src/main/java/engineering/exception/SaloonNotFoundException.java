package engineering.exception;

import javafx.scene.control.Alert;

public class SaloonNotFoundException extends Exception {

    public SaloonNotFoundException(){
        super();
    }

    public SaloonNotFoundException(String message){
        super(message);
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

}
