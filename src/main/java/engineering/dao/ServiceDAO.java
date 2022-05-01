package engineering.dao;

import engineering.container.ServiceCatalogue;
import engineering.pattern.Connector;
import model.Service;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {

    private static final String SERVICE_ID_COL_NAME = "id";
    private static final String SERVICE_NAME_COL_NAME = "name";
    private static final String SERVICE_DESCRIPTION_COL_NAME = "description";
    private static final String SERVICE_PRICE_COL_NAME = "price";

    private List<Service> serviceList;

    public int insertService(Service service, String barberEmail){

        int newKeys = -1;
        Connection connection = Connector.getConnectorInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement("INSERT Service(name, description, price, barber) VALUES(?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, service.getServiceName());
            statement.setString(2, service.getServiceDescription());
            statement.setDouble(3, service.getServicePrice());
            statement.setString(4, barberEmail);

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()) {
                newKeys = resultSet.getInt(1);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return newKeys;

    }

    public int loadServiceId(String serviceName, String barberEmail) {

        int key = 0;
        Connection connection = Connector.getConnectorInstance().getConnection();

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = Queries.loadServiceId(statement, serviceName, barberEmail)){

            if(resultSet.next()) {

                key = resultSet.getInt(SERVICE_ID_COL_NAME);

            }


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return key;

    }

    public void deleteService(int serviceKey, String barberEmail){

        Connection connection = Connector.getConnectorInstance().getConnection();

        try (Statement statement = connection.createStatement()){

            Queries.deleteService(statement, serviceKey, barberEmail);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public void insertServiceProduct(Integer serviceId, Integer productId) {

        Connection connection = Connector.getConnectorInstance().getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT ServiceProduct(idService, idProduct) VALUES(?, ?)")) {

            preparedStatement.setInt(1, serviceId);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public void deleteServiceProduct(int serviceId, int productId) {

        Connection connection = Connector.getConnectorInstance().getConnection();

        try(Statement statement = connection.createStatement()) {

            Queries.deleteServiceProduct(statement, serviceId, productId);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public Service loadServiceByNameAndBarber(String serviceName, String barberEmail) {

        Connection connection = Connector.getConnectorInstance().getConnection();
        Service service = null;

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = Queries.loadServiceByNameAndBarberEmail(statement, serviceName, barberEmail)) {

            if(resultSet.next()) {

                service = this.createService(resultSet);

            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }


        return service;
    }

    public ServiceCatalogue loadAllServiceByBarber(String barberEmail) {

        ServiceCatalogue serviceCatalogue = new ServiceCatalogue();
        List<Service> services = new ArrayList<>();

        Connection connection = Connector.getConnectorInstance().getConnection();

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = Queries.loadServicesByBarberEmail(statement, barberEmail)) {

            while(resultSet.next()) {

                Service service = createService(resultSet);
                services.add(service);

            }


        } catch (SQLException sqlException) {

            sqlException.printStackTrace();

        }

        serviceCatalogue.setServiceArrayList(services);

        return serviceCatalogue;

    }

    private Service createService(ResultSet resultSet) throws SQLException {

        ProductDAO productDAO = new ProductDAO();
        int serviceId = resultSet.getInt(1);
        int productId;
        Product product;
        String serviceName = resultSet.getString(SERVICE_NAME_COL_NAME);
        String serviceDescription = resultSet.getString(SERVICE_DESCRIPTION_COL_NAME);
        double servicePrice = resultSet.getDouble(SERVICE_PRICE_COL_NAME);
        productId = productDAO.loadIsbnOfUsedProduct(serviceId);

        if(productId != -1){

            product = productDAO.loadProductByIsbn(productId);
            return new Service(serviceName, serviceDescription, servicePrice, product);

        }

        return new Service(serviceName, serviceDescription, servicePrice);

    }

    public List<Service> retrieveService(String saloonName) {
        Connection connection = Connector.getConnectorInstance().getConnection();

        try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            ResultSet resultSet = Queries.selectServices(statement, saloonName)) {
            serviceList = new ArrayList<>();

            while(resultSet.next()){
                Service service = createServiceCustomer(resultSet);
                serviceList.add(service);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return serviceList;
    }

    public void updateService(int serviceKey, String serviceName, String serviceDescription, Double servicePrice) {

        Connection connection = Connector.getConnectorInstance().getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Service SET name = ?, description = ?, price = ? WHERE id = ?")) {

            preparedStatement.setString(1, serviceName);
            preparedStatement.setString(2, serviceDescription);
            preparedStatement.setDouble(3, servicePrice);
            preparedStatement.setInt(4, serviceKey);

            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public void updateServiceProduct(int serviceKey, int productKey) {

        Connection connection = Connector.getConnectorInstance().getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ServiceProduct SET idProduct = ? WHERE idService = ?;")) {

            preparedStatement.setInt(1, productKey);
            preparedStatement.setInt( 2, serviceKey);

            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    private Service createServiceCustomer(ResultSet resultSet) throws SQLException {

        String serviceName = resultSet.getString(SERVICE_NAME_COL_NAME);
        String serviceDescription = resultSet.getString(SERVICE_DESCRIPTION_COL_NAME);
        double servicePrice = resultSet.getDouble(SERVICE_PRICE_COL_NAME);
        return new Service(serviceName, serviceDescription, servicePrice);

    }
}