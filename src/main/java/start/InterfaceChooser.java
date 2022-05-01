package start;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

import static first_view.list_cell_factories.BuyProductListCellFactory.FIRST_VIEW;
import static first_view.list_cell_factories.BuyProductListCellFactory.SECOND_VIEW;

public class InterfaceChooser extends Dialog<Integer> {

    private static final String CHOOSER_RES_NAME = "start/interface_chooser.fxml" ;
    private static final String NORMAL_RADIO = "normalRadio" ;
    private static final String TERMINAL_RADIO = "terminalRadio" ;

    public InterfaceChooser() throws IOException {
        super();
        DialogPane dialogPane = (new FXMLLoader(getClass().getClassLoader().getResource(CHOOSER_RES_NAME))).load() ;
        this.setDialogPane(dialogPane);
        this.setTitle("Choose Your Interface");
        Image icon = new Image(new File("images/AppLogo.png").toURI().toString());
        setGraphic(new ImageView(icon));

        RadioButton normalRadio = (RadioButton) dialogPane.lookup("#" + NORMAL_RADIO);
        RadioButton terminalRadio = (RadioButton) dialogPane.lookup("#" + TERMINAL_RADIO) ;

        this.resultConverterProperty().set(param -> {
            Integer choosenInterface = -1 ;
            if (param == ButtonType.OK) {
                if (normalRadio.isSelected()) choosenInterface = FIRST_VIEW ;
                else if (terminalRadio.isSelected()) choosenInterface = SECOND_VIEW ;
            }
            this.setResult(choosenInterface);
            return choosenInterface ;
        });
    }

    public Integer chooseInterface() {
        this.showAndWait() ;
        return this.getResult() ;
    }

}
