package first_view.list_cell_factories;

import engineering.bean.CartRowBean;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.io.IOException;

import static first_view.list_cell_factories.BuyProductListCellFactory.*;

public class CartRowListCellFactory extends ListCell<CartRowBean> {

    private Parent parentNode = null ;

    private static final String LIST_ITEM_RES = "first_view/list_item/client_cart_row.fxml";
    private static final String NAME_LABEL_ID = "productNameLabel" ;
    private static final String PRICE_LABEL_ID = "productPriceLabel" ;
    private static final String QUANTITY_LABEL_ID = "productRowQuantity" ;
    private static final String INDEX_LABEL_ID = "cartRowIndexLabel" ;

    private final Integer caller ;

    public CartRowListCellFactory() {
        this(FIRST_VIEW) ;
    }

    public CartRowListCellFactory(Integer caller) {
        this.caller = caller ;
    }

    @Override
    protected void updateItem(CartRowBean item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
            try {
                if (parentNode == null) parentNode = new FXMLLoader(getClass().getClassLoader().getResource(LIST_ITEM_RES)).load() ;

                Label productNameLabel = (Label) parentNode.lookup("#" + NAME_LABEL_ID) ;
                Label productPriceLabel = (Label) parentNode.lookup("#" + PRICE_LABEL_ID) ;
                Label productQuantityLabel = (Label) parentNode.lookup("#" + QUANTITY_LABEL_ID) ;
                productNameLabel.setText(item.getName());
                productPriceLabel.setText(String.format("%s %.2f", EURO_SYMBOL, item.getPrice()));
                productQuantityLabel.setText(Integer.toString(item.getQuantity()));

                if (caller == SECOND_VIEW) {
                    Label indexLabel = (Label) parentNode.lookup("#" + INDEX_LABEL_ID) ;
                    indexLabel.setText("Index " + this.getIndex());
                }

                setGraphic(parentNode);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            setGraphic(null);
        }

    }
}
