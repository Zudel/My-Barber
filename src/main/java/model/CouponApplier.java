package model;

import engineering.exception.InvalidCouponException;
import engineering.exception.NegativePriceException;
import engineering.pattern.decorator.Priceable;
import engineering.container.CouponContainer;

import java.util.ArrayList;
import java.util.List;

public class CouponApplier {
    //This class has been built with the responsibility of apply Decorator Pattern.

    private Priceable originalPrice ;
    private Priceable finalPrice ;

    private final CouponContainer couponContainer ;

    public CouponApplier(Priceable priceable) {
        // Creates a coupon container in order to manage all the Coupons that are added to the price
        setOriginalPrice(priceable);
        couponContainer = new CouponContainer() ;
        finalPrice = originalPrice ;
    }

    public void setOriginalPrice(Priceable originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getFinalPrice() {
        // Access to the finalPrice attribute: this starts recursive calls of Decorator Pattern
        return finalPrice.getPrice() ;
    }

    public void applyCoupon(Coupon coupon) throws InvalidCouponException, NegativePriceException {
        if (couponContainer.verifyPresent(coupon) == null) {
            couponContainer.addCoupon(coupon);
            try {
                applyDiscount() ;
            } catch (NegativePriceException negativePriceException) {
                /*
                    If the exception is thrown, the last coupon is removed from the container and the discount is
                    applied again: the removal of the last coupon ensure that the price won't be negative again
                 */
                couponContainer.removeCoupon(coupon);
                applyDiscount();
                throw negativePriceException ;
            }
        }
        else {
            throw new InvalidCouponException("ALREADY USED COUPON!!") ;
        }
    }

    private void applyDiscount() throws NegativePriceException {
        /*
            Decorator application.
            The finalPrice is started as original price. Then the coupons are retrieved from the coupon container
            (the order in which they are retrieved is defined by Coupon class implements Comparable).
            One to one is setted the applyPrice of the coupon to the actual finalPrice and then finalPrice is setted as
            the current coupon
         */
        finalPrice = originalPrice ;
        for (int i = 0 ; i < couponContainer.getSize() ; i++) {
            Coupon currentCoupon = couponContainer.getCouponByIndex(i) ;
            if (currentCoupon != null) {
                currentCoupon.setAppliedPrice(finalPrice);
                if (currentCoupon.getPrice() < 0) {
                    // If the finalPrice is negative, then an Exception is thrown
                    throw new NegativePriceException("THE GIVEN COUPON MAKES PRICE NEGATIVE!!") ;
                }
                finalPrice = currentCoupon ;
            }
        }
    }

    public List<Integer> getAppliedCouponCode() {
        List<Integer> couponCodes = new ArrayList<>() ;
        for (int i = 0 ; i < couponContainer.getSize() ; i++) {
            if (couponContainer.getCouponByIndex(i) != null) couponCodes.add(couponContainer.getCouponByIndex(i).getCouponCode()) ;
        }
        return couponCodes ;
    }

    public void reset() {
        finalPrice = originalPrice ;
        couponContainer.clear();
    }

    public CouponContainer getCouponContainer() {
        return couponContainer ;
    }
}
