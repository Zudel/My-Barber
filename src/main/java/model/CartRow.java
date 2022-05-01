package model;

import java.io.Serializable;

public class CartRow implements Serializable {
    /*
        This class represents a brought product with its quantity. Its responsibility is to encapsulate product and
        quantity in order to calculate total price
     */

    private Product product ;
    private Integer quantity ;

    public CartRow(Product product, Integer quantity) {
        setProduct(product);
        setQuantity(quantity);
    }

    public Double getSubTotal() {
        return product.getPrice() * quantity ;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProductIsbn() {
        return product.getIsbn() ;
    }

    public String getProductName() {
        return product.getName() ;
    }


    public Double getProductPrice() {
        return product.getPrice() ;
    }

    public String getProductVendor() {
        return product.getVendorEmail() ;
    }
}
