package fr.iut.paris8.towerdefense.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControleurMenu implements Initializable {
    @Override
    public void initialize ( URL url, ResourceBundle resourceBundle ) {
    }
    @FXML
    void connexion ( ActionEvent event ) throws IOException {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL ressource = getClass().getResource("/fr/iut/paris8/towerdefense/Connexion.fxml");
        Parent root = fxmlLoader.load(ressource);
        Scene scene = new Scene(root,500,500);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Towerdefense");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    void inscription(ActionEvent event)throws IOException {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL ressource = getClass().getResource("/fr/iut/paris8/towerdefense/Inscription.fxml");
        Parent root = fxmlLoader.load(ressource);
        Scene scene = new Scene(root,500,500);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Towerdefense");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    void quitter(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }



}
