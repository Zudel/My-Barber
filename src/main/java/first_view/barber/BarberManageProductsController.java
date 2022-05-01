package first_view.barber;

import first_view.ObservableListNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import first_view.general.InternalBackController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BarberManageProductsController implements Initializable {

    private static final String LIST_ITEM_RES = "first_view/list_item/client_buy_product_list_item.fxml";
    private static final String MODIFY_PRODUCT_SCREEN_NAME = "first_view/barber/barber_add_product.fxml";

    @FXML ListView<Node> sellProductListView ;
    @FXML Button addProductButton ;
    @FXML Button editProductButton ;
    @FXML Button deleteProductButton ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Node[] nodesList = new Node[10] ;
        for (int i = 0 ; i < 10 ; i++) {
            try {
                nodesList[i] = (new FXMLLoader(getClass().getClassLoader().getResource(LIST_ITEM_RES))).load() ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ObservableListNode clientAppointmentsList = new ObservableListNode(nodesList);
        sellProductListView.setItems(clientAppointmentsList);
    }

    @FXML
    public void onButtonClicked(ActionEvent event) throws IOException {
        Node sourceNode = (Node) event.getSource() ;
        if (sourceNode == addProductButton || sourceNode == editProductButton) {
            InternalBackController.getInternalBackControllerInstance().onNextScreen(sourceNode);

            Parent nextParent = (new FXMLLoader(getClass().getClassLoader().getResource(MODIFY_PRODUCT_SCREEN_NAME))).load() ;
            BorderPane scenePane = (BorderPane) sourceNode.getScene().getRoot() ;
            scenePane.setCenter(nextParent);
        }
        else if (sourceNode == deleteProductButton) {
            Alert deleteAlert = new Alert(Alert.AlertType.WARNING, "Sei sicuro di voler eliminare questo prodotto?", ButtonType.OK, ButtonType.NO) ;
            deleteAlert.showAndWait() ;
        }
    }
}
