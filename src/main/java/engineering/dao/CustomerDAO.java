package engineering.dao;

import engineering.exception.DuplicatedUserException;
import engineering.exception.NotExistentUserException;
import engineering.pattern.Connector;
import model.Customer;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private static final String CUSTOMER_COL_EMAIL = "userEmail";
    private static final String CUSTOMER_COL_NAME = "name";
    private static final String CUSTOMER_COL_SURNAME = "surname";
    private static final String CUSTOMER_COL_TELEPHONE = "telephone";
    private static final String CUSTOMER_COL_CARD_POINTS = "cardPoints";
    private static final String CUSTOMER= "customer";

    public Customer retrieveInfoCustomer(String userEmail) {
        Customer customer = null;
        Connection connection = Connector.getConnectorInstance().getConnection();
        try (Statement statement = connection.createStatement();
            ResultSet resultSet = Queries.selectCustomerByEmail(statement, userEmail);) {

            while (resultSet.next()) {
                customer = createCustomer(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public List<Customer> loadCustomerFromFavoriteSaloon(Integer favoriteSaloonId) {
        List<Customer> customers = new ArrayList<>();
        Connection connection = Connector.getConnectorInstance().getConnection();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = Queries.loadCustomerFromSaloon(statement, favoriteSaloonId)) {

            while (resultSet.next()) {
                customers.add(createCustomer(resultSet));
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return customers;
    }

    private Customer createCustomer(ResultSet resultSet) throws SQLException {

        String email = resultSet.getString(CUSTOMER_COL_EMAIL);
        String password = "";
        String name = resultSet.getString(CUSTOMER_COL_NAME);
        String surname = resultSet.getString(CUSTOMER_COL_SURNAME);
        String phone = resultSet.getString(CUSTOMER_COL_TELEPHONE);
        Integer pointsCard = resultSet.getInt(CUSTOMER_COL_CARD_POINTS);

        return new Customer(email, password, name, surname, phone, pointsCard);
    }


    public void insertCustomer(User customer) throws DuplicatedUserException {
        Connection connection = Connector.getConnectorInstance().getConnection();
        try (Statement statement = connection.createStatement();) {

            Queries.insertIntoUser(statement,customer.getEmail(),customer.getPass(),CUSTOMER);
            Queries.insertIntoCustomer(statement, customer.getName(), customer.getSurname(), customer.getEmail());

        }catch (SQLIntegrityConstraintViolationException e){
            throw new DuplicatedUserException(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Customer loadCustomerByEmail(String userEmail) throws NotExistentUserException {
        Customer customer = retrieveInfoCustomer(userEmail) ;
        if (customer == null) {
            throw new NotExistentUserException("NOT EXISTENT USER WITH THIS CREDENTIALS!!") ;
        }
        return customer ;
    }

    public void updateCustomerPoints(Customer customer) {
        Connection connection = Connector.getConnectorInstance().getConnection();

        try(Statement statement = connection.createStatement() ;) {
            Queries.updateCustomerCardPoints(statement, customer.getEmail(), customer.getCardPoints());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
