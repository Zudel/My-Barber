package application_controller;

import boundary.BarberManageOrderEMailSystemBoundary;
import engineering.bean.UserBean;
import engineering.bean.CartRowBean;
import engineering.bean.VendorOrderBean;
import engineering.dao.BarberDAO;
import engineering.dao.OrderDAO;
import model.User;
import model.CartRow;
import model.Order;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BarberManageOrderController {

    private final User barber;

    private List<Order> orderList ;

    public BarberManageOrderController(UserBean loggedUser) {
        BarberDAO barberDAO = new BarberDAO() ;
        barber = barberDAO.loadBarber(loggedUser.getUserEmail()) ;
    }

    public List<VendorOrderBean> showAllOrders() {
        OrderDAO orderDAO = new OrderDAO() ;
        orderList = orderDAO.loadAllOrdersByBarber(barber.getEmail()) ;

        List<VendorOrderBean> vendorOrderBeans = new ArrayList<>() ;
        for (Order order : orderList) {
            VendorOrderBean vendorOrderBean = new VendorOrderBean(barber.getEmail(), order.getAddress(), order.getTelephone(), order.getDate(), order.getOwnerEmail(), order.getOrderCode()) ;
            vendorOrderBeans.add(vendorOrderBean) ;
        }

        return vendorOrderBeans ;
    }


    public List<CartRowBean> showOrderCart(VendorOrderBean vendorOrderBean) {
        for (Order order : orderList) {
            if (order.getOrderCode() == vendorOrderBean.getOrderCode()) {
                return buildCartRowBean(order) ;
            }
        }
        return new ArrayList<>() ;
    }

    private List<CartRowBean> buildCartRowBean(Order order) {
        List<CartRow> cartRows = order.getCartRows() ;

        List<CartRowBean> cartRowBeans = new ArrayList<>() ;
        for (CartRow cartRow : cartRows) {
            cartRowBeans.add(new CartRowBean(cartRow.getQuantity(), cartRow.getProductIsbn(), cartRow.getProductName(), cartRow.getProductPrice())) ;
        }
        return cartRowBeans ;
    }

    public void confirmOrder(VendorOrderBean vendorOrderBean) {
        Order order = findOrder(vendorOrderBean.getOrderCode()) ;
        if (order != null) {
            List<CartRowBean> cartRowBeans = buildCartRowBean(order);

            BarberManageOrderEMailSystemBoundary barberManageOrderEMailSystemBoundary = new BarberManageOrderEMailSystemBoundary();
            barberManageOrderEMailSystemBoundary.sendEmail(vendorOrderBean, cartRowBeans);
        }
    }

    @Nullable
    private Order findOrder(Integer orderCode) {
        for (Order order : orderList) {
            if (order.getOrderCode() == orderCode) {
                return order ;
            }
        }
        return null ;
    }
}
