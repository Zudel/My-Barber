package first_view.list_cell_factories;

import engineering.bean.ServiceBean;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import static first_view.list_cell_factories.BuyProductListCellFactory.EURO_SYMBOL;
import java.io.IOException;

public class ServiceListCellFactory extends ListCell<ServiceBean> {

    private String fxmlPath= null;
    private Parent parentNode = null ;

    private static final String BARBER_LIST_ITEM = "first_view/list_item/barber_service_list_item.fxml";
    private static final String CLIENT_SERVICE_ITEM ="first_view/list_item/client_service_item.fxml";
    private static final String SERVICE_NAME_LABEL_ID = "serviceNameLabel";
    private static final String SERVICE_PRICE_LABEL_ID = "servicePriceLabel";
    private static final String SERVICE_LABEL_SLOT_NAME_ID = "indexLabel";
    private static final String SERVICE_SLOT_INDEX_LABEL_ID = "indexServiceOfListLabel" ;

    private boolean viewCaller;

    public ServiceListCellFactory() {

        this.viewCaller = false;

    }

    public ServiceListCellFactory(boolean flag){

        this.viewCaller = flag;

    }

    public ServiceListCellFactory(String fxmlPath, boolean view){

        this.fxmlPath = fxmlPath;
        this.viewCaller = view;

    }


    @Override
    protected void updateItem(ServiceBean item, boolean empty){

        super.updateItem(item, empty);

        if(item != null) {

            try{

                if(parentNode == null && fxmlPath != null && fxmlPath.compareTo(CLIENT_SERVICE_ITEM) == 0) {
                        parentNode = new FXMLLoader(getClass().getClassLoader().getResource(CLIENT_SERVICE_ITEM)).load() ;
                }
                else {
                    parentNode = new FXMLLoader(getClass().getClassLoader().getResource(BARBER_LIST_ITEM)).load();
                }


                if(parentNode == null) {
                    parentNode = new FXMLLoader(getClass().getClassLoader().getResource(BARBER_LIST_ITEM)).load();
                }


                Label nameServiceLabel = (Label) parentNode.lookup("#" + SERVICE_NAME_LABEL_ID);
                Label priceServiceLabel = (Label) parentNode.lookup("#" + SERVICE_PRICE_LABEL_ID);
                nameServiceLabel.setText(item.getNameInfo());

                if(fxmlPath != null && fxmlPath.compareTo(CLIENT_SERVICE_ITEM) == 0){
                    priceServiceLabel.setText(String.valueOf(item.getPriceInfo()));
                }
                else {
                    priceServiceLabel.setText(item.getPriceInfo() + " " + EURO_SYMBOL);
                }

                if(viewCaller){
                    Label indexLabel = (Label) parentNode.lookup("#" + SERVICE_LABEL_SLOT_NAME_ID);
                    Label indexSlotLabel = (Label) parentNode.lookup("#" + SERVICE_SLOT_INDEX_LABEL_ID);

                    indexLabel.setText("Slot Index:");
                    indexSlotLabel.setText(Integer.toString(this.getIndex()));
                }

                setGraphic(parentNode);

            }catch (IOException e){
                e.printStackTrace();
            }

        }

    }


}
