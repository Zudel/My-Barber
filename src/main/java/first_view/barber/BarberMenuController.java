package first_view.barber;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import first_view.general.BackController;
import first_view.general.InternalBackController;

import java.io.IOException;

public class BarberMenuController {

    @FXML
    private MenuBar barberMenuBar ;

    @FXML private MenuItem userAreaItem ;
    @FXML private MenuItem addCenterItem;
    @FXML private MenuItem manageProductsItem ;
    @FXML private MenuItem barberCenterItem;
    @FXML private MenuItem addServiceItem;
    @FXML private MenuItem listAppointmentsItem;
    @FXML private MenuItem serviceMenuItem;
    @FXML private MenuItem manageOrderMenuItem ;

    @FXML private Button logoutButton ;
    @FXML private Button homeButton ;

    private static final String ADD_SERVICE_BARBER_SCREEN_NAME = "first_view/barber/barber_add_service.fxml";
    private static final String USER_AREA_SCREEN_NAME = "first_view/general/user_area.fxml";
    private static final String BARBER_MANAGE_PRODUCT_SCREEN_NAME = "first_view/barber/barber_manage_products.fxml";
    private static final String BARBER_APPOINTMENTS_LIST_VIEW_SCREEN_NAME = "first_view/barber/barber_manage_appointments.fxml";
    private static final String BARBER_MANAGE_SERVICE_SCREEN_NAME = "first_view/barber/barber_manage_service.fxml" ;
    private static final String BARBER_MANAGE_ORDER_SCREEN_NAME = "first_view/barber/barber_manage_order.fxml" ;


    @FXML
    public void onButtonClicked(ActionEvent event) throws IOException {
        Node sourceNode = (Node) event.getSource() ;
        if (sourceNode == logoutButton) {
            BackController.getInstance().onBackClick((Node) event.getSource());
            InternalBackController.getInternalBackControllerInstance().emptyStack();
        }
        else if (sourceNode == homeButton){
            //Gestione Home
            InternalBackController.getInternalBackControllerInstance().backToHome(homeButton);
        }
        else {
            //Gestione back
            InternalBackController.getInternalBackControllerInstance().onBackClicked(event);
        }
    }

    @FXML
    public void onMenuItemSelected(ActionEvent event) throws IOException {
        MenuItem sourceItem = (MenuItem) event.getSource() ;
        BorderPane barberBorderPane = (BorderPane) barberMenuBar.getScene().getRoot() ;
        String newCenterNodeResName ;
        InternalBackController.getInternalBackControllerInstance().onNextScreen(barberBorderPane);

        if (sourceItem == userAreaItem) {
            newCenterNodeResName = USER_AREA_SCREEN_NAME;
        }
        else if (sourceItem == manageProductsItem) {
            newCenterNodeResName = BARBER_MANAGE_PRODUCT_SCREEN_NAME;
        }
        else if (sourceItem == addServiceItem){
            newCenterNodeResName = ADD_SERVICE_BARBER_SCREEN_NAME;
        }
        else if (sourceItem == listAppointmentsItem){
            newCenterNodeResName = BARBER_APPOINTMENTS_LIST_VIEW_SCREEN_NAME;
        }
        else if (sourceItem == serviceMenuItem) {
            newCenterNodeResName = BARBER_MANAGE_SERVICE_SCREEN_NAME ;
        }
        else if (sourceItem == manageOrderMenuItem) {
            newCenterNodeResName = BARBER_MANAGE_ORDER_SCREEN_NAME ;
        }

        else {
            newCenterNodeResName = null ;
        }

        if (newCenterNodeResName != null) {
            Parent newCenterNode = new FXMLLoader(getClass().getClassLoader().getResource(newCenterNodeResName)).load() ;
            barberBorderPane.setCenter(newCenterNode);

            InternalBackController.getInternalBackControllerInstance().onMenuItemClicked();
        }


    }


}
