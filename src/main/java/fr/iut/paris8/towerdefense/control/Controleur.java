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
    void testTourelle(ActionEvent event) {
        TourelleBase t = new TourelleBase(env);
        creerSpriteDefense(t);

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

    @FXML
    void testTesla(ActionEvent event) {
        Tesla t = new Tesla(env);
        creerSpriteDefense(t);

        ajoutTesla.setOnMouseDragged(eve -> {
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


    @FXML
    void testPiege(ActionEvent event) {
        Mine t = new Mine(env);
        creerSpriteMine(t);

        ajoutPiege.setOnMouseDragged(eve -> {

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

    public void creerSpriteDefense(Defense t) {

        Circle c = new Circle(8);

        if (t instanceof Tesla) {
            c.setFill(Color.ORANGE);
        } else if (t instanceof TourelleBase)
            c.setFill(Color.RED);

        c.setTranslateX(t.getColonne());
        c.setTranslateY(t.getLigne());
        c.translateXProperty().bind(t.colonneProperty());
        c.translateYProperty().bind(t.ligneProperty());
        pane.getChildren().add(c);
        c.setOnMouseExited(e -> {
            if (defenseBienPlacé(t)) {
                env.ajouterDefense(t);
                System.out.println("Tourelle ajoutée");
            }
//                    else
//                        pane.getChildren().remove(c);
            ajouterDefenseDansModele(t.getColonne(), t.getLigne());
            ajusterEmplacementtourelle(t, (Math.round(t.getColonne() / 16)), Math.round(t.getLigne() / 16));
            env.ajouterDefense(t);
            System.out.println("Tourelle ajoutée");

            env.getBfs().testBFS();
        });
    }

    public void creerSpriteMine(Mine m) {
        Circle c = new Circle(4);
        c.setFill(Color.BLUE);

        c.setTranslateX(m.getColonne());
        c.setTranslateY(m.getLigne());
        c.translateXProperty().bind(m.colonneProperty());
        c.translateYProperty().bind(m.ligneProperty());
        c.setId(m.getId());
        pane.getChildren().add(c);
        c.setOnMouseExited(e -> {

                    if (defenseBienPlacé(m)) {
                        env.ajouterDefense(m);
                        System.out.println("Mine ajoutée");
                    }
//                    else
//                        pane.getChildren().remove(c);
                    ajouterDefenseDansModele(m.getColonne(), m.getLigne());
                    ajusterEmplacementtourelle(m, (Math.round(m.getColonne() / 16)), Math.round(m.getLigne() / 16));
                }
        );


    }

    public void ajouterDefenseDansModele(int colonne, int ligne) {



        int co = (int) (Math.round(colonne / 16.0));
        int li = (int) (Math.round(ligne / 16.0));

        if (env.getTerrainModele().dansTerrain(li, co) && env.getTerrainModele().getTerrain()[li][co] == 0) {
            env.getTerrainModele().getTerrain()[li][co] = 3;


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



