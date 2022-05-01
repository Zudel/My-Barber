package first_view.list_cell_factories;

import javafx.scene.control.ListCell;

import static first_view.list_cell_factories.BuyProductListCellFactory.FIRST_VIEW;

public class CouponCodeCellFactory extends ListCell<String> {

    private final Integer caller ;

    public CouponCodeCellFactory(Integer caller) {
        this.caller = caller ;
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            String codeString = (caller.equals(FIRST_VIEW)) ? "Codice Coupon: " : "Coupon Code: " ;
            setText(codeString + item);
            setStyle("-fx-alignment: center ; -fx-font-size: 16");
        }
        else setText(null);
    }
}
