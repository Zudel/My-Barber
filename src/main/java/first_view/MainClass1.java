package first_view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainClass1 extends Application {

    private static final String OPEN_SCREEN_NAME = "first_view/general/open_screen.fxml";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getClassLoader().getResource(OPEN_SCREEN_NAME)) ;
        Scene scene = new Scene(root.load());
        Image icon = new Image(new File("images/AppLogo_icon.png").toURI().toString());
        stage.getIcons().add(icon);

        stage.setResizable(false);

        stage.setTitle("MyBarber");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}