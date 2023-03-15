package ca.bcit.comp2522.termproject.capy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class CapyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CapyApplication.class.getResource("level-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Capy Pulls Up!");
        // stage.getIcons().add(capy gun image)

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}