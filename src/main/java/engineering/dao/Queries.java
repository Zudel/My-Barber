package engineering.dao;

import java.sql.*;
import java.time.LocalDate;

public class Queries { //qui vanno messe tutte le query per essere più compatti

    private Queries() {

    }
    private static final String PHONE="";
    private static final int CARDPOINTS=0;


    //-+-+-+-+-+-+-+-+-+-+- Saloon -+-+-+-+-+-+-+-+-+-+-//
    public static ResultSet selectSaloonByName(Statement stmt, String saloonName) throws SQLException{
        String query = String.format("SELECT * FROM Saloon join SaloonTime on Saloon.id = SaloonTime.Saloon WHERE  name = '%s';", saloonName);
        return stmt.executeQuery(query);
    }

    public static ResultSet loadAllSaloon(Statement statement, String barberEmail) throws SQLException {
        String query = String.format("SELECT * FROM Saloon WHERE barber = '%s';", barberEmail);
        return statement.executeQuery(query);
    }

    public static ResultSet loadTimesOfSaloon(Statement statement, Integer saloonID) throws  SQLException {
        String query = String.format("SELECT * FROM SaloonTime WHERE Saloon = '%d';", saloonID);
        return statement.executeQuery(query);
    }

    public static ResultSet loadSaloonByName(Statement statement, String city) throws  SQLException {
        String query = String.format("SELECT * FROM Saloon join SaloonTime  WHERE Saloon.id = SaloonTime.Saloon AND city = '%s';", city);
        return statement.executeQuery(query);
    }


    //-+-+-+-+-+-+-+-+-+-+- Product -+-+-+-+-+-+-+-+-+-+-//
    public static ResultSet loadAllProducts(Statement statement) throws  SQLException {
        String query = "SELECT * FROM Product";
        return  statement.executeQuery(query) ;
    }

    public static ResultSet loadAllProductByBarberEmail(Statement statement, String barberEMail) throws SQLException {
        String query = String.format("SELECT * FROM Product WHERE barber = '%s' ;", barberEMail);
        return statement.executeQuery(query);
    }

    public static ResultSet loadProductsByName(Statement statement, String productName) throws SQLException {
        String query = String.format("SELECT * FROM Product where name = '%s';", productName) ;
        return statement.executeQuery(query) ;
    }

    public static ResultSet loadProductByName(Statement statement, String name, String barberEmail) throws SQLException {
        String query = String.format("SELECT * FROM Product where name = '%s' AND barber = '%s';", name, barberEmail) ;
        return statement.executeQuery(query) ;
    }

    public static ResultSet loadProductIdFromServiceProduct(Statement statement, Integer serviceId) throws  SQLException {
        String query = String.format("SELECT * FROM ServiceProduct WHERE idService = '%d';", serviceId);
        return statement.executeQuery(query);
    }

    public static ResultSet loadProductByIsbn(Statement statement, int productIsbn) throws SQLException {
        String query = String.format("SELECT * FROM Product WHERE id = %d;", productIsbn);
        return statement.executeQuery(query);
    }


    //-+-+-+-+-+-+-+-+-+-+- Coupon -+-+-+-+-+-+-+-+-+-+-//
    public static ResultSet selectCouponByCode(Statement statement, Integer couponCode) throws SQLException {
        String query = String.format("SELECT * FROM Coupon C where C.id = %d ;", couponCode) ;
        return statement.executeQuery(query) ;
    }

    public static void deleteCoupon(Statement statement, Integer couponCode) throws SQLException {
        String delete = String.format("DELETE FROM Coupon WHERE id = %d ;", couponCode) ;
        statement.execute(delete) ;
    }

    public static ResultSet selectCouponsByOwner(Statement statement, String userEmail) throws SQLException {
        String query = String.format("SELECT * FROM Coupon WHERE customer = '%s' ;", userEmail) ;
        return statement.executeQuery(query) ;
    }



