package engineering.bean;

import java.util.ArrayList;
import java.util.List;

public class CartBean {

    private Double total ;
    private List<CartRowBean> cartRowBeanArrayList ;

    public CartBean() {
        this(0.0, new ArrayList<>()) ;
    }


    public CartBean(Double total, List<CartRowBean> cartRowBeans) {
        setTotal(total);
        setCartRowBeanArrayList(cartRowBeans) ;

    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<CartRowBean> getCartRowBeanArrayList() {
        return cartRowBeanArrayList;
    }

    public void setCartRowBeanArrayList(List<CartRowBean> cartRowBeanArrayList) {
        this.cartRowBeanArrayList = cartRowBeanArrayList;
    }

}
