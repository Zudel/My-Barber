package engineering.bean;

public class ProductSearchInfoBean {

    private String productName ;

    public ProductSearchInfoBean(String productName) {
        setProductName(productName);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
