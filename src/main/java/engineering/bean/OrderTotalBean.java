package engineering.bean;

import java.util.ArrayList;
import java.util.List;

public class OrderTotalBean {

    private Double orderTotal ;
    private List<Integer> couponCodes ;
    private Integer orderPoints ;

    public OrderTotalBean(Double oderTotal, List<Integer> couponCodes, Integer orderPoints) {
        setOrderTotal(oderTotal);
        setCouponCodes(couponCodes);
        setOrderPoints(orderPoints);
    }

    public OrderTotalBean(Double orderTotal) {
        this(orderTotal, null, 0) ;
    }


    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public List<Integer> getInternalCouponCodes() {
        return couponCodes;
    }

    public List<String> getExternalCouponCodes() {
        List<String> externalCouponCodes = new ArrayList<>() ;
        for (Integer code : couponCodes) {
            externalCouponCodes.add(Integer.toString(code)) ;
        }
        return externalCouponCodes ;
    }

    public void setCouponCodes(List<Integer> couponCodes) {
        this.couponCodes = couponCodes;
    }

    public Integer getOrderPoints() {
        return orderPoints;
    }

    public void setOrderPoints(Integer orderPoints) {
        this.orderPoints = orderPoints;
    }
}
