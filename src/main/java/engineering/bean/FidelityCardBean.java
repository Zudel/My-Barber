package engineering.bean;

import java.util.List;

public class FidelityCardBean {

    private Integer pointsSale ;
    private List<CouponBean> couponBeans ;
    private String owner ;

    public FidelityCardBean(Integer pointsSale, List<CouponBean> couponBeans, String owner) {
        setPointsSale(pointsSale);
        setCouponBeans(couponBeans);
        setOwner(owner);
    }

    public Integer getPointsSale() {
        return pointsSale;
    }

    public void setPointsSale(Integer pointsSale) {
        this.pointsSale = pointsSale;
    }

    public List<CouponBean> getCouponBeans() {
        return couponBeans;
    }

    public void setCouponBeans(List<CouponBean> couponBeans) {
        this.couponBeans = couponBeans;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
