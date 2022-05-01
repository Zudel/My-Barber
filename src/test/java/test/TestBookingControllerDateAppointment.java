package test;

import application_controller.BookingController;
import engineering.bean.BookingBean;
import engineering.exception.SaloonNotFoundException;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
/*@author Testing:  Roberto Fardella
                        Matricola 0266759
    */
public class TestBookingControllerDateAppointment {
    @Test
    public void checkDate() throws SaloonNotFoundException {
        String saloonName ="TagliaX";
        String day ="31-01-2022";
        Date date = Date.valueOf(day);
        BookingBean bookingBean = new BookingBean(saloonName,date);
        BookingController bookingController = new BookingController();
        boolean flag = bookingController.checkDateHour(bookingBean);
        assertEquals(true,flag);

    }
}
