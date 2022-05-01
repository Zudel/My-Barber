package engineering.dao;

import engineering.pattern.Connector;
import model.CartRow;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    private static final String PRODUCT_ID_LAB = "productId" ;
    private static final String PRODUCT_QUANTITY_LAB = "productQuantity" ;

    public void saveCart(List<CartRow> cartRowList, Integer orderKey) {

        Connection connection = Connector.getConnectorInstance().getConnection();
        String insert = "INSERT INTO ProductOrder (productId, orderId, productQuantity) VALUES(?,?,?)" ;
        try(PreparedStatement statement = connection.prepareStatement(insert) ;) {

            for (CartRow cartRow : cartRowList) {
                statement.setInt(1, cartRow.getProductIsbn());
                statement.setInt(2, orderKey);
                statement.setInt(3, cartRow.getQuantity());

                statement.executeUpdate() ;
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public List<CartRow> loadCartByOrderCodeAndVendor(Integer orderCode, String vendor) {
        // Retrieve info of brought product for a specific Order and Vendor
        Connection connection = Connector.getConnectorInstance().getConnection();
        List<CartRow> cartRowList = new ArrayList<>() ;
        try(
                Statement statement = connection.createStatement() ;
                ResultSet resultSet = Queries.selectCartByOrderId(statement, orderCode) ;
                ) {

            ProductDAO productDAO = new ProductDAO() ;
            while (resultSet.next()) {
                Integer productId = resultSet.getInt(PRODUCT_ID_LAB) ;
                Integer productQuantity = resultSet.getInt(PRODUCT_QUANTITY_LAB) ;

                Product product = productDAO.loadProductByIsbn(productId) ;
                if (product.getVendorEmail().equals(vendor)) {
                    CartRow cartRow = new CartRow(product, productQuantity);
                    cartRowList.add(cartRow);
                }
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return cartRowList ;
    }
}
