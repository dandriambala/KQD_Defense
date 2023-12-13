package fr.iut.paris8.towerdefense.control;

import fr.iut.paris8.towerdefense.modele.JDBC.InscriptionJDBC;
import fr.iut.paris8.towerdefense.modele.SessionUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Pos;

import java.io.IOException;
import java.net.URL;
import org.mindrot.jbcrypt.BCrypt;

public class ControleurInscription {

    @FXML
    private TextField loginText;
    @FXML
    private TextField mdpText;

    public void inscription(ActionEvent event) throws IOException {
        String nom = loginText.getText();
        String motDePasse = mdpText.getText();

        String motDePasseHache = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        // Créer une instance de InscriptionJDBC pour gérer l'inscription
        InscriptionJDBC inscriptionJDBC = new InscriptionJDBC();

        int idJoueur = inscriptionJDBC.inscrireJoueur(nom, motDePasseHache);

        if (idJoueur != -1) {
            SessionUtilisateur.setIdJoueur(idJoueur);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL ressource = getClass().getResource("/fr/iut/paris8/towerdefense/Vue.fxml");
            Parent root = fxmlLoader.load(ressource);
            Scene scene = new Scene(root, 960, 770);
            primaryStage.setTitle("Towerdefense");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } else {
            afficherErreur("Utilisateur déjà existant");
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
