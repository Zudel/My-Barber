package first_view.barber;

import application_controller.ManageSaloonController;
import engineering.bean.SaloonBean;
import first_view.list_cell_factories.BarberSaloonListCellFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import first_view.general.InternalBackController;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BarberCenterController implements Initializable {

    @FXML private ListView<SaloonBean> barberCenterListView;
    @FXML private Label nameCenterLabelFirstView;
    @FXML private Label addressCenterLabelFirstView;
    @FXML private Label cityCenterLabelFirstView;
    @FXML private Label telephoneCenterLabelFirstView;
    @FXML private Button addCenterButton;
    @FXML private Button modifyButton;

    private static final String ADD_BARBER_CENTER_SCREEN_NAME = "first_view/barber/barber_modify_center.fxml";
    private static final String BARBER_MODIFY_SCREEN_NAME = "first_view/barber/barber_modify_center.fxml";


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ManageSaloonController manageSaloonController = new ManageSaloonController();
        manageSaloonController.setUserBean(InternalBackController.getInternalBackControllerInstance().getLoggedUser());

        List<SaloonBean> saloonBeanList = manageSaloonController.getAllSaloon();

        barberCenterListView.setCellFactory(param -> new BarberSaloonListCellFactory());

        barberCenterListView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {


            nameCenterLabelFirstView.setText(newValue.getName());
            addressCenterLabelFirstView.setText(newValue.getAddress());
            cityCenterLabelFirstView.setText(newValue.getCity());
            telephoneCenterLabelFirstView.setText(newValue.getPhone());

        }));

        barberCenterListView.getItems().clear();
        barberCenterListView.setItems(FXCollections.observableList(saloonBeanList));

    }

    @FXML
    public void onButtonClicked(ActionEvent event) throws IOException {
        Button sourceButton = (Button) event.getSource();


        String nextScreenName ;

        if (sourceButton == addCenterButton){
            nextScreenName = ADD_BARBER_CENTER_SCREEN_NAME ;
        }
        else if(sourceButton == modifyButton) {
            nextScreenName = BARBER_MODIFY_SCREEN_NAME ;
        }
        else {
            nextScreenName = null ;
        }

        if (nextScreenName != null) {
            InternalBackController.getInternalBackControllerInstance().onNextScreen(sourceButton);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(nextScreenName));
            BorderPane myBorderPane = (BorderPane) (sourceButton.getScene()).getRoot();
            myBorderPane.setCenter(fxmlLoader.load());
        }

    }


}
