package second_view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static second_view.general.ScreenChanger.LOGIN_SCREEN;

public class MainClass2 extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(LOGIN_SCREEN));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MyBarber");

        Image icon = new Image(new File("images/AppLogo_icon.png").toURI().toString());
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}