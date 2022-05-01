package application_controller;

import boundary.BuyProductEMailSystemBoundary;
import boundary.BuyProductPaypalBoundary;
import engineering.bean.*;
import engineering.dao.*;
import engineering.exception.InvalidCouponException;
import engineering.exception.NegativePriceException;
import engineering.exception.NotExistentUserException;
import model.Customer;
import model.Cart;
import model.CartRow;
import model.Order;
import model.Product;
import engineering.container.ProductCatalog;
import model.Coupon;
import model.CouponApplier;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BuyProductController {

    private final ProductCatalog productCatalog ;
    private Cart cart ;
    private Customer customer;
    private final CouponApplier couponApplier ;

    private CartFileSaver cartFileSaver ;


    public BuyProductController(UserBean loggedUserBean) {
        ProductDAO productDAO = new ProductDAO();
        productCatalog = productDAO.loadAllProducts() ;

        try {
            //If the user is logged we check if there is a previous cart saved
            loadCustomer(loggedUserBean);
            cart = cartFileSaver.loadCartFromFile() ;
        } catch (NotExistentUserException e) {
            cart = new Cart() ;
        }
        couponApplier = new CouponApplier(cart) ;
    }

    public List<ProductBean> filterProductList(ProductSearchInfoBean searchInfoBean) {
        String searchName = searchInfoBean.getProductName() ;
        List<Product> productArrayList = productCatalog.filterByName(searchName) ;
        return createArrayProductBean(productArrayList) ;
    }

    private List<ProductBean> createArrayProductBean(List<Product> productArrayList) {
        List<ProductBean> productBeanArrayList = new ArrayList<>() ;
        for (Product product : productArrayList) {
            ProductBean productBean = new ProductBean(product.getIsbn(), product.getName(), product.getDescription(), product.getPrice()) ;
            productBeanArrayList.add(productBean) ;
        }
        return productBeanArrayList ;
    }

    public CartBean insertProductToCart(ProductBean productBean) {
        // We retrieve the Product from product catalog using ISBN and then add it to cart
        Product product = productCatalog.getProductByIsbn(productBean.getBeanIsbn()) ;
        cart.insertProduct(product);
        if (customer != null) cartFileSaver.saveCartInFile(cart);

        return createCartBean() ;
    }

    public CartBean showCart() {
        return createCartBean() ;
    }

    private CartBean createCartBean() {
        List<CartRow> cartRows = cart.getCartRowArrayList() ;
        CartBean cartBean = new CartBean() ;
        cartBean.setTotal(cart.getPrice());
        List<CartRowBean> cartRowBeans = new ArrayList<>() ;
        for (CartRow cartRow : cartRows) {
            cartRowBeans.add(new CartRowBean(cartRow.getQuantity(), cartRow.getProductIsbn(), cartRow.getProductName(), cartRow.getProductPrice())) ;
        }
        cartBean.setCartRowBeanArrayList(cartRowBeans);
        return cartBean;
    }


    public CartBean removeProductFromCart(ProductBean productBean) {
        Product rmvProduct = productCatalog.getProductByIsbn(productBean.getBeanIsbn()) ;
        cart.removeProduct(rmvProduct);
        if (customer != null) cartFileSaver.saveCartInFile(cart);

        return createCartBean() ;
    }

    public OrderTotalBean applyCoupon(CouponBean couponBean) throws InvalidCouponException, NegativePriceException {
        //Load coupon from DB and then apply it to the cart price with the CouponApplier instance
        CouponDAO couponDAO = new CouponDAO() ;
        Coupon myCoupon = couponDAO.loadCouponByCode(couponBean.getCouponCode());
        couponApplier.applyCoupon(myCoupon);
        Double cartPrice = cart.getPrice() ;
        Integer orderPoints = (int) Math.round(cartPrice) ;
        return new OrderTotalBean(couponApplier.getFinalPrice(), couponApplier.getAppliedCouponCode(), orderPoints) ;
    }

    public void completeOrder(OrderInfoBean orderInfoBean) {
        //Create and set Order Info; then save it in DB
        Order order = new Order() ;
        order.setAddress(orderInfoBean.getAddressInfo());
        order.setTelephone(orderInfoBean.getTelephoneInfo());
        order.setDate(LocalDate.now());
        order.setFinalPrice(couponApplier.getFinalPrice());
        order.setOrderOwner(customer);
        order.setCartRows(cart.getCartRowArrayList());

        OrderDAO orderDAO = new OrderDAO() ;
        orderDAO.saveOrder(order);

        //Invalidate of all used coupons
        CouponDAO couponDAO = new CouponDAO() ;
        couponDAO.invalidateAllCoupon(couponApplier.getCouponContainer());

        //Delete of partial cart: the order is completed so there is no need to maintain it
        cartFileSaver.deleteCartFromFile() ;

        //Update customer points in DB
        CustomerDAO customerDAO = new CustomerDAO() ;
        Double cartPrice = cart.getPrice() ;
        Integer orderPoints = (int) Math.round(cartPrice) ;
        Integer finalPoints;
        try {
            finalPoints = Math.addExact(customer.getCardPoints(), orderPoints) ;
        }
        catch (ArithmeticException arithmeticException) {
            finalPoints = Integer.MAX_VALUE ;
        }
        customer.setCardPoints(finalPoints);
        customerDAO.updateCustomerPoints(customer);

        // Send notifications to Vendors and contact Payment System
        BuyProductPaypalBoundary paypalBoundary = new BuyProductPaypalBoundary() ;
        paypalBoundary.pay(new OrderTotalBean(couponApplier.getFinalPrice()));

        BuyProductEMailSystemBoundary eMailSystemBoundary = new BuyProductEMailSystemBoundary() ;
        ArrayList<String> vendorsInfo = cart.getVendorsInfo();
        for (String vendor : vendorsInfo) {
            eMailSystemBoundary.notifyVendors(createNotificationInfo(vendor, order));
        }
    }

    private VendorOrderBean createNotificationInfo(String vendor, Order order) {
        Calendar calendar = Calendar.getInstance() ;
        calendar.setTime(Date.from(Instant.now()));

        return new VendorOrderBean(vendor, order.getAddress(), order.getTelephone(), order.getDate(), order.getOwnerEmail(), order.getOrderCode()) ;
    }

    public OrderTotalBean showOrder(){
        couponApplier.reset() ;
        Double cartPrice = cart.getPrice() ;
        Integer orderPoints = (int) Math.round(cartPrice) ;
        return new OrderTotalBean(couponApplier.getFinalPrice(), couponApplier.getAppliedCouponCode(), orderPoints) ;
    }

    public void loadCustomer(UserBean userBean) throws NotExistentUserException {
        if (userBean == null) throw new NotExistentUserException() ;

        CustomerDAO customerDAO = new CustomerDAO() ;
        customer = customerDAO.loadCustomerByEmail(userBean.getUserEmail()) ;
        cartFileSaver = new CartFileSaver(customer.getEmail()) ;

        if (cart != null) cartFileSaver.saveCartInFile(cart);
    }

    public CartBean changeProductQuantity(CartRowBean cartRowBean, Integer changer) {
        ProductBean productBean = new ProductBean(cartRowBean.getIsbn()) ;
        if (changer > 0) {
            return insertProductToCart(productBean) ;
        }
        else return removeProductFromCart(productBean) ;
    }

}
