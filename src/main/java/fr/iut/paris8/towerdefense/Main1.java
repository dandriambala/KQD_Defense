package fr.iut.paris8.towerdefense;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main1 extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main1.class.getResource("Vue.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 770);
        stage.setTitle("Tower Defense");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}