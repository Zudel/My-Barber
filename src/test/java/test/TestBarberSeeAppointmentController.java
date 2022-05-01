package test;

import application_controller.BarberSeeAppointmentsController;
import engineering.bean.BookingBean;
import engineering.exception.BookingNotFoundExcption;
import engineering.exception.SaloonNotFoundException;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
/*@author Testing:  Roberto Fardella
                        Matricola 0266759
    */
public class TestBarberSeeAppointmentController {

    @Test
    public void checkBooking() throws SaloonNotFoundException, BookingNotFoundExcption {

        String dateBooking = "2020-11-11";
        BookingBean bookingBean = new BookingBean();
        Date date = Date.valueOf(dateBooking);
        bookingBean.setDate(date);
        BarberSeeAppointmentsController barberSeeAppointmentsController = new BarberSeeAppointmentsController();
        List<BookingBean> bookingBeanList = barberSeeAppointmentsController.retrieveAppointment(bookingBean);
        String saloon = bookingBeanList.get(0).getSaloonName();
        assertEquals("TagliaX", saloon);
    }
}
