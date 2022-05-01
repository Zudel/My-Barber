package engineering.dao;

import engineering.exception.NotExistentUserException;
import engineering.pattern.Connector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    private static final String BARBER_ENUM_TYPE = "barber" ;
    private static final String CUSTOMER_ENUM_TYPE = "customer" ;
    private static final String USER_TYPE = "type" ;
    private static final Integer CUSTOMER_TYPE = 0 ;
    private static final Integer BARBER_TYPE = 1 ;


    public Integer loadUserByCredentials(String userEmail, String userPassword) throws NotExistentUserException {
        Connection connection = Connector.getConnectorInstance().getConnection();

        Integer userType = -1 ;
        try(Statement statement = connection.createStatement() ;
            ResultSet resultSet = Queries.selectUserByCredentials(statement, userEmail, userPassword) ;
            ) {

            if (resultSet.next()) {
                String userEnum = resultSet.getString(USER_TYPE) ;
                if (userEnum.compareTo(BARBER_ENUM_TYPE) == 0) {
                    userType = BARBER_TYPE ;
                }
                else if (userEnum.compareTo(CUSTOMER_ENUM_TYPE) == 0) {
                    userType = CUSTOMER_TYPE ;
                }
            }
            else {
                throw new NotExistentUserException("NOT EXISTENT USER!!") ;
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return userType ;
    }

}
