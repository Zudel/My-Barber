package first_view.client;

import engineering.bean.ServiceBean;
import first_view.general.InternalBackController;
import first_view.list_cell_factories.ServiceListCellFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;

import java.util.List;

public class BookedGraphicController{

    private static final String SERVICE_ITEM = "first_view/list_item/barber_service_list_item.fxml";

        @FXML
        private ListView<ServiceBean> serviceSelectedListView;
        public BookedGraphicController(){
            serviceSelectedListView = new ListView<>();
        }

        @FXML
        void onBookedButton(ActionEvent event) {
            InternalBackController.getInternalBackControllerInstance().backToHome((Node) event.getSource());

        }


    public void injectServicesList(List<ServiceBean> services) {
        boolean firstView;
        firstView=false;
        serviceSelectedListView.setCellFactory(param -> new ServiceListCellFactory(SERVICE_ITEM,firstView));
        serviceSelectedListView.setItems(FXCollections.observableList(services));

    }
}
