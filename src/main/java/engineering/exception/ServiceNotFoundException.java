package engineering.exception;

import javafx.scene.control.Alert;

import java.io.Serial;

public class ServiceNotFoundException extends Exception{

    @Serial
    private static final long serialVersionUID = -1087345249983767448L;

    public ServiceNotFoundException(){
        super();
    }

    public ServiceNotFoundException(String message){
        super(message);
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

}
