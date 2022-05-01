package engineering.pattern;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Connector {

        private Connection connection ;
        private static Connector myConnector ;

        private Connector()  {

            try(FileInputStream fileInputStream = new FileInputStream("src/main/java/engineering/dao/db_configuration.properties")) {
                // Load DB Connection info from Properties file
                Properties prop = new Properties() ;
                prop.load(fileInputStream);
                String driver = prop.getProperty("jdbcDriver") ;
                String dbName = prop.getProperty("dbName") ;
                String dbUsername = prop.getProperty("dbUserName") ;
                String dbPassword = prop.getProperty("dbPassword") ;
                String dbPort = prop.getProperty("dbPort") ;
                String dbHostname = prop.getProperty("dbHostName") ;
                String dbUrlStart = prop.getProperty("dbUrlStart") ;

                Class.forName(driver) ;
                String jdbcUrl = String.format("%s://%s:%s/%s?user=%s&password=%s",dbUrlStart,dbHostname,dbPort,dbName, dbUsername, dbPassword) ;
                connection = DriverManager.getConnection(jdbcUrl);

            }
            catch (SQLException e) {
                e.getMessage();
                e.getSQLState();
                e.getErrorCode();

            }
            catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }

        public static Connector getConnectorInstance() {
            if (myConnector == null)
                myConnector = new Connector();
            return myConnector ;
        }

        public Connection getConnection() {return connection;}
}

