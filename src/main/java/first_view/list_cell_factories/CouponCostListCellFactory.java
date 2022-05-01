package first_view.list_cell_factories;

import engineering.bean.CouponBean;
import javafx.scene.control.ListCell;

import static first_view.list_cell_factories.BuyProductListCellFactory.EURO_SYMBOL;
import static first_view.list_cell_factories.BuyProductListCellFactory.FIRST_VIEW;

public class CouponCostListCellFactory extends ListCell<CouponBean> {

    private final Integer caller ;


    public CouponCostListCellFactory(Integer caller) {
        this.caller = caller ;
    }
    @Override
    protected void updateItem(CouponBean item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            Integer size ;
            if (caller.equals(FIRST_VIEW)) {
                setText(String.format("SCONTO: -%.2f\t\t%s\t\tCOSTO IN PUNTI: %d", item.getCouponDiscount(), (item.getExternalCouponType().compareTo("subtraction") == 0) ? EURO_SYMBOL : "%", item.getCouponPointsPrice()));
                size = 16 ;
            }
            else {
                setText(String.format("INDEX: %1$4d\tDISCOUNT: -%2$4.2f\t\t%3$1s\t\tCOST: %4$3d", this.getIndex() , item.getCouponDiscount(), (item.getExternalCouponType().compareTo("subtraction") == 0) ? EURO_SYMBOL : "%", item.getCouponPointsPrice()));
                size = 12 ;
            }
            setStyle(String.format("-fx-font-size: %d ; -fx-alignment: center", size));
        } else setText(null);
    }
}
