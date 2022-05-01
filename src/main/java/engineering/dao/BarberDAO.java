package engineering.dao;

import engineering.exception.DuplicatedUserException;
import engineering.pattern.Connector;
import model.Barber;
import model.User;

import java.sql.*;

public class BarberDAO {

    private static  final String BARBER = "barber";
    private static final String BARBER_EMAIL_LAB = "emailUser" ;
    private static final String BARBER_NAME_LAB = "name" ;
    private static final String BARBER_SURNAME_LAB = "surname" ;

    public void insertBarber(User barber) throws DuplicatedUserException {
        Connection connection = Connector.getConnectorInstance().getConnection();
        try(
            Statement statement = connection.createStatement();) {
            Queries.insertIntoUser(statement,barber.getEmail(),barber.getPass(),BARBER);
            Queries.insertIntoBarber(statement, barber.getName(), barber.getSurname(), barber.getEmail());

        }catch (SQLIntegrityConstraintViolationException e){
            throw new DuplicatedUserException(e.getMessage());
        }
        catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public Barber loadBarber(String barberEmail) {
        Connection connection = Connector.getConnectorInstance().getConnection();
        Barber barber = null ;
        try(
                Statement statement = connection.createStatement() ;
                ResultSet resultSet = Queries.selectBarberByEmail(statement, barberEmail) ;)
        {
            if (resultSet.next()) {
                barber = createBarber(resultSet) ;
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return barber ;
    }


    public Barber createBarber(ResultSet resultSet) throws SQLException {
        String email = resultSet.getString(BARBER_EMAIL_LAB) ;
        String name = resultSet.getString(BARBER_NAME_LAB) ;
        String surname = resultSet.getString(BARBER_SURNAME_LAB) ;

        return new Barber(email, "", name, surname) ;
    }
}
