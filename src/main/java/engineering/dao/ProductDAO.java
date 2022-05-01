package engineering.dao;

import engineering.exception.ProductNotFoundException;
import engineering.pattern.Connector;
import model.Barber;
import model.Product;
import engineering.container.ProductCatalog;

import java.sql.*;
import java.util.ArrayList;

public class ProductDAO {

    private static final String ISBN_COL_NAME = "id" ;
    private static final String NAME_COL_LABEL = "name" ;
    private static final String DESCRIPTION_COL_LABEL = "description" ;
    private static final String PRICE_COL_LABEL = "price" ;
    private static final String BARBER_COL_LABEL = "barber" ;

    public ProductCatalog loadAllProducts() {
        ArrayList<Product> products = new ArrayList<>() ;

        Connection connection = Connector.getConnectorInstance().getConnection();
        try (Statement statement = connection.createStatement() ;
             ResultSet resultSet = Queries.loadAllProducts(statement) ;)
        {

            while (resultSet.next()) {
                Product product = createProduct(resultSet);

                products.add(product);
            }

        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return new ProductCatalog(products) ;
    }

    public boolean insertProduct(Product product, String barberEmail) {

        boolean flag = true;

        Connection connection = Connector.getConnectorInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement("INSERT Product(name, description, price, barber) VALUES(?, ?, ?, ?);")){

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setString(4, barberEmail);

            statement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            flag = false;
        }

        return flag;
    }

    public ProductCatalog loadAllProductsByBarberEmail(String barberEmail) {

        ArrayList<Product> products = new ArrayList<>();

        Connection connection = Connector.getConnectorInstance().getConnection();
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = Queries.loadAllProductByBarberEmail(statement, barberEmail)){

            while(resultSet.next()) {
                Product localProduct = createProduct(resultSet);
                products.add(localProduct);
            }


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return new ProductCatalog(products);

    }

    public Product loadProductByName(String name, String barberEmail) throws ProductNotFoundException{
        Product product = null;
        Connection connection = Connector.getConnectorInstance().getConnection();
        try (Statement statement = connection.createStatement() ;
             ResultSet resultSet = Queries.loadProductByName(statement, name, barberEmail) ) {

            if (resultSet.next()) {

                product = createProduct(resultSet) ;

            }
            else{

                throw new ProductNotFoundException();

            }

        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return product ;
    }

    public int loadIsbnOfUsedProduct(Integer serviceId) throws SQLException{

        int productId = -1;
        Connection connection = Connector.getConnectorInstance().getConnection();

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = Queries.loadProductIdFromServiceProduct(statement, serviceId)){


            if(resultSet.next()){
                productId = resultSet.getInt("idProduct");
            }

        }
        return productId;

    }

    public Product loadProductByIsbn(int productIsbn) throws SQLException{

        Product product = null;
        Connection connection = Connector.getConnectorInstance().getConnection();

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = Queries.loadProductByIsbn(statement, productIsbn)){


            if(resultSet.next()){
                product = createProduct(resultSet);
            }

        }

        return product;

    }


    private Product createProduct(ResultSet resultSet) throws SQLException {

        Integer isbn = resultSet.getInt(ISBN_COL_NAME) ;
        String name = resultSet.getString(NAME_COL_LABEL) ;
        String description = resultSet.getString(DESCRIPTION_COL_LABEL) ;
        Double price = resultSet.getDouble(PRICE_COL_LABEL) ;
        String vendorEmail = resultSet.getString(BARBER_COL_LABEL) ;

        BarberDAO barberDAO = new BarberDAO() ;
        Barber vendor = barberDAO.loadBarber(vendorEmail) ;

        return new Product(isbn, name, description, price, vendor) ;
    }
}
