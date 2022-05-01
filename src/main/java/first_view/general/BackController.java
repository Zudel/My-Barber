package first_view.general;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BackController {

    private static BackController backController ;
    private final ArrayList<Scene> prevSceneStack;

    private BackController() {
        /*
            Creazione dello stack di gestione del back.
            Stack di Scene che vengono riaggiunte direttamente sullo Stage nella onBackClick
         */
        prevSceneStack = new ArrayList<>() ;
    }

    @FXML
    public void onBackClick(Node sourceNode) {
        /*
            1. Prendo lo Stage dove si trova il nodo che ha causato l'evento
            2. Prendo la scena sulla cima dello stack che è la scena che devo reinserire sullo Stage
            3. Reinserisco la Scena prelevata sullo Stage
         */
        Stage stage = (Stage) (sourceNode).getScene().getWindow();
        Scene scene = prevSceneStack.remove(prevSceneStack.size() - 1);
        stage.setScene(scene) ;
    }

    public static BackController getInstance() {
        //Pattern Singleton
        if (backController == null) {
            backController = new BackController() ;
        }
        return backController ;
    }

    public void pushPrevScene(Scene prevSceneName) {
        /*
            1. Invocata quando c'è il cambio schermata da una Scena verso l'altra
            2. Il controller della schermata attuale aggiunge la Scena e naviga verso la successiva
        */

        this.prevSceneStack.add(prevSceneName) ;
    }
}
