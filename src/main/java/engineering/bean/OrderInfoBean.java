package engineering.bean;

import engineering.exception.IncorrectFormatException;

public class OrderInfoBean {

    private String addressInfo;
    private String telephoneInfo;

    public OrderInfoBean(String address, String telephone) throws IncorrectFormatException {
        setAddressInfo(address);
        setTelephoneInfo(telephone);
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getTelephoneInfo() {
        return telephoneInfo;
    }

    public void setTelephoneInfo(String telephoneInfo) throws IncorrectFormatException {
        if (!telephoneInfo.matches("[0-9]{10}")) throw new IncorrectFormatException("FORMATO NUMERO DI TELEFONO NON VALIDO") ;
        this.telephoneInfo = telephoneInfo ;
    }
}
