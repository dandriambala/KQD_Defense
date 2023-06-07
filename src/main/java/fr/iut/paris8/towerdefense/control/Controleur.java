package fr.iut.paris8.towerdefense.control;


import fr.iut.paris8.towerdefense.modele.*;
import fr.iut.paris8.towerdefense.vue.DefenseVue;
import fr.iut.paris8.towerdefense.vue.TerrainVue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {
    @FXML
    private TilePane tilepane;
    @FXML
    private Pane pane;
    private Environnement env;
    private Timeline gameLoop;
    @FXML
    private Button ajoutTourelle;
    @FXML
    private Button ajoutPiege;
    @FXML
    private HBox Top;
    @FXML
    private Label nbPvJoueur;
    @FXML
    private Label nbVague;
    @FXML
    private Label nbArgent;
    @FXML
    private Button ajoutTesla;
    @FXML
    private Button ajoutLanceMissile;
    private TerrainModele t1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        t1 = new TerrainModele();
        env = new Environnement(t1);

        TerrainVue tv = new TerrainVue(t1, tilepane);
        tv.afficherTuile();
        initTowerDefense();
        gameLoop.play();

        ListChangeListener l1 = new ObservateurEnMouvement(pane);
        this.env.getEnMouvements().addListener(l1);

        this.env.getRessourceJeu().pvProperty().addListener((obs, old, nouv) -> this.nbPvJoueur.setText(String.valueOf(nouv)));
        this.env.getRessourceJeu().argentProperty().addListener((obs, old, nouv) -> this.nbArgent.setText(String.valueOf(nouv)));

        this.env.getVague().nbVagueProperty().addListener((obs, old, nouv) -> this.nbVague.setText(String.valueOf(nouv)));

        ListChangeListener l2 = new ObservateurPiege(pane);
        this.env.getDefense().addListener(l2);
    }

    private void initTowerDefense() {

        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);


        KeyFrame kf = new KeyFrame(

                Duration.seconds(0.05),
                (ev -> {
                    env.unTour();
                })
        );
        gameLoop.getKeyFrames().add(kf);
    }
    @FXML
    void placementDefense(ActionEvent event){
        Button b;
        Defense d;
        if (ajoutTourelle.isFocused()) {
            d = new TourelleBase(env);
            b = ajoutTourelle;
        }
        else if (ajoutTesla.isFocused()) {
            d = new Tesla(env);
            b = ajoutTesla;
        }
        else if (ajoutLanceMissile.isFocused()){
            d = new LanceMissile(env);
            b = ajoutLanceMissile;
        }
        else {
            d = new Mine(env);
            b = ajoutPiege;
        }

        DefenseVue defVue = new DefenseVue(pane);
        ImageView c = defVue.creerSpriteDefense(d);

        b.setOnMouseDragged(eve -> {
                    d.setColonne((int) eve.getSceneX());
                    d.setLigne((int) (eve.getSceneY() - Top.getHeight()));
                b.setOnMouseReleased(e -> {

                    if (defenseBienPlacé(d)) {
                        env.ajouterDefense(d);
                        System.out.println("Défense ajoutée");
                    }
                    else
                        pane.getChildren().remove(c);
                    t1.ajouterDefenseDansModele(d.getColonne(), d.getLigne());
                   // ajusterEmplacementTourelle(d, (d.getColonne() / 16),d.getLigne() / 16);

                    env.getBfs().testBFS();
                });}
        );

    }

    public void ajusterEmplacementTourelle(Defense t, int colonne, int ligne ) {
        System.out.println(colonne + " " + ligne);
        t.setColonne(colonne * 16);
        t.setLigne(ligne * 16);
    }
    private boolean defenseBienPlacé(Defense d) {
        return ((d.getColonne() < tilepane.getMaxWidth() && d.getLigne() < tilepane.getMaxHeight()) && (d.getColonne() > tilepane.getMinWidth() && d.getLigne() > tilepane.getMinHeight()) && env.getTerrainModele().getTerrain()[d.getLigne() /16][d.getColonne() /16] == 0);
    }
}



