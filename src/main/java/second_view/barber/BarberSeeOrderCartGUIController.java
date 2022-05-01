package second_view.barber;

import application_controller.BarberManageOrderController;
import engineering.bean.CartRowBean;
import engineering.bean.VendorOrderBean;
import first_view.list_cell_factories.CartRowListCellFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import second_view.general.ScreenChanger;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BarberSeeOrderCartGUIController implements Initializable {

    @FXML private ListView<CartRowBean> orderCartListView ;
    @FXML private TextField addressLabel ;
    @FXML private TextField telephoneLabel ;
    @FXML private TextField orderOwnerLabel ;
    @FXML private TextField commandLine ;

    private VendorOrderBean vendorOrderBean ;

    private BarberManageOrderController barberManageOrderController ;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderCartListView.setCellFactory(param -> new CartRowListCellFactory());
    }


    @FXML
    public void onCommand(ActionEvent event) {
        String command = commandLine.getText() ;

        commandLine.setText("");
        commandLine.setStyle(null);

        if (command.matches("back")) {
            ScreenChanger.getInstance().onBack(event);
            return;
        }
        else if (command.matches("confirm order")){
            barberManageOrderController.confirmOrder(vendorOrderBean);
            return ;
        }

        commandLine.setStyle("-fx-border-color: RED");
    }

    public void setBarberManageOrderController(BarberManageOrderController barberManageOrderController, VendorOrderBean vendorOrderBean) {
        this.barberManageOrderController = barberManageOrderController;
        this.vendorOrderBean = vendorOrderBean ;
        viewCart();
    }

    private void viewCart() {
        List<CartRowBean> cartRowBeanList = barberManageOrderController.showOrderCart(vendorOrderBean) ;
        orderCartListView.setItems(FXCollections.observableList(cartRowBeanList));
        addressLabel.setText(vendorOrderBean.getAddress());
        telephoneLabel.setText(vendorOrderBean.getTelephone());
        orderOwnerLabel.setText(vendorOrderBean.getOrderOwner());
    }
}
