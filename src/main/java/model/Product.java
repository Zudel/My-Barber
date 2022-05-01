package model;

import java.io.Serializable;

public class Product implements Serializable {

    private Integer isbn ;
    private String name ;
    private String description ;
    private Double price ;
    private Barber vendor ;

    public Product() {
        this(0,"","", 0.0, null) ;
    }

    public Product(Integer isbn, String name, String description, Double price, Barber vendor) {
        setIsbn(isbn);
        setName(name);
        setDescription(description);
        setPrice(price);
        setVendor(vendor);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Barber getVendor() {
        return vendor;
    }

    public String getVendorEmail() {
        return vendor.getEmail() ;
    }

    public void setVendor(Barber vendor) {
        this.vendor = vendor;
    }


    @Override
    public boolean equals(Object toCompare) {
        Boolean result ;
        if (this == toCompare) result = true ;
        else if (toCompare instanceof Product) {
            result = (Integer.compare(this.getIsbn(), ((Product) toCompare).getIsbn()) == 0) ;
        }
        else result = false ;
        return result ;
    }

    @Override
    public int hashCode(){
        return super.hashCode();
    }
}
