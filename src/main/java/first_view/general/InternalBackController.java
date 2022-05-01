package first_view.general;

import engineering.bean.UserBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static first_view.general.EnterAsUserTypeController.CLIENT_TYPE;
import static first_view.general.RegisterScreenController.BARBER_MENU_SCREEN_NAME;
import static first_view.general.RegisterScreenController.CLIENT_MENU_SCREEN_NAME;

public class InternalBackController {

    public static final String BARBER_HOME_SCREEN = "first_view/barber/barber_home.fxml";
    public static final String CLIENT_HOME_SCREEN = "first_view/client/client_home.fxml";
    public static final Integer NOT_LOGGED_USER = -1 ;

    private final ArrayList<Node> internalNodeStack ;
    private static InternalBackController internalBackController ;
    private UserBean loggedUser ;

    private InternalBackController() {
        this.internalNodeStack = new ArrayList<>() ;
        loggedUser = null ;
    }

    public static InternalBackController getInternalBackControllerInstance() {
        if (internalBackController == null) {
            internalBackController = new InternalBackController() ;
        }
        return internalBackController ;
    }

    public void emptyStack() {
        internalNodeStack.removeAll(internalNodeStack) ;
    }


    public void backToHome(Node sourceNode) {

        BorderPane sceneRoot = getBorderPane(sourceNode) ;
        Node homeNode = null ;
        while (!internalNodeStack.isEmpty()) {
            homeNode = internalNodeStack.remove(internalNodeStack.size() - 1) ;
        }
        if (homeNode != null) {
            sceneRoot.setCenter(homeNode);
        }
    }

    public void onBackClicked(ActionEvent event) {
        Node sourceNode = (Node) event.getSource() ;
        BorderPane sceneRoot = getBorderPane(sourceNode) ;
        if (!internalNodeStack.isEmpty()) {
            Node nextNode = internalNodeStack.remove(internalNodeStack.size() - 1);
            sceneRoot.setCenter(nextNode);
        }
    }

    public void onNextScreen(Node sourceNode) {
        this.internalNodeStack.add(getBorderPane(sourceNode).getCenter()) ;
    }

    public void onMenuItemClicked() {
        Node homeNode = null ;
        while (!internalNodeStack.isEmpty()) {
            homeNode = internalNodeStack.remove(internalNodeStack.size() - 1) ;
        }
        if (homeNode != null) {
            internalNodeStack.add(homeNode);
        }
    }

    private BorderPane getBorderPane(Node sourceNode) {
        Scene nodeScene = sourceNode.getScene() ;
        return (BorderPane) nodeScene.getRoot();
    }

    public UserBean getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(UserBean loggedUser) {
        this.loggedUser = loggedUser;
    }


    public void enterAsUser(UserBean userBean, Stage stage) throws IOException {

        String homeScreen ;
        String menuScreen ;
        setLoggedUser(userBean);
        if (userBean == null || userBean.getUserType() == CLIENT_TYPE) {
            homeScreen = CLIENT_HOME_SCREEN ;
            menuScreen = CLIENT_MENU_SCREEN_NAME ;
        }
        else {
            homeScreen = BARBER_HOME_SCREEN ;
            menuScreen = BARBER_MENU_SCREEN_NAME ;
        }
        FXMLLoader userMenuScreen = new FXMLLoader(getClass().getClassLoader().getResource(menuScreen)) ;
        Scene menuScene = new Scene(userMenuScreen.load()) ;
        BorderPane homePane = (BorderPane) menuScene.getRoot();

        FXMLLoader homeScene = new FXMLLoader(getClass().getClassLoader().getResource(homeScreen)) ;
        homePane.setCenter(homeScene.load());

        stage.setScene(menuScene);
    }
}
