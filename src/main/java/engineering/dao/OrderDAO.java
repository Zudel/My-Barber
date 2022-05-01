package engineering.dao;

import engineering.exception.NotExistentUserException;
import engineering.pattern.Connector;
import model.Customer;
import model.CartRow;
import model.Order;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private static final String ORDER_ID_LAB = "id" ;
    private static final String ORDER_DATE_LAB = "orderDate" ;
    private static final String ORDER_CUSTOMER_LAB = "customer" ;
    private static final String ORDER_ADDRESS_LAB = "orderAddress" ;
    private static final String ORDER_TELEPHONE_LAB = "orderTelephone" ;


    public void saveOrder(Order order) {

        Connection connection = Connector.getConnectorInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO CustomerOrder (orderDate, orderTotal, orderAddress, orderTelephone, customer) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);) {

            statement.setDate(1, Date.valueOf(order.getDate()));
            statement.setDouble(2, order.getFinalPrice());
            statement.setString(3, order.getAddress());
            statement.setString(4, order.getTelephone());
            statement.setString(5, order.getOwnerEmail());

            statement.executeUpdate() ;
            try(ResultSet generatedKeys = statement.getGeneratedKeys() ;) {
                if (generatedKeys.next()) {
                    Integer orderKey = generatedKeys.getInt(1);
                    order.setOrderCode(orderKey);
                }
            }

            // Save cart of this order
            CartDAO cartDAO = new CartDAO() ;
            cartDAO.saveCart(order.getCartRows(), order.getOrderCode());

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public List<Order> loadAllOrdersByBarber(String vendor) {
        Connection connection = Connector.getConnectorInstance().getConnection();
        List<Order> orderList = new ArrayList<>() ;

        try(
                Statement statement = connection.createStatement() ;
                ResultSet resultSet = Queries.selectOrderByVendor(statement, vendor) ;
                ) {

            CartDAO cartDAO = new CartDAO() ;
            while (resultSet.next()) {
                Order order = createOrder(resultSet) ;
                List<CartRow> cartRows = cartDAO.loadCartByOrderCodeAndVendor(order.getOrderCode(), vendor) ;
                order.setCartRows(cartRows);
                orderList.add(order) ;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return orderList ;
    }


    private Order createOrder(ResultSet resultSet) throws SQLException {
        Integer orderCode = resultSet.getInt(ORDER_ID_LAB) ;
        String orderAddress = resultSet.getString(ORDER_ADDRESS_LAB) ;
        String orderTelephone = resultSet.getString(ORDER_TELEPHONE_LAB) ;
        String ownerEmail = resultSet.getString(ORDER_CUSTOMER_LAB) ;
        LocalDate orderDate = resultSet.getDate(ORDER_DATE_LAB).toLocalDate() ;

        CustomerDAO customerDAO = new CustomerDAO() ;
        Customer customer = null;
        try {
            customer = customerDAO.loadCustomerByEmail(ownerEmail);
        } catch (NotExistentUserException e) {
            e.printStackTrace();
        }
        return new Order(orderCode, orderAddress, orderTelephone, customer, orderDate) ;
    }
}
