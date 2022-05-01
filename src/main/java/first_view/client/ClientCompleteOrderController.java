package first_view.client;

import application_controller.BuyProductController;
import engineering.bean.UserBean;
import engineering.bean.CouponBean;
import engineering.bean.OrderInfoBean;
import engineering.bean.OrderTotalBean;
import engineering.exception.IncorrectFormatException;
import engineering.exception.InvalidCouponException;
import engineering.exception.NegativePriceException;
import engineering.exception.NotExistentUserException;
import first_view.general.InternalBackController;
import first_view.list_cell_factories.CouponCodeCellFactory;
import first_view.pickers.CredentialsPicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static first_view.list_cell_factories.BuyProductListCellFactory.FIRST_VIEW;

public class ClientCompleteOrderController implements Initializable {

    @FXML private Button addCouponButton ;
    @FXML private TextField addressField ;
    @FXML private ListView<String> couponListView ;
    @FXML private Label orderTotalAmountLabel ;
    @FXML private Label acquiredPointsLabel ;
    @FXML private Button payWithPaypalButton ;
    @FXML private TextField telephoneField ;
    @FXML private TextField couponCodeField ;

    private BuyProductController buyProductController ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        couponListView.setCellFactory(param -> new CouponCodeCellFactory(FIRST_VIEW));
    }


    @FXML
    public void onButtonClicked(ActionEvent event) throws IOException {
        Node sourceNode = (Node) event.getSource() ;
        if (sourceNode == addCouponButton) {
            insertCoupon() ;
        }
        else if (sourceNode == payWithPaypalButton) {

            try {
                if (!addressField.getText().isEmpty() && !telephoneField.getText().isEmpty()) {

                    if (InternalBackController.getInternalBackControllerInstance().getLoggedUser() == null) {
                        login();
                    }
                    OrderInfoBean orderInfoBean = new OrderInfoBean(addressField.getText(), telephoneField.getText()) ;
                    buy(orderInfoBean);
                    InternalBackController.getInternalBackControllerInstance().backToHome(sourceNode);
                }
                else {
                    Alert alertDialog = new Alert(Alert.AlertType.ERROR, "COMPLETARE TUTTI I CAMPI DELL'ORDINE!!");
                    alertDialog.showAndWait();
                }

            } catch (NotExistentUserException | IncorrectFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void login() throws IOException, NotExistentUserException {
        CredentialsPicker credentialsPicker = new CredentialsPicker() ;
        UserBean userBean = credentialsPicker.doLogin() ;
        buyProductController.loadCustomer(userBean);
        InternalBackController.getInternalBackControllerInstance().setLoggedUser(userBean);

    }

    private void insertCoupon() {
        String stringCouponCode = couponCodeField.getText() ;
        try {
            CouponBean couponBean = new CouponBean(stringCouponCode) ;
            OrderTotalBean orderTotalBean = buyProductController.applyCoupon(couponBean);
            updateInfo(orderTotalBean);

        }
        catch (InvalidCouponException | NegativePriceException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage()) ;
            alert.showAndWait() ;
        }
    }

    public void setApplicationController(BuyProductController buyProductController) {
        this.buyProductController = buyProductController ;
        viewOrder();
    }

    public void viewOrder() {
        OrderTotalBean orderTotalBean = buyProductController.showOrder() ;
        updateInfo(orderTotalBean);
    }

    private void updateInfo(OrderTotalBean orderTotalBean) {
        orderTotalAmountLabel.setText(String.format("Totale Ordine: %.2f", orderTotalBean.getOrderTotal()));
        acquiredPointsLabel.setText("Punti Raccolti: " + orderTotalBean.getOrderPoints());

        couponListView.setItems(FXCollections.observableList(orderTotalBean.getExternalCouponCodes()));
    }

    private void buy(OrderInfoBean orderInfoBean) {
        buyProductController.completeOrder(orderInfoBean);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Il tuo ordine è stato completato correttamente") ;
        alert.showAndWait() ;
    }

}
