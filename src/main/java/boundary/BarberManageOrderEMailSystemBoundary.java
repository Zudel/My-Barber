package boundary;

import engineering.bean.CartRowBean;
import engineering.bean.VendorOrderBean;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class BarberManageOrderEMailSystemBoundary {

    private static final String CONFIRM_ORDER_FILE_NAME = "confirmOrderFile" ;

    public void sendEmail(VendorOrderBean vendorOrderBean, List<CartRowBean> cartRowBeanList) {

        try(Writer fileWriter = new FileWriter(CONFIRM_ORDER_FILE_NAME) ;) {

            fileWriter.write(String.format("""
                Caro %s!!
                La informiamo che l'ordine numero %d fatto presso il rivenditore %s è stato confermato!
                Di seguito i prodotti dell'ordine confermati!
                Cordiali saluti, il team MyBarber!
                
                """, vendorOrderBean.getOrderOwner(), vendorOrderBean.getOrderCode(), vendorOrderBean.getVendor()));

            for (CartRowBean cartRowBean : cartRowBeanList) {
                fileWriter.write(String.format("Prodotto: %s\nQuantità: %s\n\n", cartRowBean.getName(), cartRowBean.getQuantity()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
