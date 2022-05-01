package first_view.list_cell_factories;

import engineering.bean.SaloonBean;
import engineering.bean.TimeSlotBean;
import engineering.time.ScheduleTime;
import engineering.time.TimeSlot;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class SaloonTimeSlotsListCellFactory  extends ListCell<TimeSlotBean> {
    private static final String LIST_ITEM_SALOON_TIMESLOTS="first_view/list_item/client_time_slot_item.fxml";
    private static final String LIST_ITEM_SALOON_TIMESLOTS_2V="second_view/list_item/client_slot_time_item.fxml";
    private static final String INIT_TIME = "initTime";
    private static final String FINAL_TIME ="finalTime";
    private static final String SEAT_NUMBER="seatNumberLabel"; //da implemetare sul fxml
    private static final String INDEX="indexLabel";
    private Parent parentNode = null ;
    private boolean view;

    public SaloonTimeSlotsListCellFactory(boolean view){
        this.view=view;
    }

    @Override
    protected void updateItem(TimeSlotBean item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
            try {
                if(parentNode== null && view)
                    parentNode = new FXMLLoader(getClass().getClassLoader().getResource(LIST_ITEM_SALOON_TIMESLOTS)).load();
                if(parentNode== null && !view){
                    parentNode = new FXMLLoader(getClass().getClassLoader().getResource(LIST_ITEM_SALOON_TIMESLOTS_2V)).load();
                    Label index = (Label) parentNode.lookup("#"+INDEX);
                    index.setText("slot index: "+Integer.toString(this.getIndex()));
                }

                assert parentNode != null;
                Label initTime = (Label) parentNode.lookup("#" + INIT_TIME) ;
                Label finalTime = (Label) parentNode.lookup("#" + FINAL_TIME) ;
                Label seatNumber = (Label)parentNode.lookup("#" + SEAT_NUMBER);
                initTime.setText(String.valueOf(item.getFromTime()));
                finalTime.setText(String.valueOf(item.getToTime()));
                seatNumber.setText("seat avalaible: "+String.valueOf(item.getSeatAvailable()));

                setGraphic(parentNode);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
