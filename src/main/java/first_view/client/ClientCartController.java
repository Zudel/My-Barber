package first_view.client;

import application_controller.BuyProductController;
import engineering.bean.UserBean;
import engineering.bean.CartBean;
import engineering.bean.CartRowBean;
import first_view.general.InternalBackController;
import first_view.list_cell_factories.CartRowListCellFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static first_view.list_cell_factories.BuyProductListCellFactory.EURO_SYMBOL;

public class ClientCartController implements Initializable {

    @FXML private ListView<CartRowBean> cartListView ;
    @FXML private Label totalAmount ;
    @FXML private Button plusButton ;
    @FXML private Button minusButton ;
    @FXML private Button deleteButton ;
    @FXML private Button addOrderInfoButton ;

    private BuyProductController buyProductController ;

    public static final String COMPLETE_ORDER_SCENE_RES = "first_view/client/client_complete_order.fxml" ;

    public ClientCartController() {
        UserBean loggedUser = InternalBackController.getInternalBackControllerInstance().getLoggedUser();
        buyProductController = new BuyProductController(loggedUser);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cartListView.setCellFactory(param -> new CartRowListCellFactory());

        cartListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            plusButton.setDisable(newValue == null);
            deleteButton.setDisable(newValue == null);
            minusButton.setDisable(newValue == null);
        });

        plusButton.setDisable(true);
        deleteButton.setDisable(true);
        minusButton.setDisable(true);

        viewCart();
    }

    public void setApplicationController(BuyProductController buyProductController) {
        this.buyProductController = buyProductController ;
        viewCart();
    }

    private void viewCart() {
        CartBean cartBean = buyProductController.showCart();
        updateInfo(cartBean) ;
    }

    private void updateInfo(CartBean cartBean) {
        cartListView.setItems(FXCollections.observableList(cartBean.getCartRowBeanArrayList()));
        totalAmount.setText(String.format("Totale: %s %.2f", EURO_SYMBOL, cartBean.getTotal())) ;
        addOrderInfoButton.setDisable(cartBean.getCartRowBeanArrayList().size() == 0);
    }

    public void onButtonClicked(ActionEvent event) {
        Node sourceNode = (Node) event.getSource() ;
        CartRowBean cartRow = cartListView.getSelectionModel().getSelectedItem() ;
        if (sourceNode == plusButton) {
            addProduct(cartRow) ;
        }
        else if (sourceNode == minusButton) {
            removeProduct(cartRow) ;
        }
        else if (sourceNode == deleteButton) {
            deleteProduct(cartRow) ;
        }
        else if (sourceNode == addOrderInfoButton){
            InternalBackController.getInternalBackControllerInstance().onNextScreen(sourceNode);
            FXMLLoader newCenterNodeLoader = new FXMLLoader(getClass().getClassLoader().getResource(COMPLETE_ORDER_SCENE_RES));
            Scene myScene = sourceNode.getScene();
            BorderPane borderPane = (BorderPane) myScene.getRoot();

            try {
                borderPane.setCenter(newCenterNodeLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            ClientCompleteOrderController completeOrderController = newCenterNodeLoader.getController();
            completeOrderController.setApplicationController(buyProductController);
        }

    }

    private void deleteProduct(CartRowBean cartRow) {
        Integer quantity = cartRow.getQuantity() ;
        for (int i = 0 ; i < quantity ; i++) {
            removeProduct(cartRow);
        }
    }

    private void removeProduct(CartRowBean cartRow) {
        CartBean cartBean = buyProductController.changeProductQuantity(cartRow, -1) ;
        updateInfo(cartBean);
    }

    private void addProduct(CartRowBean cartRow) {
        CartBean cartBean = buyProductController.changeProductQuantity(cartRow, +1) ;
        updateInfo(cartBean);
    }
}
