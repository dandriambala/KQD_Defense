package fr.iut.paris8.towerdefense.control;


import fr.iut.paris8.towerdefense.BFS.BFS;
import fr.iut.paris8.towerdefense.BFS.Case;
import fr.iut.paris8.towerdefense.BFS.Grille;
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

    @Override
    public void initialize ( URL url, ResourceBundle resourceBundle ) {
        t1 = new TerrainModele();
        env = new Environnement(t1);

        TerrainVue tv = new TerrainVue(t1, tilepane);
        tv.afficherTuile();
        initTowerDefense();
        gameLoop.play();

        ListChangeListener l1 = new ObservateurEnMouvement(pane);
        this.env.getEnMouvements().addListener(l1);

        this.env.getRessourceJeu().pvProperty().addListener(( obs, old, nouv ) -> this.nbPvJoueur.setText(String.valueOf(nouv)));
        this.env.getRessourceJeu().argentProperty().addListener(( obs, old, nouv ) -> this.nbArgent.setText(String.valueOf(nouv)));

        this.env.getVague().nbVagueProperty().addListener(( obs, old, nouv ) -> this.nbVague.setText(String.valueOf(nouv)));

        ListChangeListener l2 = new ObservateurPiege(pane);
        this.env.getDefense().addListener(l2);
    }

    private void initTowerDefense () {

        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);


        KeyFrame kf = new KeyFrame(

                Duration.seconds(0.08),
                ( ev -> {
                    env.unTour();
                } )
        );
        gameLoop.getKeyFrames().add(kf);
    }

    @FXML
    void placementDefense(ActionEvent event){
        Button b;
        int numeroButton;

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

        BFS bfsSecondaire = new BFS(new Grille(env.getTerrainModele().getWidth() / 16, env.getTerrainModele().getHeight() / 16), new Case(59, 10));
        Case caseDentree = new Case(1, 10);
        Case caseTourelle = new Case();
        Case premier = new Case();

        for (Defense defense : env.getDefense())
            if ( defense instanceof Tourelle )
                bfsSecondaire.getG().deconnecte(new Case(defense.getColonne() / 16, defense.getLigne() / 16));
        bfsSecondaire.testBFS();

        ArrayList<Case> chemin = bfsSecondaire.cheminVersSource(caseDentree);
        ArrayList<Circle> listSprite = new ArrayList<>();

        affichageChemin(bfsSecondaire, listSprite);

        b.setOnMouseDragged(e1 -> {
            c.setTranslateX((int) e1.getSceneX());
            c.setTranslateY((int) (e1.getSceneY() - Top.getHeight()));


            caseTourelle.setColonne((int) (e1.getSceneX()/ 16));
            caseTourelle.setLigne((int) ((e1.getSceneY() - Top.getHeight())/ 16));

            if ( chemin.contains(caseTourelle) ) {
                if ( !bfsSecondaire.getG().estDeconnecte(caseTourelle) ) {
                    premier.setColonne(caseTourelle.getColonne());
                    premier.setLigne(caseTourelle.getLigne());

                    bfsSecondaire.getG().deconnecte(caseTourelle);
                    System.out.println("case deconnecter");
                    effacerChemin(listSprite);
                    affichageChemin(bfsSecondaire, listSprite);
                }
                else{
                    if (!caseTourelle.equals(premier)){
                        System.out.println(caseTourelle + " " + premier);
                        effacerChemin(listSprite);
                        affichageChemin(bfsSecondaire,listSprite);
                        premier.setColonne(caseTourelle.getColonne());
                        premier.setLigne(caseTourelle.getLigne());
                    }
                }
            }
            else {
                if ( bfsSecondaire.getG().estDeconnecte(caseTourelle) ) {
                    bfsSecondaire.getG().reconnecte(caseTourelle);
                    System.out.println("reconnecter");
                    effacerChemin(listSprite);
                    affichageChemin(bfsSecondaire, listSprite);
                }
            }

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
                    System.out.println("Défense ajoutée");
                    env.getBfs().testBFS();
                }
                else
                    pane.getChildren().remove(c);

                effacerChemin(listSprite);
            });
        });
    }

    private boolean defenseBienPlacé(ImageView c) {
        System.out.println(env.getTerrainModele().getTerrain()[(int) c.getTranslateY() /16][(int) c.getTranslateX() /16]);
        return ((c.getTranslateX() < tilepane.getMaxWidth() && c.getTranslateY() < tilepane.getMaxHeight()) && (c.getTranslateX() > tilepane.getMinWidth() && c.getTranslateY() > tilepane.getMinHeight()) && env.getTerrainModele().getTerrain()[(int) c.getTranslateY() /16][(int) c.getTranslateX() /16] == 0);
    }

    private void affichageChemin(BFS bfsSecondaire, ArrayList<Circle> list){
        int compteur = 0;
        bfsSecondaire.testBFS();
        ArrayList<Case> chemin = bfsSecondaire.cheminVersSource(new Case(0,10));

//        System.out.println(chemin);

        for ( Case c : chemin){
            Circle circle = new Circle(3);
            circle.setFill(Color.CYAN);
            circle.setCenterX(c.getX()+8);
            circle.setCenterY(c.getY()+8);
            circle.setId("chemin"+compteur);
            pane.getChildren().add(circle);
            compteur++;
            list.add(circle);
        }
    }

    public void effacerChemin(ArrayList<Circle> list){
        int compteur = 0;

        for ( Circle circle : list){
            pane.getChildren().remove(pane.lookup("#chemin"+compteur));
            compteur++;
        }
    }
}