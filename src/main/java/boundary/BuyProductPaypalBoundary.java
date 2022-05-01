package boundary;

import engineering.bean.OrderTotalBean;

import java.io.FileWriter;
import java.io.IOException;

public class BuyProductPaypalBoundary {

    private static final String PAYPAL_FILE = "paypalFile" ;

    public void pay(OrderTotalBean orderTotalBean) {

        try(FileWriter fileWriter = new FileWriter(PAYPAL_FILE)) {
            fileWriter.write("Pagamento totale dell'ordine: " + orderTotalBean.getOrderTotal());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
