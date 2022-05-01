package engineering.bean;

import engineering.exception.IncorrectFormatException;
import engineering.exception.InsertNegativePriceException;
import engineering.exception.NegativePriceException;
import engineering.pattern.observer.Subject;

public class ServiceBean extends Subject {

    private String nameInfo;
    private String descriptionInfo;
    private String nameOfUsedProductInfo;
    private Double priceInfo;

    public ServiceBean(){
    }

    public ServiceBean (String name, String description, String nameOfUsedProduct, String price) throws IncorrectFormatException {

        super();
        this.setNameInfo(name);
        this.setDescriptionInfo(description);
        this.setPriceInfo(price);
        this.setNameOfUsedProductInfo(nameOfUsedProduct);

    }

    public void setNameInfo(String nameInfo) {

        this.nameInfo = nameInfo;

    }

    public String getNameInfo() {

        return this.nameInfo;

    }

    public void setDescriptionInfo(String descriptionInfo) {

        this.descriptionInfo = descriptionInfo;

    }

    public String getDescriptionInfo() {

        return descriptionInfo;

    }

    public void setPriceInfo(String priceInfo) throws IncorrectFormatException {

        try {
            this.priceInfo = Double.parseDouble(priceInfo);
        }catch (NumberFormatException e) {
            throw new IncorrectFormatException();
        }
    }

    public Double getPriceInfo() {

        return priceInfo;

    }

    public String getNameOfUsedProductInfo() {

        return nameOfUsedProductInfo;

    }

    public void setNameOfUsedProductInfo(String nameOfUsedProductInfo) {

        this.nameOfUsedProductInfo = nameOfUsedProductInfo;

    }


    public void notifyChanges(){

        super.notifyObservers();

    }
}
