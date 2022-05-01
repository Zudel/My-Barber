package test;

import application_controller.BookingController;
import engineering.bean.SaloonBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
/*@author Testing:  Roberto Fardella
                        Matricola 0266759
    */

public class TestBookingControllerSearchSaloon {
    @Test
    public void checkSaloon(){
        String name = "TagliaX";
        SaloonBean saloonBean = new SaloonBean(name);
        BookingController bookingController = new BookingController();
        saloonBean = bookingController.searchByNameSaloon(saloonBean);
        assertEquals("Roma",saloonBean.getName());

    }
}
