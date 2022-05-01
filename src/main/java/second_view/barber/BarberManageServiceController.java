package second_view.barber;

import application_controller.ManageServiceController;
import engineering.bean.ServiceBean;
import engineering.exception.IncorrectFormatException;
import first_view.list_cell_factories.ServiceListCellFactory;
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


public class BarberManageServiceController implements Initializable {

    @FXML private TextField manageServiceCommandLine;
    @FXML private TextField manageServiceNameTextField;
    @FXML private TextField manageServiceDescriptionTextField;
    @FXML private TextField manageServicePriceTextField;
    @FXML private TextField manageServiceNameOfUsedProductTextField;
    @FXML private TextField manageServiceSlotIndexTextField;
    @FXML private ListView<ServiceBean> manageServiceListView;



    private List<ServiceBean> serviceBeanList;
    private ManageServiceController manageServiceController;

    public BarberManageServiceController() {

        manageServiceController = new ManageServiceController();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources){

        try{
            serviceBeanList = manageServiceController.getAllService(ScreenChanger.getInstance().getLoggedUser());
        } catch (IncorrectFormatException e) {
            e.printStackTrace();
        }


        manageServiceListView.setCellFactory(param-> new ServiceListCellFactory(null, true));

        manageServiceListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            assert newValue != null;
            manageServiceNameTextField.setText(newValue.getNameInfo());
            manageServiceDescriptionTextField.setText(newValue.getDescriptionInfo());
            manageServiceNameOfUsedProductTextField.setText(newValue.getNameOfUsedProductInfo());
            manageServicePriceTextField.setText(Double.toString(newValue.getPriceInfo()));
            manageServiceSlotIndexTextField.setText(Integer.toString(serviceBeanList.indexOf(newValue)));
        });

        manageServiceListView.getItems().clear();
        manageServiceListView.setItems(FXCollections.observableList(serviceBeanList));
    }


    @FXML
    public void onCommand(ActionEvent event) throws IOException {

        String manageServiceCommandLineLocal = manageServiceCommandLine.getText();
        manageServiceCommandLine.setText("");
        manageServiceCommandLine.setStyle(null);

        if(manageServiceCommandLineLocal.compareTo("back") == 0){
            ScreenChanger.getInstance().onBack(event);
            return ;
        }
        else if(manageServiceCommandLineLocal.startsWith("modify service")){

            String nameOfServiceToModify = manageServiceCommandLineLocal.replace("modify service" + " ", "");
            ServiceBean serviceBeanToModify = null;
            for(ServiceBean serviceBean : serviceBeanList) {

                if(serviceBean.getNameInfo().compareTo(nameOfServiceToModify) == 0) {
                    serviceBeanToModify = serviceBean;
                    break;
                }

            }

            assert serviceBeanToModify != null;
            BarberModifyServiceController barberModifyServiceController = (BarberModifyServiceController) ScreenChanger.getInstance().changeScreen(event, ScreenChanger.BARBER_MODIFY_SERVICE_SCREEN_NAME);
            barberModifyServiceController.setServiceBeanToModify(serviceBeanToModify);

            return;

        }
        else if(manageServiceCommandLineLocal.startsWith("delete")) {

            String serviceNameToDelete = manageServiceCommandLineLocal.replace("delete ", "");


            for(ServiceBean serviceBean : serviceBeanList) {

                if(serviceBean.getNameInfo().equals(serviceNameToDelete)) {

                    manageServiceController.deleteService(serviceBean, ScreenChanger.getInstance().getLoggedUser());
                    break;

                }

            }

            ScreenChanger.getInstance().goToHome(event);
            return;
        }

        manageServiceCommandLine.setStyle("-fx-border-color: red");
    }


}
