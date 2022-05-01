package second_view.barber;

import application_controller.ManageSaloonController;
import engineering.bean.SaloonBean;
import first_view.list_cell_factories.BarberSaloonListCellFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import second_view.general.ScreenChanger;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BarberManageSaloonController implements Initializable {

    @FXML private TextField manageSaloonCommandLine;
    @FXML private TextField manageSaloonNameTextField;
    @FXML private TextField manageSaloonAddressTextField;
    @FXML private TextField manageSaloonCityTextField;
    @FXML private TextField manageSaloonPhoneTextField;
    @FXML private ListView<SaloonBean> manageSaloonListView;
    @FXML private TextField manageSaloonSlotIndexTextField;

    private static final String OVERWRITE_SALOON_NAME_COMMAND = "overwrite name";
    private static final String OVERWRITE_SALOON_CITY_COMMAND = "overwrite city";
    private static final String OVERWRITE_SALOON_ADDRESS_COMMAND = "overwrite address";
    private static final String OVERWRITE_SALOON_PHONE_NUMBER_COMMAND = "overwrite phone number";


    @Override
    public void initialize(URL location, ResourceBundle resources){

        ManageSaloonController manageSaloonController = new ManageSaloonController();
        manageSaloonController.setUserBean(ScreenChanger.getInstance().getLoggedUser());
        List<SaloonBean> saloonBeanList;

        saloonBeanList = manageSaloonController.getAllSaloon();

        manageSaloonListView.setCellFactory(param -> new BarberSaloonListCellFactory(true));

        manageSaloonListView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {


            manageSaloonNameTextField.setText(newValue.getName());
            manageSaloonAddressTextField.setText(newValue.getAddress());
            manageSaloonCityTextField.setText(newValue.getCity());
            manageSaloonPhoneTextField.setText(newValue.getPhone());
            manageSaloonSlotIndexTextField.setText(Integer.toString(saloonBeanList.indexOf(newValue)));

        }));

        manageSaloonListView.getItems().clear();
        manageSaloonListView.setItems(FXCollections.observableList(saloonBeanList));

    }


    @FXML
    public void onCommand(ActionEvent event) throws IOException {

        String manageSaloonCommandLineLocal = manageSaloonCommandLine.getText();
        manageSaloonCommandLine.setText("");
        manageSaloonCommandLine.setStyle(null);

        if(manageSaloonCommandLineLocal.compareTo("back") == 0) {
            ScreenChanger.getInstance().onBack(event);
            return ;
        }
        else if(manageSaloonCommandLineLocal.compareTo("delete") == 0) {
            return ;
        }
        else if(manageSaloonCommandLineLocal.compareTo("modify") == 0) {
            return ;
        }
        else if(manageSaloonCommandLineLocal.startsWith("select saloon")) {
            String nameOfSelectedSaloon = manageSaloonCommandLineLocal.replace("select saloon" + " ", "");
            manageSaloonNameTextField.setText(nameOfSelectedSaloon);
            return ;
        }
        else if(manageSaloonCommandLineLocal.startsWith("overwrite")) {
            if(overwriteSaloonCommandHandler(manageSaloonCommandLineLocal)) {
                return;
            }
        }
        else if(manageSaloonCommandLineLocal.compareTo("save changes") == 0) {
            return ;
        }
        else if(manageSaloonCommandLineLocal.compareTo("add new saloon") == 0) {
            ScreenChanger.getInstance().changeScreen(event, ScreenChanger.BARBER_ADD_NEW_SALOON);
            return;
        }

        manageSaloonCommandLine.setStyle("-fx-border-color: red");

    }

    private boolean overwriteSaloonCommandHandler(String command) {

        boolean flag = false;
        String insertCommand;

        if(command.startsWith(OVERWRITE_SALOON_NAME_COMMAND)) {
            insertCommand = command.replace(OVERWRITE_SALOON_NAME_COMMAND + " ", "");
            manageSaloonNameTextField.setText(insertCommand);
            flag = true;
        }
        else if(command.startsWith(OVERWRITE_SALOON_CITY_COMMAND)) {
            insertCommand = command.replace(OVERWRITE_SALOON_CITY_COMMAND + " ", "");
            manageSaloonCityTextField.setText(insertCommand);
            flag = true;
        }
        else if(command.startsWith(OVERWRITE_SALOON_ADDRESS_COMMAND)) {
            insertCommand = command.replace(OVERWRITE_SALOON_ADDRESS_COMMAND + " ", "");
            manageSaloonAddressTextField.setText(insertCommand);
            flag = true;
        }
        else if(command.startsWith(OVERWRITE_SALOON_PHONE_NUMBER_COMMAND)) {
            insertCommand = command.replace(OVERWRITE_SALOON_PHONE_NUMBER_COMMAND + " ", "");
            manageSaloonPhoneTextField.setText(insertCommand);
            flag = true;
        }

        return flag;
    }

}