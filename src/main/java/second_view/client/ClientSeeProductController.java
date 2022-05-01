package second_view.client;

import application_controller.BuyProductController;
import engineering.bean.ProductBean;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import second_view.general.ScreenChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static first_view.list_cell_factories.BuyProductListCellFactory.EURO_SYMBOL;

public class ClientSeeProductController {

    @FXML private TextField commandLine ;
    @FXML private Label productNameLabel ;
    @FXML private Label productPriceLabel ;
    @FXML private Text productDescriptionText ;

    private ProductBean productBean ;
    private BuyProductController buyProductController ;

    @FXML
    public void onCommand(ActionEvent event) {
        String command = commandLine.getText() ;
        commandLine.setStyle(null);
        commandLine.setText("");
        String addString = "add" ;

        if (command.compareTo("back") == 0) {
            ScreenChanger.getInstance().onBack(event);
            return;
        }
        else if (command.matches("add [0-9]+")) {
            Integer quantity = Integer.parseInt(command.replace(addString + " " , "")) ;
            for (int i = 0 ; i < quantity ; i++) {
                addProduct() ;
            }
            return ;
        }

        commandLine.setStyle("-fx-border-color: red");
    }

    private void addProduct() {
        buyProductController.insertProductToCart(productBean);
    }

    public void setProduct(ProductBean productBean) {
        this.productBean = productBean ;
        productNameLabel.setText(productBean.getBeanName());
        productPriceLabel.setText(EURO_SYMBOL + " " + productBean.getBeanPrice());
        productDescriptionText.setText(productBean.getBeanDescription());
    }

    public void setController(BuyProductController buyProductController) {
        this.buyProductController = buyProductController ;
    }
}
