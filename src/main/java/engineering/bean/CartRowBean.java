package engineering.bean;

public class CartRowBean {

    private Integer quantity ;
    private Integer isbn ;
    private String name ;
    private Double price ;


    public CartRowBean(Integer quantity, Integer isbn, String name, Double price) {
        setQuantity(quantity);
        setIsbn(isbn);
        setName(name);
        setPrice(price);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
