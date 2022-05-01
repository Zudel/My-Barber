package engineering.dao;

import engineering.exception.BookingNotFoundExcption;
import engineering.exception.SaloonNotFoundException;
import engineering.pattern.Connector;
import engineering.time.TimeSlot;
import model.Booking;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public boolean insertBooking(String userEmail, String nameSaloon, String[] services, Time fromTime, Time toTime, Date date) {
        boolean flag = false;
        Connection conn = Connector.getConnectorInstance().getConnection();

        try (Statement statement = conn.createStatement()){
            int i=0;
            while(i< services.length  ) {
                flag = Queries.insertBookingInfo(statement, userEmail, nameSaloon, services[i], fromTime, toTime, date);
            i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }

    public List<Booking> retrieveBookingList(String saloonName, Date date) throws SaloonNotFoundException, BookingNotFoundExcption {
        Connection conn = Connector.getConnectorInstance().getConnection();
        List<Booking> bookingList= new ArrayList<>();

        try(Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSetSaloon = Queries.selectSaloonByName(statement, saloonName);){

            if(!resultSetSaloon.first()){
                throw new SaloonNotFoundException("Il salone cercato non è presente in nessuna prenotazione!");
            }

            ResultSet resultSetDate = Queries.selectDateBooking(statement, date);
            if(!resultSetDate.first()){
                throw new BookingNotFoundExcption("la data selezionata non ha nessuna prenotazione presente!");
            }



            ResultSet resultSet = Queries.selectBooking(statement, saloonName, date);

            while (resultSet.next()){
                String cName = resultSet.getString("name");
                String cSurname = resultSet.getString("surname");
                Time toTime = resultSet.getTime("toTime");
                Time fromTime = resultSet.getTime("fromTime");
                Customer customer = new Customer(cName, cSurname);
                TimeSlot timeSlot = new TimeSlot(toTime,fromTime);
                Booking booking = new Booking(customer,timeSlot);
                bookingList.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookingList;
    }
}
