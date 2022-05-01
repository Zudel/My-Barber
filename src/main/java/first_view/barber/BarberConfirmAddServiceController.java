package first_view.barber;

import application_controller.ManageServiceController;
import engineering.bean.ServiceBean;
import engineering.bean.UserBean;
import engineering.exception.DuplicatedServiceException;
import engineering.exception.InsertNegativePriceException;
import first_view.general.InternalBackController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BarberConfirmAddServiceController {

    public BarberConfirmAddServiceController() {

        this.goToHome = false;

    }

    private ServiceBean serviceBean;

    @FXML private TextField nameConfirmedAddService ;
    @FXML private TextField priceConfirmedAddService ;
    @FXML private TextField descriptionConfirmedAddService ;
    @FXML private TextField nameOfUsedProductConfirmedAddService ;
    @FXML private Button saveConfirmButton;
    @FXML private Label errorLabelConfirmAddService;

    private boolean goToHome;

    @FXML
    public void onButtonClicked(ActionEvent event) {

        errorLabelConfirmAddService.setText("");
        Node sourceNode = (Node) event.getSource();
        UserBean userBean = InternalBackController.getInternalBackControllerInstance().getLoggedUser();
        goToHome = true;

        if(sourceNode == saveConfirmButton && serviceBean != null && (serviceBean.getNameInfo() != null && serviceBean.getNameOfUsedProductInfo() != null && serviceBean.getPriceInfo() != null)) {

            ManageServiceController manageServiceController = new ManageServiceController();

            try {

                manageServiceController.addService(serviceBean,userBean);

            }catch (DuplicatedServiceException e){

                errorLabelConfirmAddService.setText("This service already exist!");
                goToHome = false;

            } catch (InsertNegativePriceException exception) {

                errorLabelConfirmAddService.setText("Insert price is negative!");
                goToHome = false;

            }

        }
        else {

            errorLabelConfirmAddService.setText("Invalid insert field!");
            return;

        }


        if(goToHome) {
            InternalBackController.getInternalBackControllerInstance().backToHome(sourceNode);
        }

    }

    public void display(ServiceBean localServiceBean) {

        this.serviceBean = localServiceBean;

        nameConfirmedAddService.setText(localServiceBean.getNameInfo()) ;
        priceConfirmedAddService.setText(Double.toString(localServiceBean.getPriceInfo())) ;
        descriptionConfirmedAddService.setText(localServiceBean.getDescriptionInfo()) ;
        nameOfUsedProductConfirmedAddService.setText(localServiceBean.getNameOfUsedProductInfo());

    }

}