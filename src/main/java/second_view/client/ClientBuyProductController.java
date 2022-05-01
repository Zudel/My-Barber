package second_view.client;

import application_controller.BuyProductController;
import engineering.bean.UserBean;
import engineering.bean.ProductBean;
import engineering.bean.ProductSearchInfoBean;
import first_view.list_cell_factories.BuyProductListCellFactory;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import second_view.general.ScreenChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientBuyProductController implements Initializable {


    @FXML private TextField commandLine ;
    @FXML private ListView<ProductBean> productListView ;

    private final BuyProductController buyProductController ;


    public ClientBuyProductController() {
        UserBean loggedUser = ScreenChanger.getInstance().getLoggedUser();
        buyProductController = new BuyProductController(loggedUser) ;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productListView.setCellFactory(param -> new BuyProductListCellFactory(BuyProductListCellFactory.SECOND_VIEW));
        searchProduct("");
    }


    @FXML
    public void onCommand(ActionEvent event) throws IOException {
        String command = commandLine.getText() ;
        commandLine.setStyle(null);
        commandLine.setText("");
        if (command.matches("search .*")) {
            String commandInput = command.replace("search " , "") ;
            searchProduct(commandInput);
            return ;
        }
        else if (command.matches("goto [0-9]+")) {
            String commandInput = command.replace("goto " , "") ;
            Integer productIndex = Integer.parseInt(commandInput) ;
            if (productIndex < productListView.getItems().size()) {
                ProductBean productBean = productListView.getItems().get(productIndex);
                ClientSeeProductController clientSeeProductController = (ClientSeeProductController) ScreenChanger.getInstance().changeScreen(event, ScreenChanger.CLIENT_SEE_PRODUCT_SCREEN);
                clientSeeProductController.setProduct(productBean);
                clientSeeProductController.setController(buyProductController);
                return;
            }
        }
        else if (command.compareTo("cart") == 0) {
            ClientCartController cartController = (ClientCartController) ScreenChanger.getInstance().changeScreen(event, ScreenChanger.CLIENT_CART_SCREEN);
            cartController.setAppController(buyProductController) ;
            return ;
        }
        else if (command.compareTo("back") == 0) {
            ScreenChanger.getInstance().onBack(event);
            return ;
        }

        commandLine.setStyle("-fx-border-color: red");
    }

    private void searchProduct(String searchText) {
        ProductSearchInfoBean productSearchInfoBean = new ProductSearchInfoBean(searchText) ;
        List<ProductBean> productBeans = buyProductController.filterProductList(productSearchInfoBean) ;
        productListView.setItems(FXCollections.observableList(productBeans));
    }


}
