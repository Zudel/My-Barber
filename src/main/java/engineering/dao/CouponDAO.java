package engineering.dao;

import engineering.exception.InvalidCouponException;
import engineering.pattern.Connector;
import model.Coupon;
import engineering.container.CouponContainer;
import model.PercentageCoupon;
import model.SubtractionCoupon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static model.Coupon.SUBTRACTION_TYPE;

public class CouponDAO {

    private static final String COUPON_CODE_COL_ID = "id" ;
    private static final String COUPON_TYPE_COL_ID = "couponType" ;
    private static final String COUPON_DISCOUNT_COL_ID = "discount" ;

    private static final String COUPON_SUBTRACTION_DB_ENUM = "subtraction";
    private static final String COUPON_PERCENTAGE_DB_ENUM = "percentage" ;


    public Coupon loadCouponByCode(Integer couponCode) throws InvalidCouponException {
        Connection connection = Connector.getConnectorInstance().getConnection();
        Coupon coupon = null ;
        try(Statement statement = connection.createStatement() ;
            ResultSet resultSet = Queries.selectCouponByCode(statement, couponCode) ;)
        {

            if (resultSet.next()) {
                coupon = createCoupon(resultSet) ;
            }
            else {
                throw  new InvalidCouponException("ERROR: NOT VALID COUPON") ;
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return coupon ;
    }

    public List<Coupon> loadCouponByUser(String userEmail) {
        Connection connection = Connector.getConnectorInstance().getConnection();
        List<Coupon> coupons = new ArrayList<>() ;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = Queries.selectCouponsByOwner(statement, userEmail) ;)
        {
            while (resultSet.next()) {
                Coupon coupon = createCoupon(resultSet) ;
                coupons.add(coupon) ;
            }


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return  coupons ;
    }

    private Coupon createCoupon(ResultSet resultSet) throws SQLException {
        Integer couponCode = resultSet.getInt(COUPON_CODE_COL_ID) ;
        Double couponDiscount = resultSet.getDouble(COUPON_DISCOUNT_COL_ID) ;
        String typeEnum = resultSet.getString(COUPON_TYPE_COL_ID) ;

        Coupon coupon ;
        if (typeEnum.compareTo(COUPON_PERCENTAGE_DB_ENUM) == 0) coupon = new PercentageCoupon(couponCode, couponDiscount) ;
        else coupon = new SubtractionCoupon(couponCode, couponDiscount);

        return coupon ;
    }

    public void invalidateCoupon(Coupon coupon) {
        Connection connection = Connector.getConnectorInstance().getConnection();
        try(Statement statement = connection.createStatement()) {

            Queries.deleteCoupon(statement, coupon.getCouponCode()) ;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void invalidateAllCoupon(CouponContainer couponContainer) {
        // Deletes all the coupon in a coupon container
        for (int i = 0 ; i < couponContainer.getSize() ; i++) {
            if (couponContainer.getCouponByIndex(i) != null) {
                invalidateCoupon(couponContainer.getCouponByIndex(i));
            }
        }
    }

    public Coupon addNewCoupon(Integer couponType, Double couponValue, String ownerEmail) {
        Connection connection = Connector.getConnectorInstance().getConnection();

        Coupon newCoupon = null ;
        try(PreparedStatement statement = connection.prepareStatement("INSERT Coupon(couponType, discount, customer) VALUES(?,?,?) ;", Statement.RETURN_GENERATED_KEYS);) {

            String dbCouponType;
            if (couponType.equals(SUBTRACTION_TYPE)) {
                dbCouponType = COUPON_SUBTRACTION_DB_ENUM;
            }
            else {
                dbCouponType = COUPON_PERCENTAGE_DB_ENUM;
            }

            statement.setString(1, dbCouponType);
            statement.setDouble(2, couponValue);
            statement.setString(3, ownerEmail);

            statement.executeUpdate() ;
            ResultSet generatedKeys = statement.getGeneratedKeys() ;

            while (generatedKeys.next()) {
                Integer newCouponCode = generatedKeys.getInt(1) ;
                if (couponType.equals(SUBTRACTION_TYPE)) newCoupon = new SubtractionCoupon(newCouponCode, couponValue) ;
                else newCoupon = new PercentageCoupon(newCouponCode, couponValue) ;
            }

            generatedKeys.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return newCoupon ;
    }
}
