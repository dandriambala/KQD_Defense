package fr.iut.paris8.towerdefense.control;


import fr.iut.paris8.towerdefense.Main1;
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
import javafx.scene.image.Image;
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
    private Button ajoutMine;
    @FXML
    private Button ajoutRalentisseur;
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

        ListChangeListener l2 = new ObservateurPiege(pane);
        this.env.getDefense().addListener(l2);


        imNuage.setOnMouseClicked(e -> {
            URL urlNuage = Main1.class.getResource("nuage.png");
            ImageView imgNuage = new ImageView(String.valueOf(urlNuage));

            pane.getChildren().add(imgNuage);

            imgNuage.setOnMouseDragged(e1 -> {
                System.out.println("drag");
                imgNuage.setTranslateX(e1.getSceneX());
                imgNuage.setTranslateY(e1.getSceneY() - Top.getHeight());

            });
        pane.setOnMouseReleased(
                e2-> {
                    int nbDefenseAncien = env.getDefense().size();
                    Defense d;
                    if (defenseBienPlacé(imgNuage)) {
                        d = new NuageRalentisseur(env);
                        imNuage.setId(((Piege) d).getId());


                        t1.ajouterDefenseDansModele(imgNuage.getTranslateX(), imgNuage.getTranslateY());
                        t1.ajusterEmplacementDefense(imgNuage, (int) (imgNuage.getTranslateX() / 16), (int) (imgNuage.getTranslateY() / 16));
                        d.setColonne((int) imgNuage.getTranslateX());
                        d.setLigne((int) imgNuage.getTranslateY());
                        env.ajouterDefense(d);

                        env.getBfs().testBFS();

                        int nbDefenseCourant = env.getDefense().size();
                        if (nbDefenseAncien == nbDefenseCourant) {
                            pane.getChildren().remove(imgNuage);
                        }
                    } else
                            pane.getChildren().remove(imgNuage);


                }  );

    });

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
    @FXML
    void placementDefense(ActionEvent event){
        Button b;
        int numeroButton;

        int nbDefenseAncien = env.getDefense().size();

        if (ajoutTourelle.isFocused()) {
            b = ajoutTourelle;
            numeroButton = 1;
        }
        else if (ajoutTesla.isFocused()) {
            b = ajoutTesla;
            numeroButton = 2;
        }
        else if (ajoutRalentisseur.isFocused()){
            b = ajoutRalentisseur;
            numeroButton = 3;
        }
        else if (ajoutLanceMissile.isFocused()){
            b = ajoutLanceMissile;
            numeroButton = 4;
        }
        else {
                b = ajoutMine;
                numeroButton = 5;
        }

        DefenseVue defVue = new DefenseVue(pane);
        ImageView c = defVue.creerSpriteDefense(numeroButton);

        b.setOnMouseDragged(e1 -> {
            c.setTranslateX((int) e1.getSceneX());
            c.setTranslateY((int) (e1.getSceneY() - Top.getHeight()));

            b.setOnMouseReleased(e2 -> {
                Defense d;
                if (defenseBienPlacé(c)) {
                    if (b.equals(ajoutTourelle))
                        d = new TourelleBase(env);
                    else if (b.equals(ajoutTesla)) {
                        d = new Tesla(env);
                    }
                    else if (b.equals(ajoutLanceMissile)) {
                        d = new LanceMissile(env);
                    }
                    else if (b.equals(ajoutRalentisseur)) {
                        d = new NuageRalentisseur(env);
                        c.setId(((Piege)d).getId());
                    }

                    else {
                        d = new Mine(env);
                        c.setId(((Piege)d).getId());
                    }
                    t1.ajouterDefenseDansModele(c.getTranslateX(), c.getTranslateY());
                    t1.ajusterEmplacementDefense(c, (int) (c.getTranslateX() / 16), (int) (c.getTranslateY() / 16));
                    d.setColonne((int) c.getTranslateX());
                    d.setLigne((int) c.getTranslateY());
                    env.ajouterDefense(d);

                    int nbDefenseCourant = env.getDefense().size();
                    if (nbDefenseAncien == nbDefenseCourant){
                        pane.getChildren().remove(c);
                    }
                }
                else
                    pane.getChildren().remove(c);


                env.getBfs().testBFS();
            });
        });


    }

    private boolean defenseBienPlacé(ImageView c) {
        return ((c.getTranslateX() < tilepane.getMaxWidth() && c.getTranslateY() < tilepane.getMaxHeight()) && (c.getTranslateX() > tilepane.getMinWidth() && c.getTranslateY() > tilepane.getMinHeight()) && env.getTerrainModele().getTerrain()[(int) c.getTranslateY() /16][(int) c.getTranslateX() /16] == 0);
    }

}



