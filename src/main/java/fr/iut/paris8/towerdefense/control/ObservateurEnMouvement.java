package fr.iut.paris8.towerdefense.control;

import fr.iut.paris8.towerdefense.Main1;
import fr.iut.paris8.towerdefense.modele.*;
import fr.iut.paris8.towerdefense.modele.ennemis.*;
import fr.iut.paris8.towerdefense.modele.tirTourelle.Balle;
//import fr.iut.paris8.towerdefense.vue.BalleMissile;
import fr.iut.paris8.towerdefense.modele.tirTourelle.Eclair;
import fr.iut.paris8.towerdefense.modele.tirTourelle.Missile;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;

public class ObservateurEnMouvement implements ListChangeListener<EnMouvement> {

    private Pane pane;
    private Image imgEnnemi1;
    private Image imgEnnemi2;
    private Image imgEnnemi3;
    private Image imgEnnemi4;



    public ObservateurEnMouvement(Pane pane) {

        this.pane = pane;

        URL urlEnnemi1 = Main1.class.getResource("ennemiBase.png");
        imgEnnemi1 = new Image(String.valueOf(urlEnnemi1));

        URL urlEnnemi2 = Main1.class.getResource("éclaireur.png");
        imgEnnemi2 = new Image(String.valueOf(urlEnnemi2));

        URL ennemi3 = Main1.class.getResource("mastodonte.png");
        imgEnnemi3 = new Image(String.valueOf(ennemi3));

        URL urlEnnemi4 = Main1.class.getResource("tank.png");
        imgEnnemi4 = new Image(String.valueOf(urlEnnemi4));
    }

    @Override
    public void onChanged(Change<? extends EnMouvement> change) {

        while (change.next()) {

            for (EnMouvement em : change.getAddedSubList()){
                if(em instanceof Balle)
                    creerSpriteBalle((Balle) em);
                else
                    creerSpriteEnnemi((Ennemi) em);
            }
            for (EnMouvement em: change.getRemoved()) {
                Node node = pane.lookup("#" + em.getId());
                pane.getChildren().remove(node);
                pane.getChildren().remove(pane.lookup("#"+ em.getId()));

            }
        }
    }


    public void creerSpriteBalle(Balle b) {
        Circle c;


        if(b instanceof Missile) {
            c = new Circle(5);
            c.setFill(Color.RED);
        }
        else if (b instanceof Eclair){
            c = new Circle(1);
            c.setFill(Color.WHITE);
        }
        else{
            c = new Circle(1);
            c.setFill(Color.YELLOW);
        }
        c.setTranslateX(b.getX());
        c.setTranslateY(b.getY());
        pane.getChildren().add(c);
        c.translateXProperty().bind(b.positionXProperty());
        c.translateYProperty().bind(b.positionYProperty());
        c.setId(b.getId());

    }


    private void creerSpriteEnnemi(Ennemi ennemi) {

        ImageView c = new ImageView();

        if (ennemi instanceof EnnemiBase){
            c = new ImageView(imgEnnemi1);
        }
        else if (ennemi instanceof Eclaireur){
            c = new ImageView(imgEnnemi2);
        }
        else if (ennemi instanceof Mastodonte) {
            c = new ImageView(imgEnnemi3);
        }
        else if (ennemi instanceof Tank) {
            c = new ImageView(imgEnnemi4);
        }

        c.setTranslateX(ennemi.getX());
        c.setTranslateY(ennemi.getY());
        pane.getChildren().add(c);
        c.translateXProperty().bind(ennemi.positionXProperty());
        c.translateYProperty().bind(ennemi.positionYProperty());
        c.setId(ennemi.getId());

    }
}