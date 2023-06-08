package fr.iut.paris8.towerdefense.control;


import fr.iut.paris8.towerdefense.BFS.BFS;
import fr.iut.paris8.towerdefense.BFS.Case;
import fr.iut.paris8.towerdefense.BFS.Grille;
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
import javafx.scene.shape.Rectangle;
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
//        cheminBFS = new CheminBFS(env);
        t1 = new TerrainModele();
        env = new Environnement(t1);

        TerrainVue tv = new TerrainVue(t1, tilepane);
        tv.afficherTuile();
        initTowerDefense();
        gameLoop.play();
//        pane.getChildren().add(cheminBFS);

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
        BFS bfsSecondaire = new BFS(new Grille(env.getTerrainModele().getWidth()/16,env.getTerrainModele().getHeight()/16),new Case(59,10));
        Case c = new Case(1,10);
        Case caseTourelle = new Case();

        for (Defense defense : env.getDefense())
            if ( defense instanceof Tourelle)
                bfsSecondaire.getG().deconnecte(new Case(defense.getColonne()/16, defense.getLigne()/16));
        bfsSecondaire.testBFS();
        ArrayList<Case> chemin = bfsSecondaire.cheminVersSource(c);
        ArrayList<Circle> listSprite;

        listSprite = affichageChemin(bfsSecondaire);

        ajoutTourelle.setOnMouseDragged(eve -> {
                    t.setColonne((int) eve.getSceneX());
                    t.setLigne((int) (eve.getSceneY() - Top.getHeight()));
                    caseTourelle.setColonne(t.getColonne() / 16);
                    caseTourelle.setLigne(t.getLigne() / 16);
                    if ( chemin.contains(caseTourelle)){
                        if (!bfsSecondaire.getG().estDeconnecte(caseTourelle)) {
                            bfsSecondaire.getG().deconnecte(caseTourelle);
                            System.out.println("case deconnecter");
                            effacerChemin(listSprite);
                            affichageChemin(bfsSecondaire);
                        }
                    }
                    else {
                        if ( bfsSecondaire.getG().estDeconnecte(caseTourelle) ) {
                            bfsSecondaire.getG().reconnecte(caseTourelle);
                            System.out.println("recvonnecter");
                            effacerChemin(listSprite);
                            affichageChemin(bfsSecondaire);
                        }
                    }
        }
        );


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
            if ( defenseBienPlacé(t) ) {
                ajouterDefenseDansModele(t.getColonne(), t.getLigne());
                ajusterEmplacementtourelle(t, ( t.getColonne() / 16 ), t.getLigne() / 16);
                env.ajouterDefense(t);
                System.out.println("Tourelle ajoutée");

            }
            else
                pane.getChildren().remove(c);
        });
    }

    public void creerSpriteMine ( Mine m ) {
        Circle c = new Circle(4);
        c.setFill(Color.BLUE);

        c.setTranslateX(m.getColonne());
        c.setTranslateY(m.getLigne());
        c.translateXProperty().bind(m.colonneProperty());
        c.translateYProperty().bind(m.ligneProperty());
        c.setId(m.getId());
        pane.getChildren().add(c);
        c.setOnMouseExited(e -> {
                    if ( defenseBienPlacé(m) ) {
                        ajouterDefenseDansModele(m.getColonne(), m.getLigne());
                        ajusterEmplacementtourelle(m, ( m.getColonne() / 16 ), m.getLigne() / 16);
                        env.ajouterDefense(m);
                        System.out.println("Mine ajoutée");
                    }
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

    public void ajusterEmplacementtourelle ( Defense t, int colonne, int ligne ) {
        System.out.println(colonne + " " + ligne);
        t.setColonne(colonne * 16 + 8);
        t.setLigne(ligne * 16 + 8);
    }
    private boolean defenseBienPlacé(Defense d) {
        int colonne = d.getColonne();
        int ligne = d.getLigne();
        return ((d.getColonne() < tilepane.getMaxWidth() && d.getLigne() < tilepane.getHeight()) && env.getTerrainModele().getTerrain()[d.getLigne() /16][d.getColonne() /16] == 0 &&  !(colonne <= 2 && colonne >= 1 && ligne <= 11 && ligne >= 9  && d instanceof Tourelle || colonne <= 59 && colonne >= 57 && ligne <= 11 && ligne >= 9  && d instanceof Tourelle));
    }

    private ArrayList<Circle> affichageChemin(BFS bfsSecondaire){
        int compteur = 0;
        bfsSecondaire.testBFS();
        ArrayList<Case> chemin = bfsSecondaire.cheminVersSource(new Case(0,10));
        ArrayList<Circle> list = new ArrayList<>();

//        System.out.println(chemin);

        for ( Case c : chemin){
            Circle circle = new Circle(5);
            circle.setFill(Color.CYAN);
            circle.setCenterX(c.getX());
            circle.setCenterY(c.getY());
            circle.setId("chemin"+compteur);
            pane.getChildren().add(circle);
            compteur++;
            list.add(circle);
        }
        return list;
    }

    public void effacerChemin(ArrayList<Circle> list){
        int compteur = 0;

        for ( Circle circle : list){
            pane.getChildren().remove(pane.lookup("#chemin"+compteur));
            compteur++;
        }
    }
}