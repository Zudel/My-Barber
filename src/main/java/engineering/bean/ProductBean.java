package engineering.bean;

public class ProductBean {

    private Integer beanIsbn;
    private String beanName;
    private String beanDescription;
    private Double beanPrice;

    public ProductBean() {
        this(0,"","",0.0) ;
    }

    public ProductBean(Integer isbn) {
        this(isbn, "", "", 0.0) ;
    }

    public ProductBean(Integer isbn, String name, String description, Double price) {
        setBeanIsbn(isbn);
        setBeanName(name);
        setBeanDescription(description);
        setBeanPrice(price);
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanDescription() {
        return beanDescription;
    }

    public void setBeanDescription(String beanDescription) {
        this.beanDescription = beanDescription;
    }

    public Double getBeanPrice() {
        return beanPrice;
    }

    public void setBeanPrice(Double beanPrice) {
        this.beanPrice = beanPrice;
    }


    public Integer getBeanIsbn() {
        return beanIsbn;
    }

    public void setBeanIsbn(Integer beanIsbn) {
        this.beanIsbn = beanIsbn;
    }
}
