package first_view.client;

import application_controller.BuyProductController;
import engineering.bean.UserBean;
import engineering.bean.ProductBean;
import engineering.bean.ProductSearchInfoBean;
import first_view.general.InternalBackController;
import first_view.list_cell_factories.BuyProductListCellFactory;
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
import java.util.List;
import java.util.ResourceBundle;

import static first_view.list_cell_factories.BuyProductListCellFactory.EURO_SYMBOL;

public class ClientBuyProductController implements Initializable {

    @FXML private ListView<ProductBean> buyProductListView ;
    @FXML private Button addToCartButton ;
    @FXML private Button searchButton ;
    @FXML private TextField searchField ;
    @FXML private Label productNameLabel ;
    @FXML private Label productPriceLabel ;
    @FXML private Label productDescriptionLabel ;
    @FXML private Button goToCartButton ;

    private final BuyProductController buyProductController;

    public static final String CLIENT_CART_SCENE_RES = "first_view/client/client_cart.fxml" ;

    public ClientBuyProductController() {
        UserBean userBean = InternalBackController.getInternalBackControllerInstance().getLoggedUser();
        buyProductController = new BuyProductController(userBean) ;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buyProductListView.setCellFactory(param -> new BuyProductListCellFactory());
        addToCartButton.setDisable(true);

        buyProductListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            productNameLabel.setText((newValue == null) ? "" : newValue.getBeanName());
            productPriceLabel.setText((newValue == null) ? "" : EURO_SYMBOL + newValue.getBeanPrice());
            productDescriptionLabel.setText((newValue == null) ? "" : newValue.getBeanDescription());

            addToCartButton.setDisable(newValue == null);
        });

        searchProduct("");
    }

    @FXML
    public void onButtonClicked(ActionEvent event) {
        Node sourceNode = (Node) event.getSource() ;
        if (sourceNode == addToCartButton) {
            ProductBean selectedProduct = buyProductListView.getSelectionModel().getSelectedItem() ;
            buyProductController.insertProductToCart(selectedProduct);
        }
        else if (sourceNode == searchButton) {
            searchProduct(searchField.getText());
        }
        else if (sourceNode == goToCartButton) {

            try {
                InternalBackController.getInternalBackControllerInstance().onNextScreen(sourceNode);
                FXMLLoader newCenterNodeLoader = new FXMLLoader(getClass().getClassLoader().getResource(CLIENT_CART_SCENE_RES));

                Scene myScene = sourceNode.getScene();
                BorderPane borderPane = (BorderPane) myScene.getRoot();
                borderPane.setCenter(newCenterNodeLoader.load());
                ClientCartController cartController = newCenterNodeLoader.getController();
                cartController.setApplicationController(buyProductController);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void searchProduct(String productName) {
        ProductSearchInfoBean searchInfoBean = new ProductSearchInfoBean(productName) ;
        List<ProductBean> productBeans = buyProductController.filterProductList(searchInfoBean);
        buyProductListView.setItems(FXCollections.observableList(productBeans));
    }

}
