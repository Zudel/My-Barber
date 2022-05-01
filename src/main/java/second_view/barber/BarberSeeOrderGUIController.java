package second_view.barber;

import application_controller.BarberManageOrderController;
import engineering.bean.UserBean;
import engineering.bean.VendorOrderBean;
import first_view.list_cell_factories.BarberOrderListCellFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import second_view.general.ScreenChanger;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static first_view.list_cell_factories.BuyProductListCellFactory.SECOND_VIEW;

public class BarberSeeOrderGUIController implements Initializable {

    @FXML private TextField commandLine ;
    @FXML private Button enterButton ;
    @FXML private ListView<VendorOrderBean> orderListView ;

    private final BarberManageOrderController barberManageOrderController ;

    public BarberSeeOrderGUIController() {
        UserBean userBean = ScreenChanger.getInstance().getLoggedUser();
        barberManageOrderController = new BarberManageOrderController(userBean) ;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderListView.setCellFactory(param -> new BarberOrderListCellFactory(SECOND_VIEW));
        List<VendorOrderBean> vendorOrderBeanList = barberManageOrderController.showAllOrders() ;
        Collections.reverse(vendorOrderBeanList);
        orderListView.setItems(FXCollections.observableList(vendorOrderBeanList));
    }

    @FXML
    public void onCommand(ActionEvent event) {
        String commandText = commandLine.getText() ;
        commandLine.setStyle(null);
        commandLine.setText("");

        if (commandText.compareTo("back") == 0) {
            ScreenChanger.getInstance().onBack(event);
            return ;
        }
        else if (commandText.matches("see order [0-9]+")) {
            Integer commandInput = Integer.parseInt(commandText.replace("see order ", "")) ;
            if (commandInput < orderListView.getItems().size()) {
                VendorOrderBean vendorOrderBean = orderListView.getItems().get(commandInput);
                try {
                    BarberSeeOrderCartGUIController barberSeeOrderCartGUIController = (BarberSeeOrderCartGUIController) ScreenChanger.getInstance().changeScreen(event, ScreenChanger.BARBER_SEE_CART_ORDER_SCREEN);
                    barberSeeOrderCartGUIController.setBarberManageOrderController(barberManageOrderController, vendorOrderBean);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }

        commandLine.setStyle("-fx-border-color: RED");
    }


}
