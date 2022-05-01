package second_view.client;

import application_controller.ManageCouponController;
import engineering.bean.FidelityCardBean;
import engineering.bean.UserBean;
import engineering.bean.CouponBean;
import engineering.exception.CardPointsException;
import engineering.exception.NotExistentUserException;
import first_view.list_cell_factories.CouponCellFactory;
import first_view.list_cell_factories.CouponCostListCellFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import second_view.general.ScreenChanger;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static first_view.list_cell_factories.BuyProductListCellFactory.SECOND_VIEW;

public class ClientFidelityCardGUIController implements Initializable {

    @FXML private Label pointsSaleLabel ;
    @FXML private Label userEmailLabel ;
    @FXML private ListView<CouponBean> couponListView ;
    @FXML private ListView<CouponBean> couponCostListView ;

    @FXML private TextField commandLine ;

    private final ManageCouponController manageCouponController ;

    public ClientFidelityCardGUIController() {
        UserBean loggedUser = ScreenChanger.getInstance().getLoggedUser();
        manageCouponController = new ManageCouponController(loggedUser) ;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        couponListView.setCellFactory(param -> new CouponCellFactory(SECOND_VIEW));
        couponCostListView.setCellFactory(param -> new CouponCostListCellFactory(SECOND_VIEW)) ;

        List<CouponBean> couponBeanList = manageCouponController.showCouponCosts() ;
        couponCostListView.setItems(FXCollections.observableList(couponBeanList));

        viewFidelityCard() ;
    }


    @FXML
    public void onCommand(ActionEvent event) {
        String command = commandLine.getText() ;
        commandLine.setText("") ;
        commandLine.setStyle(null);

        if (command.compareTo("back") == 0) {
            ScreenChanger.getInstance().onBack(event);
            return ;
        }

        else if (command.matches("generate [0-9]+")) {
            String commandInput = command.replace("generate ", "" );
            Integer costIndex = Integer.parseInt(commandInput) ;
            getNewCoupon(costIndex) ;
            return ;
        }

        commandLine.setStyle("-fx-border-color: RED");
    }

    private void getNewCoupon(Integer couponCostIndex) {

        try {
            CouponBean couponCostBean = couponCostListView.getItems().get(couponCostIndex);
            FidelityCardBean fidelityCardBean = manageCouponController.generateNewCoupon(couponCostBean) ;
            updateView(fidelityCardBean);
        } catch (CardPointsException | NotExistentUserException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage()) ;
            alert.showAndWait() ;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "NOT VALID INDEX") ;
            alert.showAndWait() ;
        }
    }

    private void viewFidelityCard() {
        try {
            FidelityCardBean fidelityCardBean = manageCouponController.showFidelityCard();
            updateView(fidelityCardBean);
        } catch (NotExistentUserException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage()) ;
            alert.showAndWait() ;
        }
    }

    private void updateView(FidelityCardBean fidelityCardBean) {
        pointsSaleLabel.setText("Total Points " + fidelityCardBean.getPointsSale());
        userEmailLabel.setText("eMail " + fidelityCardBean.getOwner());
        couponListView.setItems(FXCollections.observableList(fidelityCardBean.getCouponBeans()));
    }
}
