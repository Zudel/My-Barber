package test;

import engineering.exception.InvalidCouponException;
import engineering.exception.NegativePriceException;
import engineering.pattern.decorator.Priceable;
import javafx.util.Pair;
import model.Coupon;
import model.CouponApplier;
import model.PercentageCoupon;
import model.SubtractionCoupon;
import org.junit.jupiter.api.Test;

import java.util.List;

import static model.Coupon.PERCENTAGE_TYPE;
import static model.Coupon.SUBTRACTION_TYPE;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestCouponApplier {
    //Responsabile Testing: Simone Nicosanti ; Matricola 0266910

    private static final List<Pair<Double, Integer>> COUPON_MAP_DISCOUNT_TYPE = List.of(
            new Pair<>(45.0, PERCENTAGE_TYPE), new Pair<>(23.0, PERCENTAGE_TYPE), new Pair<>(23.0, SUBTRACTION_TYPE), new Pair<>(12.0, SUBTRACTION_TYPE))  ;

    private static final Double TEST_DELTA = 0.001 ;


    @Test
    public void testCouponApplierGetFinalPriceNotCorrect() throws NegativePriceException, InvalidCouponException {
        Priceable testPriceable = TestCart.createTestCart() ;
        CouponApplier couponApplier = new CouponApplier(testPriceable) ;

        int i = 0 ;
        for (Pair<Double,Integer> couponPair : COUPON_MAP_DISCOUNT_TYPE) {
            Coupon coupon = createTestCoupon(i, couponPair) ;
            couponApplier.applyCoupon(coupon);
            i++ ;
        }

        Double applierPrice = couponApplier.getFinalPrice() ;

        Double testPrice = testPriceable.getPrice() ;
        for (Pair<Double,Integer> couponPair : COUPON_MAP_DISCOUNT_TYPE) {
            Integer type = couponPair.getValue() ;
            if (type.equals(SUBTRACTION_TYPE)) testPrice = testPrice - couponPair.getKey() ;
            else testPrice = testPrice * (1 - couponPair.getKey() / 100.0) ;
        }

        //We attend that values are not equals: by system rule, we apply first subtraction coupon then percentage; here are applied in reverse
        assertNotEquals(testPrice, applierPrice, TEST_DELTA);
    }

    @Test
    public void testCouponApplierApplyCouponThrowsNegativePriceException() {
        Priceable testPriceable = TestCart.createTestCart() ;
        CouponApplier couponApplier = new CouponApplier(testPriceable) ;

        Coupon coupon1 = new SubtractionCoupon(0, 10000.0) ;
        assertThrows(NegativePriceException.class, () -> couponApplier.applyCoupon(coupon1)) ;

    }


    @Test
    public void testCouponApplierApplyCouponThrowsInvalidCouponException() throws NegativePriceException, InvalidCouponException {
        Priceable testPriceable = TestCart.createTestCart() ;
        CouponApplier couponApplier = new CouponApplier(testPriceable) ;

        Coupon coupon2 = new SubtractionCoupon(1, 15.0) ;
        couponApplier.applyCoupon(coupon2);

        Coupon coupon3 = new SubtractionCoupon(1, 23.0) ;
        assertThrows(InvalidCouponException.class, () -> couponApplier.applyCoupon(coupon3)) ;
    }

    private Coupon createTestCoupon(Integer couponCode, Pair<Double, Integer> couponPair) {
        Integer couponType = couponPair.getValue() ;
        Coupon coupon ;
        if (couponType.equals(SUBTRACTION_TYPE)) coupon = new SubtractionCoupon(couponCode, couponPair.getKey()) ;
        else coupon = new PercentageCoupon(couponCode, couponPair.getKey()) ;

        return coupon ;
    }
}
