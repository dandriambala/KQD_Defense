package fr.iut.paris8.towerdefense.control;


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
    void placementDefense(ActionEvent event){
        Button b;
        if (ajoutTourelle.isFocused()) {
            b = ajoutTourelle;
        }
        else if (ajoutTesla.isFocused()) {
            b = ajoutTesla;
        }
        else {
            b = ajoutPiege;
        }

        Circle c = creerSpriteDefense(b);
        b.setOnMouseDragged(e1 -> {
                    c.setTranslateX((int) e1.getSceneX());
                    c.setTranslateY((int) (e1.getSceneY() - Top.getHeight()));

                    b.setOnMouseReleased(e3 -> {
                        Defense d;
                        if (defenseBienPlacé(c)) {
                            if (b.equals(ajoutTourelle))
                                d = new TourelleBase(env);
                            else if (b.equals(ajoutTesla)) {
                                d = new Tesla(env);
                            }
                            else {
                                d = new Mine(env);
                                c.setId(((Piege)d).getId());
                            }
                            ajouterDefenseDansModele(c.getTranslateX(), c.getTranslateY());
                            ajusterEmplacementDefense(c, (int) (c.getTranslateX() / 16), (int) (c.getTranslateY() / 16));
                            d.setColonne((int) c.getTranslateX());
                            d.setLigne((int) c.getTranslateY());
                            env.ajouterDefense(d);
                            System.out.println("Défense ajoutée");
                        }
                        else
                            pane.getChildren().remove(c);


                        env.getBfs().testBFS();
                    });
        });

    }

    public Circle creerSpriteDefense(Button b) {

        Circle c;

        if (b.equals(ajoutTourelle)) {
            c = new Circle(8);
            c.setFill(Color.RED);
        } else if (b.equals(ajoutTesla)) {
            c = new Circle(8);
            c.setFill(Color.ORANGE);
        }
        else{
            c = new Circle(4);
            c.setFill(Color.BLUE);
        }

        pane.getChildren().add(c);
        return c;
    }

    public void ajouterDefenseDansModele(double colonne, double ligne) {
        int co = (int) Math.round(colonne / 16);
        int li = (int) Math.round(ligne / 16);

        if (env.getTerrainModele().dansTerrain(li, co) && env.getTerrainModele().getTerrain()[li][co] == 0) {
            env.getTerrainModele().getTerrain()[li][co] = 3;
        } else System.out.println("erreur placement");
    }

    public void ajusterEmplacementDefense(Circle c, double colonne, double ligne ) {
        System.out.println(colonne + " " + ligne);
        c.setTranslateX(colonne * 16 + 8);
        c.setTranslateY(ligne * 16 + 8);
    }
    private boolean defenseBienPlacé(Circle c) {
        return ((c.getTranslateX() < tilepane.getMaxWidth() && c.getTranslateY() < tilepane.getMaxHeight()) && (c.getTranslateX() > tilepane.getMinWidth() && c.getTranslateY() > tilepane.getMinHeight()) && env.getTerrainModele().getTerrain()[(int) c.getTranslateX() /16][(int) c.getTranslateY() /16] == 0);
    }
}



