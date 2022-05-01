package first_view.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import first_view.general.BackController;
import first_view.general.InternalBackController;

import java.io.IOException;

public class ClientMenuController {

    private static final String SEE_APPOINTMENTS_SCREEN_NAME = "first_view/client/client_see_appointments.fxml";
    private static final String BUY_PRODUCT_SCREEN_NAME = "first_view/client/client_buy_product.fxml";
    private static final String CLIENT_CART_SCREEN_NAME = "first_view/client/client_cart.fxml";
    private static final String USER_AREA_SCREEN_NAME = "first_view/general/user_area.fxml";
    private static final String FIDELITY_CARD_SCREEN_NAME = "first_view/client/client_fidelity_card.fxml" ;

    private static final String TAKE_APPOINTMENT_SCREEN_NAME = "first_view/client/client_take_appointment.fxml";

    @FXML
    private MenuBar clientMenuBar ;

    @FXML private MenuItem clientCartItem ;
    @FXML private MenuItem userAreaItem ;
    @FXML private MenuItem seeAppointmentsItem ;
    @FXML private MenuItem buyProductItem ;
    @FXML private MenuItem takeAppointmentItem ;
    @FXML private MenuItem fidelityCardMenuItem ;

    @FXML private Button logoutButton ;
    @FXML private Button homeButton ;


    @FXML
    public void onButtonClicked(ActionEvent event) throws IOException {
        Node sourceNode = (Node)event.getSource() ;
        if (sourceNode == logoutButton) {
            InternalBackController.getInternalBackControllerInstance().emptyStack();
            BackController.getInstance().onBackClick(sourceNode);
        }
        else if (sourceNode == homeButton){
            //Gestione Home
            InternalBackController.getInternalBackControllerInstance().backToHome(homeButton);
        }
        else {
            //Gestione Back
            InternalBackController.getInternalBackControllerInstance().onBackClicked(event);
        }
    }

    @FXML
    public void onMenuItemSelected(ActionEvent event) throws IOException {
        MenuItem sourceItem = (MenuItem) event.getSource() ;
        BorderPane clientBorderPane = (BorderPane) clientMenuBar.getScene().getRoot();
        InternalBackController.getInternalBackControllerInstance().onNextScreen(clientBorderPane);
        String newCenterNodeResName ;
        //Notare il confronto tra oggetti piuttosto che tra stringhe
        if (sourceItem == seeAppointmentsItem) {
            newCenterNodeResName = SEE_APPOINTMENTS_SCREEN_NAME ;
        }
        else if (sourceItem == buyProductItem) {
            newCenterNodeResName = BUY_PRODUCT_SCREEN_NAME;
        }
        else if (sourceItem == takeAppointmentItem) {
            newCenterNodeResName = TAKE_APPOINTMENT_SCREEN_NAME ;
        }
        else if (sourceItem == clientCartItem) {
            newCenterNodeResName = CLIENT_CART_SCREEN_NAME;
        }
        else if (sourceItem == userAreaItem) {
            newCenterNodeResName = USER_AREA_SCREEN_NAME;
        }
        else if (sourceItem == fidelityCardMenuItem) {
            newCenterNodeResName = FIDELITY_CARD_SCREEN_NAME ;
        }
        else {
            newCenterNodeResName = null ;
        }

        if (newCenterNodeResName != null) {
            Parent newCenterNode = new FXMLLoader(getClass().getClassLoader().getResource(newCenterNodeResName)).load() ;
            clientBorderPane.setCenter(newCenterNode);

            //Svuoto stack lasciando solo la home sulla cima
            InternalBackController.getInternalBackControllerInstance().onMenuItemClicked();
        }
    }


}
