package model;

public class SubtractionCoupon extends Coupon{
    //Represents one of the Concrete Decorator of pattern: it applied its decoration by subtraction of its discount

    public SubtractionCoupon(Integer couponCode, Double couponDiscount) {
        super(couponCode, couponDiscount, SUBTRACTION_TYPE) ;
    }

    @Override
    public Double getPrice() {
        return getAppliedPrice().getPrice() - getCouponDiscount() ;
    }
}
