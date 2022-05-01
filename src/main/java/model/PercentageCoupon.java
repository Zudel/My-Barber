package model;

public class PercentageCoupon extends Coupon{
    /*
        Represents one of the Concrete Decorator of pattern: it applied its decoration by subtraction of percentage
        of its discount
     */

    public PercentageCoupon(Integer couponCode, Double couponDiscount) {
        super(couponCode, couponDiscount, PERCENTAGE_TYPE) ;
    }

    @Override
    public Double getPrice() {
        Double finalPrice = getAppliedPrice().getPrice() * (1 - getCouponDiscount() / 100.0) ;
        return Math.round(finalPrice * 100) / 100.0;
    }
}
