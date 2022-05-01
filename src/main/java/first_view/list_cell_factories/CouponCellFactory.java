package first_view.list_cell_factories;

import engineering.bean.CouponBean;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.io.IOException;

import static first_view.list_cell_factories.BuyProductListCellFactory.EURO_SYMBOL;
import static first_view.list_cell_factories.BuyProductListCellFactory.FIRST_VIEW;

public class CouponCellFactory extends ListCell<CouponBean> {

    private static final String LIST_ITEM_RES = "first_view/list_item/client_coupon_item.fxml" ;
    private static final String COUPON_CODE_LABEL_ID = "couponCodeLabel" ;
    private static final String COUPON_DISCOUNT_LABEL_ID = "couponDiscountLabel" ;

    private Parent parentNode = null ;

    private final Integer caller ;
    private final String codeString ;
    private final String discountString ;

    public CouponCellFactory(Integer caller) {
        this.caller = caller ;
        codeString = (caller.equals(FIRST_VIEW)) ? "Codice: " : "Code: " ;
        discountString = (caller.equals(FIRST_VIEW)) ? "Sconto: " : "Discount: " ;
    }

    @Override
    protected void updateItem(CouponBean item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
            try {
                if (parentNode == null) parentNode = new FXMLLoader(getClass().getClassLoader().getResource(LIST_ITEM_RES)).load() ;

                Label couponCodeLabel = (Label) parentNode.lookup("#" + COUPON_CODE_LABEL_ID) ;
                Label couponDiscountLabel = (Label) parentNode.lookup("#" + COUPON_DISCOUNT_LABEL_ID) ;

                couponCodeLabel.setText(codeString + item.getExternalCouponCode());

                String discountSymbol = "" ;
                if (item.getExternalCouponType().compareTo("subtraction") == 0) discountSymbol = EURO_SYMBOL ;
                else discountSymbol = "%" ;

                couponDiscountLabel.setText(discountString + (item.getCouponDiscount()) + discountSymbol);

                if (caller.equals(FIRST_VIEW)) {
                    setGraphic(parentNode);
                }
                else {
                    setText(String.format("Discount: -%1$4.2f\t\t%2$1s\t\tCode: %3$4s", item.getCouponDiscount(), (item.getExternalCouponType().compareTo("subtraction") == 0) ? EURO_SYMBOL : "%", item.getExternalCouponCode()));
                    setStyle("-fx-font-size: 14 ; -fx-alignment: center");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            setGraphic(null);
            setText(null);
        }
    }
}
