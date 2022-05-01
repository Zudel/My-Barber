package engineering.dao;

import engineering.container.SaloonCatalogue;
import engineering.exception.SaloonNotFoundException;
import engineering.pattern.Connector;
import model.Saloon;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class SaloonDAO {

    private static final String SALOON_NAME_ID = "id" ;
    private static final String SALOON_NAME_COL = "name" ;
    private static final String SALOON_ADDRESS_COL = "address" ;
    private static final String SALOON_CITY_COL = "city" ;
    private static final String SALOON_TELEPHONE_COL = "telephone" ;
    private static final String SALOON_INTERVAL_SLOT_TIME_COL = "intervalSlotTime" ;
    private static final String SALOON_SEAT_NUMBER_COL = "seatNumber" ;
    private static final String SALOON_NUMBER_SLOT_TIME_MORNING_COL = "numberSlotTimeMorning" ;
    private static final String SALOON_NUMBER_SLOT_TIME_AFTERNOON_COL = "numberSlotTimeAfternoon" ;
    private static final String SALOON_OPENING_MORNING_TIME_COL = "openMorningTime" ;
    private static final String SALOON_OPENING_AFTERNOON_TIME_COL = "openAfternoonTime" ;
    private static final String SALOON_CLOSE_MORNING_TIME_COL = "closeMorningTime" ;
    private static final String SALOON_CLOSE_AFTERNOON_TIME = "closeAfternoonTime" ;
    private static final String DAY_CLOSED="dayClosed";

    private String barberEmail;

    public SaloonCatalogue loadAllSaloon() {

        SaloonCatalogue saloonCatalogue = new SaloonCatalogue();
        saloonCatalogue.addSaloon(new Saloon("BarberShop", "069576865", "Via Nomentana", "Roma"));
        saloonCatalogue.addSaloon(new Saloon("OldBarber", "068965123", "Via Tuscolana", "Roma"));
        return saloonCatalogue;

    }


    public SaloonCatalogue loadAllSaloonByBarberEmail(String barberEmail) {

        Connection connection = Connector.getConnectorInstance().getConnection();
        SaloonCatalogue saloonCatalogue = new SaloonCatalogue();
        ArrayList<Saloon> saloons = new ArrayList<>();
        Saloon saloon = null;


        try(Statement statement = connection.createStatement();
            ResultSet resultSet = Queries.loadAllSaloon(statement, barberEmail)){

            while(resultSet.next()) {

                saloon = createSaloon(resultSet);
                saloons.add(saloon);

            }


        } catch (SQLException sqlException) {

            sqlException.printStackTrace();

        }

        saloonCatalogue.setSaloonList(saloons);

        return saloonCatalogue;
    }

    public Saloon loadSaloonByBarberEmail(String barberEmail){

        Connection connection = Connector.getConnectorInstance().getConnection();
        Saloon saloon = null;

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = Queries.loadAllSaloon(statement, barberEmail)) {

            if(resultSet.isFirst()) {

                saloon = createSaloon(resultSet);

            }


        } catch (SQLException sqlException) {

            sqlException.printStackTrace();

        }
        return saloon;
    }

    public List<Saloon> retrieveByCityName(String saloonCity) throws SaloonNotFoundException {

        Connection connection = Connector.getConnectorInstance().getConnection();
        ArrayList<Saloon> saloons = new ArrayList<>();

        try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = Queries.loadSaloonByName(statement, saloonCity)){
            if (!resultSet.first() ) {
                String message ="nessun salone disponibile, riprova un'altra citta";
                throw new SaloonNotFoundException(message);
            }

            resultSet.beforeFirst();
            while(resultSet.next()){

                Saloon saloon = createSaloon(resultSet);
                saloons.add(saloon);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return saloons;

    }

    public Saloon retrieveByNameOfSaloon(String saloonName) {

        Connection connection = Connector.getConnectorInstance().getConnection();
        Saloon saloon = null;

        try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = Queries.selectSaloonByName(statement, saloonName)){

            if(resultSet.first()){

                saloon = createSaloon(resultSet);

            }


        } catch (SQLException sqlException) {

            sqlException.printStackTrace();

        }

        return saloon;

    }

    public Saloon retrieveTimeSlots(String saloonName) {

        Connection connection = Connector.getConnectorInstance().getConnection();
        Saloon saloon = null;
        Time[][] times = new Time[2][2];
        LocalTime intervalSlotTime;
        int[] numberSlotTime = new int[2];

        try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = Queries.selectSlotTimeSaloon(statement, saloonName)) {

            if(resultSet.first()) {

                times[0][0] = resultSet.getTime(SALOON_OPENING_MORNING_TIME_COL);
                times[0][1] = resultSet.getTime(SALOON_CLOSE_MORNING_TIME_COL);
                times[1][0] = resultSet.getTime(SALOON_OPENING_AFTERNOON_TIME_COL);
                times[1][1] = resultSet.getTime(SALOON_CLOSE_AFTERNOON_TIME);
                Time time = resultSet.getTime(SALOON_INTERVAL_SLOT_TIME_COL);
                intervalSlotTime = time.toLocalTime();
                numberSlotTime[0] = resultSet.getInt(SALOON_NUMBER_SLOT_TIME_MORNING_COL);
                numberSlotTime[1] = resultSet.getInt(SALOON_NUMBER_SLOT_TIME_AFTERNOON_COL);
                int seatNumber = resultSet.getInt(SALOON_SEAT_NUMBER_COL);
                saloon = new   Saloon(saloonName, times, intervalSlotTime, numberSlotTime,seatNumber);

            }

        } catch (SQLException sqlException) {

            sqlException.printStackTrace();

        }

        return saloon;
    }

    public Integer loadIdOfSaloon(String barberEmail){

        Connection connection = Connector.getConnectorInstance().getConnection();

        Integer saloonId = null;

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = Queries.loadAllSaloon(statement, barberEmail)) {

            if (resultSet.next()) {

                saloonId = resultSet.getInt(SALOON_NAME_ID);

            }

        } catch (SQLException sqlException) {

            sqlException.printStackTrace();

        }

        return saloonId;
    }

    public Saloon createSaloon(ResultSet resultSet) throws SQLException {

        Integer[] numberOfSlots = new Integer[2];
        String[] cityAndAddress = new String[2];
        String name = resultSet.getString(SALOON_NAME_COL);
        String address = resultSet.getString(SALOON_ADDRESS_COL);
        String city = resultSet.getString(SALOON_CITY_COL);
        String telephone = resultSet.getString(SALOON_TELEPHONE_COL);
        int seatsNumber = resultSet.getInt(SALOON_SEAT_NUMBER_COL);
        numberOfSlots[0] = resultSet.getInt(SALOON_NUMBER_SLOT_TIME_MORNING_COL) ;
        numberOfSlots[1] = resultSet.getInt(SALOON_NUMBER_SLOT_TIME_AFTERNOON_COL) ;
        Time time = resultSet.getTime(SALOON_INTERVAL_SLOT_TIME_COL);
        LocalTime intervalSlotTime = time.toLocalTime();
        cityAndAddress[0] = city;
        cityAndAddress[1] = address;

        return new Saloon(name, cityAndAddress, telephone, intervalSlotTime, seatsNumber, numberOfSlots);

    }

    public String getBarberEmail() {

        return barberEmail;

    }

    public void setBarberEmail(String barberEmail) {

        this.barberEmail = barberEmail;

    }

    public boolean checkDateSaloon(String saloonName, String date) {
        Connection connection = Connector.getConnectorInstance().getConnection();
        boolean flag= false;
        String day = null;
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = Queries.checkDateClosed(statement,saloonName);) {
            if (resultSet.first())
                day = resultSet.getString(DAY_CLOSED);

            if ( (day != null) && (day.compareTo(date) == 0)) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
}