package fr.iut.paris8.towerdefense.control;


import fr.iut.paris8.towerdefense.modele.*;
import fr.iut.paris8.towerdefense.modele.defenses.*;

import fr.iut.paris8.towerdefense.vue.TerrainVue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {
    private static int compteurImage = 0;
    @FXML
    private TilePane tilepane;
    @FXML
    private Pane pane;
    private Environnement env;
    private Timeline gameLoop;
    @FXML
    private HBox Top;
    @FXML
    private Label nbPvJoueur;
    @FXML
    private Label nbVague;
    @FXML
    private Label nbArgent;

    private TerrainModele t1;


    @FXML
    private ImageView imNuage;
    @FXML
    private ImageView imTourelle;
    @FXML
    private ImageView imMine;
    @FXML
    private ImageView imTesla;
    @FXML
    private ImageView imMissile;

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

        ListChangeListener l2 = new ObservateurDefenses(pane);
        this.env.getDefense().addListener(l2);


        imTourelle.setOnMouseClicked(e -> dragEtReleasedImageView(imTourelle, 1));
        imTesla.setOnMouseClicked(e-> dragEtReleasedImageView(imTesla, 2));
        imNuage.setOnMouseClicked(e -> dragEtReleasedImageView(imNuage, 3));
        imMissile.setOnMouseClicked(e-> dragEtReleasedImageView(imMissile, 4));
        imMine.setOnMouseClicked(e-> dragEtReleasedImageView(imMine, 5));

    }

    public void dragEtReleasedImageView(ImageView iW, int numeroDef) {


        //creation de la copie de l'image qu'on va drag à partir de l'image View de base
        ImageView copie = new ImageView(iW.getImage());


        copie.setTranslateX(90);
        copie.setTranslateY(360);


        if (numeroDef == 3) {
            copie.setFitWidth(48);
            copie.setFitHeight(48);
            copie.setPreserveRatio(true);
        } else {
            copie.setFitWidth(iW.getFitWidth());
            copie.setFitHeight(iW.getFitHeight());
            copie.setPreserveRatio(iW.isPreserveRatio());
        }

        pane.getChildren().add(copie);


        EventHandler<MouseEvent> handler1 = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                copie.setTranslateX(event.getSceneX());
                copie.setTranslateY(event.getSceneY() - Top.getHeight());
            }
        };

        copie.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler1);

        EventHandler<MouseEvent> handler2 = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                int nbDefenseAncien = env.getDefense().size();
                Defense d;

                if (defenseBienPlacé(copie.getTranslateX(), copie.getTranslateY())) {

                    switch (numeroDef) {
                        case 1:
                            d = new TourelleBase(env);
                            break;
                        case 2:
                            d = new Tesla(env);
                            break;
                        case 3:
                            d = new NuageRalentisseur(env);
                            break;
                        case 4:
                            d = new LanceMissile(env);
                            break;
                        default:
                            d = new Mine(env);
                            break;
                    }

                    copie.setId(d.getId());
                    t1.ajusterEmplacementDefense(copie, (int) (copie.getTranslateX() / 16), (int) (copie.getTranslateY() / 16));
                    d.setColonne((int) copie.getTranslateX());
                    d.setLigne((int) copie.getTranslateY());
                    env.ajouterDefense(d);

                    env.getBfs().testBFS();

                    int nbDefenseCourant = env.getDefense().size();
                    if (nbDefenseAncien == nbDefenseCourant) {
                        pane.getChildren().remove(copie);
                    }

                } else {
                    pane.getChildren().remove(copie);
                }
                    copie.removeEventHandler(MouseEvent.MOUSE_DRAGGED, handler1);
                    pane.removeEventHandler(MouseEvent.MOUSE_DRAGGED, e1 -> {});
                    pane.removeEventHandler(MouseEvent.MOUSE_RELEASED, this);
                }

        };

        pane.addEventHandler(MouseEvent.MOUSE_RELEASED, handler2);



    }

    private void initTowerDefense() {

        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);


        KeyFrame kf = new KeyFrame(

                Duration.seconds(0.08),
                (ev -> {
                    env.unTour();
                })
        );
        gameLoop.getKeyFrames().add(kf);
    }

    private boolean defenseBienPlacé(double x, double y) {
        return ((x < tilepane.getMaxWidth() && y < tilepane.getMaxHeight()) && (x > tilepane.getMinWidth() && y > tilepane.getMinHeight()) && (env.getTerrainModele().getTerrain()[(int) y /16][(int) x /16] == 0));
    }
}



