package engineering.exception;

import javafx.scene.control.Alert;

public class InvalidIndexSelected extends Exception {

    public InvalidIndexSelected(){
        super();
    }

    public InvalidIndexSelected(String message){
        super(message);
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

}
