package fr.iut.paris8.towerdefense.control;


import fr.iut.paris8.towerdefense.BFS.Case;
import fr.iut.paris8.towerdefense.modele.*;
import fr.iut.paris8.towerdefense.vue.TerrainVue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    private HBox Top;
    @FXML
    private Label nbPvJoueur;
    @FXML
    private Label nbVague;
    @FXML
    private Label nbArgent;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TerrainModele t1 = new TerrainModele();
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
    void testTourelle(ActionEvent event) {
        TourelleBase t = new TourelleBase(env);
        creerSpriteTourelle(t);

        ajoutTourelle.setOnMouseDragged(eve -> {
                    t.setColonne((int) eve.getSceneX());
                    t.setLigne((int) (eve.getSceneY() - Top.getHeight()));
                }
        );

        Case sommet = new Case();
        for ( Case s : env.getBfs().getParcours()){
            if ( s.getColonne() == t.getColonne() && s.getLigne() == t.getLigne() / 16 ) {
                sommet = s;
                break;
            }
        }

    }


    public void creerSpriteTourelle(TourelleBase t) {
        Circle c = new Circle(8);
        c.setFill(Color.RED);

        c.setTranslateX(t.getColonne());
        c.setTranslateY(t.getLigne());
        c.translateXProperty().bind(t.colonneProperty());
        c.translateYProperty().bind(t.ligneProperty());
        pane.getChildren().add(c);
        c.setOnMouseExited(e -> {

                    if(defenseBienPlacé(t)) {
                        env.ajouterDefense(t);
                        System.out.println("Tourelle ajoutée");
                    }
//                    else
//                        pane.getChildren().remove(c);
                    ajouterDefenseDansModele(t.getColonne(), t.getLigne());
                    ajusterEmplacementtourelle(t, (Math.round(t.getColonne() / 16)), Math.round(t.getLigne() / 16));

                    env.getBfs().testBFS();
                }
        );


    }

    public void ajouterDefenseDansModele(int colonne, int ligne) {


        int co = Math.round(colonne / 16);
        int li = Math.round(ligne / 16);

        if (env.getTerrainModele().dansTerrain(li, co) && env.getTerrainModele().getTerrain()[li][co] == 0) {
            env.getTerrainModele().getTerrain()[li][co] = 3;

            Case sommet = new Case();
            for (Case s : env.getBfs().getParcours()) {
                if (s.getColonne() == co && s.getLigne() == li) {
                    sommet = s;
                    break;
                }
            }
            env.getBfs().getG().deconnecte(sommet);
        } else System.out.println("erreur placement");

    }

    public void ajusterEmplacementtourelle(Defense t, int ligne, int colonne) {
        t.setColonne(ligne * 16);
        t.setLigne(colonne * 16);
    }

    private boolean defenseBienPlacé(Defense d) {
        return ((d.getColonne() < tilepane.getMaxWidth() && d.getLigne() < tilepane.getHeight()) && env.getTerrainModele().getTerrain()[d.getLigne() /16][d.getColonne() /16] == 0);
    }
}



