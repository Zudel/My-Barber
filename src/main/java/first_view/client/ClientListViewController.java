package first_view.client;

import engineering.bean.SaloonBean;
import first_view.list_cell_factories.SaloonListCellFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;


public class ClientListViewController {
    @FXML
    private ListView<SaloonBean> saloonListView;

    public  ClientListViewController(){
        saloonListView = new ListView<>();
    }


    public void injectSaloonList(List<SaloonBean> saloonBeanList) {
        boolean firstView=true;
        saloonListView.setCellFactory(param -> new SaloonListCellFactory(firstView));
        saloonListView.setItems(FXCollections.observableArrayList(saloonBeanList));
    }
}
