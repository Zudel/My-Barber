package model;

public class Service {

    private String serviceName;
    private String serviceDescription;
    private double servicePrice;
    private Product serviceUsedProduct;
    private Barber barber;

    public Service () {
        this("", "", 0.0D, null);
    }

    public Service (String serviceName, String serviceDescription, double servicePrice) {

        this.setServiceName(serviceName);
        this.setServiceDescription(serviceDescription);
        this.setServicePrice(servicePrice);

    }

    public Service (String name, String description, double price, Product usedProduct) {

        this.setServiceName(name) ;
        this.setServiceDescription(description) ;
        this.setServicePrice(price);
        this.setServiceUsedProduct(usedProduct);
    }

    public String getServiceName(){

        return serviceName;

    }

    public void setServiceName(String serviceName){

        this.serviceName = serviceName;

    }

    public String getServiceDescription(){

        return serviceDescription;

    }

    public  void setServiceDescription(String serviceDescription){

        this.serviceDescription = serviceDescription;

    }

    public Double getServicePrice(){

        return servicePrice;

    }

    public void setServicePrice(double servicePrice){

        this.servicePrice = servicePrice;

    }

    public void setServiceUsedProduct(Product serviceUsedProduct) {

        this.serviceUsedProduct = serviceUsedProduct;

    }

    public Product getServiceUsedProduct() {

        return this.serviceUsedProduct;

    }

    public Barber getBarber() {
        return barber;
    }

    public void setBarber(Barber barber) {
        this.barber = barber;
    }
}
