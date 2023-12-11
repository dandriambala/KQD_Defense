package fr.iut.paris8.towerdefense.control;

import fr.iut.paris8.towerdefense.modele.JDBC.ConnexionJDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControleurConnexion implements Initializable {
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle ) {
    }
    @FXML
    private TextField loginText;

    @FXML
    private TextField mdpText;

    @FXML
    void connexion(ActionEvent event) throws IOException, SQLException {
        String login,mdp;
        login=loginText.getText();
        mdp=mdpText.getText();
        ConnexionJDBC connexionJDBC=new ConnexionJDBC();
        if(connexionJDBC.connexionJoueur(login,mdp)) {
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL ressource = getClass().getResource("/fr/iut/paris8/towerdefense/Vue.fxml");
            Parent root = fxmlLoader.load(ressource);
            Scene scene = new Scene(root, 960, 770);
            primaryStage.setTitle("Towerdefense");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        else{
            afficherErreur("Login ou mot de passe incorrect");
        }
    }

    private void afficherErreur(String message) {
        Stage erreurStage = new Stage();
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label labelErreur = new Label(message);
        labelErreur.setStyle("-fx-text-fill: red;");
        root.getChildren().add(labelErreur);

        Scene scene = new Scene(root, 300, 200);
        erreurStage.setTitle("Message d'Erreur");
        erreurStage.setScene(scene);
        erreurStage.show();
    }

}