    //-+-+-+-+-+-+-+-+-+-+- Order -+-+-+-+-+-+-+-+-+-+-//
    public static ResultSet selectOrderByVendor(Statement statement, String vendor) throws SQLException {
        String query = String.format("SELECT DISTINCT CustomerOrder.* FROM CustomerOrder JOIN ProductOrder JOIN Product ON (CustomerOrder.id = ProductOrder.orderId AND ProductOrder.productId = Product.id) WHERE Product.barber = '%s';", vendor) ;
        return statement.executeQuery(query) ;
    }

    public static ResultSet selectCartByOrderId(Statement statement, Integer orderId) throws SQLException {
        String query = String.format("SELECT * FROM ProductOrder WHERE orderId = %d", orderId) ;
        return statement.executeQuery(query) ;
    }


    //-+-+-+-+-+-+-+-+-+-+- SaloonTime -+-+-+-+-+-+-+-+-+-+-//
    public  static ResultSet selectAvailableSaloonDateAndHour(Statement statement, Time time, LocalDate date) throws SQLException {
        String select = String.format("SELECT FROM SaloonTime where hourBooking = '%s' AND dateBooking ='%s';", time,date);
        return statement.executeQuery(select);

    }

    public static ResultSet selectSlotTimeSaloon(Statement stmt, String saloonName) throws SQLException {
        String query = String.format("SELECT openMorningTime,openAfternoonTime,closeMorningTime,closeAfternoonTime,intervalSlotTime,seatNumber,numberSlotTimeMorning,numberSlotTimeAfternoon FROM Saloon join SaloonTime on Saloon.id = SaloonTime.Saloon WHERE Saloon.name ='%s';", saloonName);
        return  stmt.executeQuery(query);
    }


    public static ResultSet selectSaloonByCity(Statement stmt, String saloonCity) throws SQLException {
        String sql = "SELECT * FROM Saloon where city = '" + saloonCity + "';";
        return stmt.executeQuery(sql);
    }


    //-+-+-+-+-+-+-+-+-+-+- Service -+-+-+-+-+-+-+-+-+-+-//
    public static ResultSet loadServiceByNameAndBarberEmail(Statement statement, String serviceName, String barberEmail) throws SQLException {
        String query = String.format("SELECT * FROM Service WHERE name = '%s' AND barber = '%s';", serviceName, barberEmail);
        return statement.executeQuery(query);
    }

    public static ResultSet loadServiceId(Statement statement, String serviceName, String barberEmail) throws SQLException {
        String query = String.format("SELECT id FROM Service WHERE name = '%s' AND barber = '%s' ;", serviceName, barberEmail);
        return statement.executeQuery(query);
    }

    public static ResultSet selectServices(Statement statement, String saloonName) throws SQLException {
        String sql = String.format("SELECT SE.name,SE.description, SE.price FROM Saloon S, Barber B, Service SE WHERE S.barber = B.emailUser AND B.emailUser = SE.barber AND S.name = '%s'; ",saloonName);
        return  statement.executeQuery(sql);
    }


    public static ResultSet loadServicesByBarberEmail(Statement statement, String barberEmail) throws SQLException {
        String query = String.format("SELECT * FROM Service WHERE barber = '%s';", barberEmail);
        return statement.executeQuery(query);
    }

    public static void deleteService(Statement statement, int serviceKey, String barberEmail) throws SQLException {
        String update = String.format("DELETE FROM Service WHERE id = %d AND barber = '%s';", serviceKey, barberEmail);
        statement.execute(update);
    }

    public static int deleteServiceProduct(Statement statement, int serviceId, int productId) throws SQLException {
        String update = String.format("DELETE FROM ServiceProduct WHERE idService = %d AND idProduct = %d ;", serviceId, productId);
        return statement.executeUpdate(update);
    }

    //-+-+-+-+-+-+-+-+-+-+- User -+-+-+-+-+-+-+-+-+-+-//
    public static ResultSet selectUserByCredentials(Statement statement, String userEmail, String password) throws SQLException {
        String query = String.format("SELECT * FROM User WHERE email = '%s' AND pass = '%s' ;", userEmail, password) ;
        return statement.executeQuery(query) ;
    }

