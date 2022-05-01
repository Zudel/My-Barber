package start;

import first_view.MainClass1;
import javafx.application.Application;
import javafx.stage.Stage;
import second_view.MainClass2;

import static first_view.list_cell_factories.BuyProductListCellFactory.FIRST_VIEW;
import static first_view.list_cell_factories.BuyProductListCellFactory.SECOND_VIEW;

public class MainClass extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        InterfaceChooser interfaceChooser = new InterfaceChooser() ;
        int choosen = interfaceChooser.chooseInterface() ;

        Application application = null ;
        if (choosen == FIRST_VIEW) application = new MainClass1() ;

        else if (choosen == SECOND_VIEW) application = new MainClass2() ;

        if (application != null) application.start(new Stage());
        else System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}