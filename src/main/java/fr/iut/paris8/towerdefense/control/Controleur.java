package fr.iut.paris8.towerdefense.control;

import fr.iut.paris8.towerdefense.BFS.BFS;
import fr.iut.paris8.towerdefense.BFS.Case;
import fr.iut.paris8.towerdefense.BFS.Grille;
import fr.iut.paris8.towerdefense.modele.*;
import fr.iut.paris8.towerdefense.modele.defenses.*;
import fr.iut.paris8.towerdefense.vue.TerrainVue;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controleur implements Initializable {
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

    @FXML
    private ImageView startImage;
    @FXML
    private ImageView pauseImage;

    @Override
    public void initialize ( URL url, ResourceBundle resourceBundle ) {
        t1 = new TerrainModele();
        env = new Environnement(t1);

        TerrainVue tv = new TerrainVue(t1, tilepane);
        tv.afficherTuile();
        initTowerDefense();

        ListChangeListener l1 = new ObservateurEnMouvement(pane);
        this.env.getEnMouvements().addListener(l1);

        this.env.getRessourceJeu().pvProperty().addListener(( obs, old, nouv ) -> this.nbPvJoueur.setText(String.valueOf(nouv)));
        this.env.getRessourceJeu().argentProperty().addListener(( obs, old, nouv ) -> this.nbArgent.setText(String.valueOf(nouv)));

        this.env.getVague().nbVagueProperty().addListener(( obs, old, nouv ) -> this.nbVague.setText(String.valueOf(nouv)));

        ListChangeListener l2 = new ObservateurDefenses(pane);
        this.env.getDefense().addListener(l2);


        imTourelle.setOnMouseClicked(e -> dragEtReleasedImageView(imTourelle, 1));
        imTesla.setOnMouseClicked(e -> dragEtReleasedImageView(imTesla, 2));
        imTesla.setOnMouseClicked(e-> dragEtReleasedImageView(imTesla, 2));
        imNuage.setOnMouseClicked(e -> dragEtReleasedImageView(imNuage, 3));
        imMissile.setOnMouseClicked(e -> dragEtReleasedImageView(imMissile, 4));
        imMine.setOnMouseClicked(e -> dragEtReleasedImageView(imMine, 5));

    }

    public void dragEtReleasedImageView(ImageView iW, int numeroDef) {

        //creation de la copie de l'image qu'on va drag à partir de l'image View de base
        ImageView copie = new ImageView(iW.getImage());

        copie.setTranslateX(120);
        copie.setTranslateY(420);


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

        BFS bfsSecondaire = new BFS(new Grille(env.getTerrainModele().getWidth() / 16, env.getTerrainModele().getHeight() / 16), new Case(59, 10));
        Case caseTourelle = new Case();
        Case premier = new Case();
        ArrayList<Case> listeObsMis = env.getBfs().getG().getObstacles();

        for (Defense defense : env.getDefense())
            if ( defense instanceof Tourelle )
                bfsSecondaire.getG().deconnecte(new Case(defense.getColonne() / 16, defense.getLigne() / 16));
        bfsSecondaire.testBFS();


        ArrayList<Case> chemin = bfsSecondaire.cheminVersSource(new Case(1, 10));
        ArrayList<Circle> listSprite = new ArrayList<>();

        affichageChemin(bfsSecondaire, listSprite);

        EventHandler<MouseEvent> handler1 = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                copie.setTranslateX(event.getSceneX());
                copie.setTranslateY(event.getSceneY() - Top.getHeight());
                caseTourelle.setColonne((int) ( event.getSceneX() / 16 ));
                caseTourelle.setLigne((int) ( ( event.getSceneY() - Top.getHeight() ) / 16 ));

                if ( chemin.contains(caseTourelle) ) {
                    if ( !bfsSecondaire.getG().estDeconnecte(caseTourelle) ) {
                        premier.setColonne(caseTourelle.getColonne());
                        premier.setLigne(caseTourelle.getLigne());

                        bfsSecondaire.getG().deconnecte(caseTourelle);
                        System.out.println("case deconnecter");
                        effacerChemin(listSprite);
                        affichageChemin(bfsSecondaire, listSprite);
                    }
                    else {
                        if ( !caseTourelle.equals(premier) ) {
                            System.out.println("refait le bfs" + caseTourelle + " " + premier);
                            effacerChemin(listSprite);
                            affichageChemin(bfsSecondaire, listSprite);
                            premier.setColonne(caseTourelle.getColonne());
                            premier.setLigne(caseTourelle.getLigne());
                        }
                    }
                }
                else {
                        if ( bfsSecondaire.getG().estDeconnecte(caseTourelle) && !listeObsMis.contains(caseTourelle)) {
                            bfsSecondaire.getG().reconnecte(caseTourelle);
                            System.out.println("reconnecter");
                            effacerChemin(listSprite);
                            affichageChemin(bfsSecondaire, listSprite);
                        }
                }
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
                    effacerChemin(listSprite);
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
                    if (env.getPartieTerminee()) {
                        gameLoop.stop();
                        afficherAlerte("Partie terminée", "Vous avez perdu la partie.");
                    }
                })
        );
        gameLoop.getKeyFrames().add(kf);
    }


    private boolean defenseBienPlacé(ImageView c) {
        System.out.println(env.getTerrainModele().getTerrain()[(int) c.getTranslateY() /16][(int) c.getTranslateX() /16]);
        return ((c.getTranslateX() < tilepane.getMaxWidth() && c.getTranslateY() < tilepane.getMaxHeight()) && (c.getTranslateX() > tilepane.getMinWidth() && c.getTranslateY() > tilepane.getMinHeight()) && env.getTerrainModele().getTerrain()[(int) c.getTranslateY() /16][(int) c.getTranslateX() /16] == 0);
    }


    @FXML
    public void commencerPartie(){
        startImage.setOnMouseClicked(e -> {
            gameLoop.play();
        });
    }

    @FXML
    public void mettreEnPause(){
        pauseImage.setOnMouseClicked(e -> {
            if (gameLoop.getStatus() == Animation.Status.RUNNING) {
                gameLoop.pause();
            } else {
                gameLoop.play();

            }
        });
    }

    private void afficherAlerte(String titre, String message) {
        Platform.runLater(() -> {
        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle(titre);
        alerte.setHeaderText(null);
        alerte.setContentText(message);
        alerte.showAndWait();
        });
    }


    private void affichageChemin(BFS bfsSecondaire, ArrayList<Circle> list){
        int compteur = 0;
        bfsSecondaire.testBFS();
        ArrayList<Case> chemin = bfsSecondaire.cheminVersSource(new Case(0,10));


        for ( Case c : chemin){
            Circle circle = new Circle(2);
            circle.setFill(Color.WHITE);
            circle.setCenterX(c.getX()+8);
            circle.setCenterY(c.getY()+8);
            circle.setId("chemin"+compteur);
            pane.getChildren().add(circle);
            compteur++;
            list.add(circle);
        }
    }

    private boolean defenseBienPlacé(double x, double y) {
        return ((x < tilepane.getMaxWidth() && y < tilepane.getMaxHeight()) && (x > tilepane.getMinWidth() && y > tilepane.getMinHeight()) && (env.getTerrainModele().getTerrain()[(int) y /16][(int) x /16] == 0));
    }

    public void effacerChemin(ArrayList<Circle> list){
        int compteur = 0;

        for ( Circle circle : list){
            pane.getChildren().remove(circle);
            compteur++;
        }
    }
}