    public static boolean insertIntoUser(Statement statement, String email, String pass, String type) throws SQLException {
        String sql = String.format("INSERT INTO User values('%s','%s','%s');", email,pass,type);
        return statement.execute(sql);
    }

    public static ResultSet selectBarberByEmail(Statement statement, String barberEmail) throws SQLException {
        String query = String.format("SELECT * FROM Barber where emailUser = '%s' ;", barberEmail) ;
        return statement.executeQuery(query) ;
    }

    public static ResultSet selectCustomerByEmail(Statement statement, String customerEmail) throws SQLException {
        String query = String.format("SELECT * FROM Customer where userEmail = '%s' ;", customerEmail) ;
        return statement.executeQuery(query) ;
    }

    //-+-+-+-+-+-+-+-+-+-+- Customer -+-+-+-+-+-+-+-+-+-+-//
    public static  ResultSet loadCustomerFromSaloon(Statement statement, Integer favoriteSaloonId) throws SQLException {
        String query = String.format("SELECT C.* FROM Customer C JOIN favorite F ON C.userEmail = F.customer WHERE F.saloon = '%d';", favoriteSaloonId);
        return statement.executeQuery(query);
    }

    public static boolean insertIntoCustomer(Statement statement, String name, String surname, String email) throws SQLException {
        String sql = String.format("INSERT INTO Customer values('%s','%s','%s','%s','%d');",email,name,surname,PHONE,CARDPOINTS);
        return statement.execute(sql);
    }

    public static void updateCustomerCardPoints(Statement statement, String customerEmail, Integer customerCardPoints) throws SQLException {
        String update = String.format("UPDATE Customer SET cardPoints = %d WHERE userEmail = '%s' ;", customerCardPoints, customerEmail) ;
        statement.executeUpdate(update) ;
    }
    //-+-+-+-+-+-+-+-+-+-+- SaloonDay -+-+-+-+-+-+-+-+-+-+-//
    public static ResultSet checkDateClosed(Statement statement,String saloonName) throws SQLException {
        String sql = String.format("SELECT dayClosed FROM Saloon join SaloonDay on Saloon.id = SaloonDay.Saloon WHERE name = '%s';",saloonName);
        return  statement.executeQuery(sql);
    }

    //-+-+-+-+-+-+-+-+-+-+- Barber -+-+-+-+-+-+-+-+-+-+-//
    public static boolean insertIntoBarber(Statement statement, String name, String surname, String email) throws SQLException {
        String sql = String.format("INSERT INTO Barber values('%s','%s','%s');",email,name,surname);
        return  statement.execute(sql);
    }

    //-+-+-+-+-+-+-+-+-+-+- Booking -+-+-+-+-+-+-+-+-+-+-//
    public static boolean insertBookingInfo(Statement statement, String userEmail, String nameSaloon, String service, Time fromTime, Time toTime,Date date) throws SQLException {
        String sql=String.format("INSERT INTO Booking(dateBooking,fromTime,toTime,customer,Saloon,service) values('%s','%s','%s','%s','%s','%s');",date,fromTime,toTime,userEmail,nameSaloon,service);
        return statement.execute(sql);
    }

    public static ResultSet selectBooking(Statement statement,String saloonName, Date date) throws SQLException {
        String sql = String.format("SELECT C.name, C.surname, B.fromTime, B.toTime FROM Customer C, Booking B, Saloon S WHERE C.userEmail = B.customer AND B.Saloon = S.name AND S.name ='%s' AND B.dateBooking ='%s';", saloonName, date);
        return statement.executeQuery(sql);
    }

    public static ResultSet selectDateBooking(Statement statement, Date date) throws SQLException {
        String sql = String.format("SELECT dateBooking FROM Booking WHERE dateBooking ='%s';", date);
        return statement.executeQuery(sql);
    }
}
