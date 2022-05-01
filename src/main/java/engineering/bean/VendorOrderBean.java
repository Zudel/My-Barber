package engineering.bean;

import java.lang.constant.Constable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VendorOrderBean {

    public static final String VENDOR_ORDER_YEAR_KEY = "YEAR" ;
    public static final String VENDOR_ORDER_MONTH_KEY = "MONTH" ;
    public static final String VENDOR_ORDER_DAY_KEY = "DAY" ;

    private String vendor ;
    private String address ;
    private String telephone ;
    private String orderOwner ;
    private LocalDate date ;
    private Integer orderCode ;


    public VendorOrderBean(String vendor, String address, String telephone, LocalDate date, String orderOwner, Integer orderCode) {
        setVendor(vendor);
        setAddress(address);
        setVendor(vendor);
        setDate(date);
        setTelephone(telephone);
        setOrderOwner(orderOwner);
        setOrderCode(orderCode);
    }


    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public String getOrderOwner() {
        return orderOwner;
    }

    public void setOrderOwner(String orderOwner) {
        this.orderOwner = orderOwner;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<String, Integer> getExternalDate() {
        return Map.of(VENDOR_ORDER_YEAR_KEY, date.getYear(), VENDOR_ORDER_MONTH_KEY, date.getMonth().getValue(), VENDOR_ORDER_DAY_KEY, date.getDayOfMonth()) ;
    }

    public Integer getOrderCode() {
        return orderCode;
    }

    public String getExternalOrderCode() {
        return Integer.toString(orderCode) ;
    }

    public void setOrderCode(Integer orderCode) {
        this.orderCode = orderCode;
    }
}
