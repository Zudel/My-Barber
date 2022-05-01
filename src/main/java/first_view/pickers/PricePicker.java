package first_view.pickers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;

public class PricePicker extends TextInputDialog {

    private static final String PRICE_PICKER_RES = "first_view/pickers/price_picker.fxml";
    private static final String INTEGER_SPINNER_ID = "integerSpinner";
    private static final String CENTS_SPINNER_ID = "centsSpinner";


    public PricePicker(int startInteger, double startCents) throws IOException {
        super();
        //Carico il dialogPane e lo imposto come quello di questo Dialog
        DialogPane dialogPane = (new FXMLLoader(getClass().getClassLoader().getResource(PRICE_PICKER_RES))).load() ;
        this.setDialogPane(dialogPane);

        //Prendo gli spinner
        Spinner<Integer> integerSpinner = (Spinner) dialogPane.lookup("#" + INTEGER_SPINNER_ID) ;
        Spinner<Double> centsSpinner = (Spinner) dialogPane.lookup("#" + CENTS_SPINNER_ID) ;

        /*
            Imposto gli SpinnerValueFactory: Sono classi che si occupano della gestione dell'incremento e decremento
            del valore dello spinner.
            In questo caso, lavorando con interi esiste un factory precostruito
         */
        integerSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4096, startInteger));
        centsSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,0.99,startCents, 0.01));

        //Imposto il meccanismo di ritorno del risultato (Vedere TimePicker per dettagli)
        this.resultConverterProperty().set(param -> {
            if (param == ButtonType.OK) {
                String result = Double.toString(integerSpinner.getValue() + centsSpinner.getValue()) ;
                this.setResult(result);
                return result ;
            }
            else {
                return "0.00" ;
            }
        });
    }

    public String getPrice() {
        // Metodo per ottenere in modo trasparente il prezzo inserito
        this.showAndWait() ;
        return this.getResult() ;
    }
}
