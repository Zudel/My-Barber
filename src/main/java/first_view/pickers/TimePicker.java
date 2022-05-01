package first_view.pickers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;

public class TimePicker extends TextInputDialog {

    private static final String TIME_PICKER_RES = "first_view/pickers/time_picker.fxml";
    private static final String HOUR_SLIDER_ID = "hourSlider" ;
    private static final String MINUTE_SLIDER_ID = "minuteSlider" ;

    public TimePicker(int firstHour, int lastHour) throws IOException {
        super();
        //Si Crea il DialogPane, ovvero il layout del dialog, dalla Risorsa .fxml
        DialogPane dialogPane = (new FXMLLoader(getClass().getClassLoader().getResource(TIME_PICKER_RES))).load() ;
        //Si prendono i due slider nel dialogPane a partire dai loro id fxml
        Slider hourSlider = (Slider) dialogPane.lookup("#" + HOUR_SLIDER_ID);
        Slider minuteSlider = (Slider) dialogPane.lookup("#" + MINUTE_SLIDER_ID) ;
        //Si impostano il minimo e il massimo dello Slider dell'ora; lo slider dei minuti ha valori di default
        hourSlider.setMin(firstHour) ;
        hourSlider.setMax(lastHour);
        //Si imposta il valore iniziale dello slider delle ore a quello del minimo
        hourSlider.setValue(firstHour);

        //Si imposta il dialogPane
        super.setDialogPane(dialogPane);

        super.resultConverterProperty().set(param -> {
            if (param == ButtonType.OK) {
                String result = formatTime((int)hourSlider.getValue()) + ":" + formatTime((int)minuteSlider.getValue()) ;
                this.setResult(result);
                return result;
            } else {
                return "--:--";
            }
        });
    }

    private String formatTime(int time) {
        //Metodo per formattare il tempo in modo corretto con uno 0 davanti se è formato da unico carattere
        String stringTime = Integer.toString(time) ;
        if (stringTime.length() == 1) {
            return "0" + stringTime ;
        }
        return stringTime ;
    }

    public String getTime() {
        this.showAndWait() ;
        return this.getResult() ;
    }

}
