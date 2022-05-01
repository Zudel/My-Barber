package first_view.list_cell_factories;

import engineering.bean.BookingBean;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import java.io.IOException;



public class BarberAppointmentsListCellFactory extends ListCell<BookingBean> {

    private static final String BARBER_APPOINTMENTS_LIST_ITEM = "first_view/list_item/barber_appointments_item.fxml";
    private static final String NAME_LABEL = "nameLabel";
    private static final String SURNAME_LABEL = "surnameLabel";
    private static final String TO_TIME_LABEL = "toTimeLabel";
    private static final String FROM_TIME_LABEL = "fromTimeLabel";
    Parent parentNode = null;
    



    @Override
    protected void updateItem(BookingBean item, boolean empty) {
        super.updateItem(item, empty);
    if(item !=null){
        try {
            if (parentNode == null) parentNode = new FXMLLoader(getClass().getClassLoader().getResource(BARBER_APPOINTMENTS_LIST_ITEM)).load() ;
            Label nameLabel = (Label) parentNode.lookup("#"+NAME_LABEL);
            Label surnameLabel = (Label) parentNode.lookup("#"+ SURNAME_LABEL);
            Label toTimeLabel = (Label) parentNode.lookup("#"+ TO_TIME_LABEL);
            Label fromTimeLabel =(Label) parentNode.lookup("#"+ FROM_TIME_LABEL);
            
            nameLabel.setText(item.getNameCustomer());
            surnameLabel.setText(item.getSurnameCustomer());
            toTimeLabel.setText(String.valueOf(item.getToTime()));
            fromTimeLabel.setText(String.valueOf(item.getFromTime()));

            
            setGraphic(parentNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }


}
