package first_view.barber;

import application_controller.BarberManageOrderController;
import engineering.bean.UserBean;
import engineering.bean.CartRowBean;
import engineering.bean.VendorOrderBean;
import first_view.general.InternalBackController;
import first_view.list_cell_factories.BarberOrderListCellFactory;
import first_view.list_cell_factories.CartRowListCellFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static first_view.list_cell_factories.BuyProductListCellFactory.FIRST_VIEW;

public class BarberManageOrderGUIController implements Initializable {

    @FXML private TextField orderAddressLabel;
    @FXML private ListView<CartRowBean> orderCartListView;
    @FXML private ListView<VendorOrderBean> orderListView;
    @FXML private TextField orderOwnerLabel;
    @FXML private TextField orderTelephoneLabel;

    private final BarberManageOrderController barberManageOrderController ;

    public BarberManageOrderGUIController() {
        UserBean loggedUser = InternalBackController.getInternalBackControllerInstance().getLoggedUser();
        barberManageOrderController = new BarberManageOrderController(loggedUser) ;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderListView.setCellFactory(param -> new BarberOrderListCellFactory(FIRST_VIEW));

        orderCartListView.setCellFactory(param -> new CartRowListCellFactory()) ;

        orderListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                List<CartRowBean> cartRowBeans = barberManageOrderController.showOrderCart(newValue);
                orderCartListView.setItems(FXCollections.observableList(cartRowBeans));
                orderTelephoneLabel.setText(newValue.getTelephone());
                orderAddressLabel.setText(newValue.getAddress());
                orderOwnerLabel.setText(newValue.getOrderOwner());
            }
            else {
                orderCartListView.setItems(FXCollections.observableList(new ArrayList<>()));
                orderTelephoneLabel.setText("");
                orderAddressLabel.setText("");
                orderOwnerLabel.setText("");
            }
        });
        viewAllOrders() ;
    }

    private void viewAllOrders() {
        List<VendorOrderBean> vendorOrderBeans = barberManageOrderController.showAllOrders() ;
        Collections.reverse(vendorOrderBeans);
        orderListView.setItems(FXCollections.observableList(vendorOrderBeans));
    }

    @FXML
    public void onButtonClicked(ActionEvent event) {
        VendorOrderBean vendorOrderBean = orderListView.getSelectionModel().getSelectedItem() ;
        if (vendorOrderBean != null) {
            barberManageOrderController.confirmOrder(vendorOrderBean) ;
        }
    }
}
