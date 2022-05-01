package model;

import engineering.pattern.decorator.Priceable;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Priceable, Serializable {
    /*
        This class has been built with the aim of manage a list of cart rows and take away responsibility of add/remove
        product from the application controller.
        It realizes Priceable interface as concrete component of Decorator Pattern.
     */

    private final ArrayList<CartRow> cartRowArrayList ;

    public Cart() {
        cartRowArrayList = new ArrayList<>() ;
    }

    public void insertProduct(Product newProduct) {
        CartRow productRow = verifyPresent(newProduct) ;
        if (productRow == null) {
            cartRowArrayList.add(new CartRow(newProduct, 1)) ;
        }
        else {
            productRow.setQuantity(productRow.getQuantity() + 1);
        }
    }

    public void removeProduct(Product rmvProduct) {
        CartRow productRow = verifyPresent(rmvProduct) ;
        if (productRow != null) {
            productRow.setQuantity(productRow.getQuantity() - 1);
            if (productRow.getQuantity() == 0) {
                cartRowArrayList.remove(productRow) ;
            }
        }
    }


    @Nullable
    private CartRow verifyPresent(Product verifyProduct) {
        for (CartRow row : cartRowArrayList) {
            if (row.getProduct().equals(verifyProduct)) {
                return row ;
            }
        }
        return null ;
    }

    public ArrayList<String> getVendorsInfo() {
        //Return list of vendors' email addresses in order to make the controller send emails to vendors
        ArrayList<String> vendorsInfo = new ArrayList<>() ;
        for (CartRow cartRow : cartRowArrayList) {
            if (!vendorsInfo.contains(cartRow.getProductVendor())) {
                vendorsInfo.add(cartRow.getProductVendor()) ;
            }
        }
        return vendorsInfo ;
    }


    public List<CartRow> getCartRowArrayList() {
        return this.cartRowArrayList ;
    }

    @Override
    public Double getPrice() {
        Double total = 0.0 ;
        for (CartRow row : cartRowArrayList) {
            total = total + row.getSubTotal() ;
        }
        return total ;
    }
}
