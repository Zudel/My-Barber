package first_view.list_cell_factories;

import engineering.bean.SaloonBean;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class SaloonListCellFactory extends ListCell<SaloonBean> {
    private final boolean view;
    private Parent parentNode = null ;
    Label nameSaloonItem;
    Label placeSaloonItem;

    private static final String LIST_ITEM_SALOON = "first_view/list_item/take_saloon_item.fxml";
    private static final String LIST_ITEM_SALOON_2V="second_view/list_item/client_saloon_item.fxml";
    private static final String NAME_LABEL_ID = "labelSaloonItem" ;
    private static final String PLACE_LABEL_ID = "placeSaloonItem" ;
    private static final String NAME_LABEL ="saloonNameLabel";
    private static final String SLOT_INDEX ="slotIndexLabel";
    private static final String PLACE_LABEL="saloonCityLabel";

    public SaloonListCellFactory(boolean view){
        this.view = view;
        /**
         if view is true,then is the first interface; Otherwise is the second interface.
         */
    }
    @Override
    protected void updateItem(SaloonBean item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
            try {
                if (parentNode == null && view) {
                    parentNode = new FXMLLoader(getClass().getClassLoader().getResource(LIST_ITEM_SALOON)).load();
                    nameSaloonItem = (Label) parentNode.lookup("#" + NAME_LABEL_ID);
                    placeSaloonItem = (Label) parentNode.lookup("#" + PLACE_LABEL_ID);

                }
                if(parentNode == null){
                    parentNode = new FXMLLoader(getClass().getClassLoader().getResource(LIST_ITEM_SALOON_2V)).load();
                    nameSaloonItem = (Label) parentNode.lookup("#"+NAME_LABEL);
                    Label indexSaloonItem = (Label) parentNode.lookup("#"+SLOT_INDEX);
                    placeSaloonItem = (Label) parentNode.lookup("#"+ PLACE_LABEL);
                    indexSaloonItem.setText("slot index: "+ Integer.toString(this.getIndex()));

                }

                nameSaloonItem.setText(item.getName());
                placeSaloonItem.setText(item.getAddress() + item.getCity());
                setGraphic(parentNode);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
