package second_view.client;

import application_controller.BuyProductController;
import engineering.bean.UserBean;
import engineering.bean.CartBean;
import engineering.bean.CartRowBean;
import first_view.list_cell_factories.CartRowListCellFactory;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import second_view.general.ScreenChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static first_view.list_cell_factories.BuyProductListCellFactory.EURO_SYMBOL;
import static first_view.list_cell_factories.BuyProductListCellFactory.SECOND_VIEW;

public class ClientCartController implements Initializable {

    @FXML private TextField commandLine ;
    @FXML private ListView<CartRowBean> cartListView ;
    @FXML private Label cartTotalLabel ;

    BuyProductController buyProductController ;

    public ClientCartController() {
        UserBean loggedUser = ScreenChanger.getInstance().getLoggedUser();
        buyProductController = new BuyProductController(loggedUser);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cartListView.setCellFactory(param -> new CartRowListCellFactory(SECOND_VIEW));
        updateView();
    }

    public void onCommand(ActionEvent event) throws IOException {
        String command = commandLine.getText() ;
        commandLine.setStyle(null);
        commandLine.setText("");

        if (command.compareTo("back") == 0) {
            ScreenChanger.getInstance().onBack(event);
            return;
        }
        else if (command.matches("increment [0-9]+")) {
            Integer inputIndex = Integer.parseInt(command.replace("increment ", "")) ;
            if (inputIndex < cartListView.getItems().size()) {
                addProduct(inputIndex);
                return;
            }
        }
        else if (command.matches("decrement [0-9]+")) {
            Integer inputIndex = Integer.parseInt(command.replace("decrement ", "")) ;
            if (inputIndex < cartListView.getItems().size()) {
                removeProduct(inputIndex);
                return;
            }
        }
        else if (command.matches("delete [0-9]+")) {
            Integer inputIndex = Integer.parseInt(command.replace("delete ", "")) ;
            if (inputIndex < cartListView.getItems().size()) {
                Integer productQuantity = cartListView.getItems().get(inputIndex).getQuantity() ;
                for (int i = 0 ; i < productQuantity ; i++) {
                    removeProduct(inputIndex);
                }
                return;
            }
        }
        else if (command.compareTo("complete") == 0 && cartListView.getItems().size() != 0) {
            ClientCompleteOrderController completeOrderController = (ClientCompleteOrderController) ScreenChanger.getInstance().changeScreen(event, ScreenChanger.CLIENT_COMPLETE_ORDER_SCREEN);
            completeOrderController.setApplicationController(buyProductController);
            return ;

        }

        commandLine.setStyle("-fx-border-color: red");
    }

    private void removeProduct(Integer inputIndex) {
        CartRowBean cartRowBean = cartListView.getItems().get(inputIndex) ;
        buyProductController.changeProductQuantity(cartRowBean, -1) ;
        updateView();
    }

    private void addProduct(Integer index) {
        CartRowBean cartRowBean = cartListView.getItems().get(index) ;
        buyProductController.changeProductQuantity(cartRowBean, +1);
        updateView();
    }

    public void setAppController(BuyProductController buyProductController) {
        this.buyProductController = buyProductController ;
        updateView();
    }

    private void updateView() {
        CartBean cartBean = buyProductController.showCart() ;
        cartListView.setItems(FXCollections.observableList(cartBean.getCartRowBeanArrayList()));
        cartTotalLabel.setText(String.format("Total: %s%.2f",EURO_SYMBOL, cartBean.getTotal()));


    }


}
