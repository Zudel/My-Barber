package engineering.container;

import model.Coupon;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CouponContainer {
    /*
        Engineering class built to manage a list of coupons and take responsibility of iterate or search or add away from
        other classes that have a list of product
     */


    private final List<Coupon> couponArrayList ;

    public CouponContainer() {
        couponArrayList = new ArrayList<>() ;
    }

    public CouponContainer(List<Coupon> coupons) {
        this.couponArrayList = coupons ;
        this.couponArrayList.sort(null);
    }

    public void addCoupon(Coupon newCoupon){
        if (verifyPresent(newCoupon) == null) {
            couponArrayList.add(newCoupon);
            /*
                When a coupon is added the coupon list is sort as specified by the Coupon class.
                This is important to make that same coupons applied in different order give the same result
             */
            couponArrayList.sort(null);
        }
    }

    public void removeCoupon(Coupon rmvCoupon) {
        Coupon presentCoupon = verifyPresent(rmvCoupon) ;
        if (presentCoupon != null) {
            couponArrayList.remove(presentCoupon) ;
        }
    }

    @Nullable
    public Coupon verifyPresent(Coupon verifyCoupon) {
        for (Coupon coupon : couponArrayList) {
            if (coupon.getCouponCode().equals(verifyCoupon.getCouponCode())) {
                return coupon ;
            }
        }
        return null ;
    }

    @Nullable
    public Coupon getCouponByIndex(Integer index) {
        if (index < couponArrayList.size()) {
            return couponArrayList.get(index) ;
        }
        return null ;
    }

    public Integer getSize() {
        return couponArrayList.size() ;
    }

    public void clear() {
        couponArrayList.clear();
    }
}
