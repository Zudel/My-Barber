package model;

import engineering.pattern.decorator.Priceable;

public abstract class Coupon implements Priceable, Comparable<Coupon> {
    /*
        Abstract class that represent the Coupon entity and the Abstract Decorator in decorator Pattern.
        This class implements Priceable with the aim of implement decorator pattern: is not given a method to getPrice
        operation as pattern says .
        The Comparable interface is realized with the aim of make that same coupons applied in different order give
        the same result with the application of decorator pattern. Coupons are sorted based on type and price: we have
        first Subtraction type coupons and then Percentage; with parity of type, they are sorted with crescent discount
     */

    private Integer couponCode ;
    // This represents the aggregated compont of decorator pattern
    private Priceable appliedPrice ;
    private Double couponDiscount ;
    private Integer couponType ;

    public static final Integer SUBTRACTION_TYPE = 0 ;
    public static final Integer PERCENTAGE_TYPE = 1 ;

    protected Coupon(Integer couponCode, Double couponDiscount) {
        setCouponCode(couponCode);
        setCouponDiscount(couponDiscount);
    }

    protected Coupon(Integer couponCode, Double couponDiscount, Integer couponType) {
        setCouponCode(couponCode);
        setCouponDiscount(couponDiscount);
        setCouponType(couponType);
    }

    public Integer getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(Integer couponCode) {
        this.couponCode = couponCode;
    }

    public void setAppliedPrice(Priceable appliedPrice) {
        //This operation allows to set the Priceable on which the coupon must be applied
        this.appliedPrice = appliedPrice;
    }

    public Double getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(Double couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public Integer getCouponType() {
        return couponType ;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType ;
    }

    protected Priceable getAppliedPrice() {
        return appliedPrice ;
    }

    @Override
    public int compareTo(Coupon toCompare) {
        Integer typeComparison = Integer.compare(this.couponType, toCompare.getCouponType()) ;
        if (typeComparison != 0) return typeComparison ;
        else {
            return Double.compare(this.getCouponDiscount(), toCompare.getCouponDiscount());
        }
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) ;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
