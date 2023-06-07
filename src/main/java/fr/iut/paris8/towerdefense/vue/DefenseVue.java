package fr.iut.paris8.towerdefense.vue;

import fr.iut.paris8.towerdefense.Main1;
import fr.iut.paris8.towerdefense.modele.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;

public class DefenseVue {

    private Pane pane;
    private Image imgTourelleBase;
    private Image imgTesla;
    private Image imgMissile;
    private Image imgMine;
    private Image imgNuage;


    public DefenseVue(Pane pane) {
        this.pane = pane;

        URL urlTourelleBase = Main1.class.getResource("tourelleViolet.png");
        imgTourelleBase = new Image(String.valueOf(urlTourelleBase));

        URL urlTesla = Main1.class.getResource("teslaBleu.png");
        imgTesla = new Image(String.valueOf(urlTesla));

        URL urlMissile = Main1.class.getResource("missile.png");
        imgMissile = new Image(String.valueOf(urlMissile));

        URL urlMine = Main1.class.getResource("mine.png");
        imgMine = new Image(String.valueOf(urlMine));

        URL urlNuage = Main1.class.getResource("nuage.png");
        imgNuage = new Image(String.valueOf(urlNuage));

    }


    public ImageView creerSpriteDefense(Defense d) {

        ImageView c = new ImageView();

        if (d instanceof Tesla) {
            c = new ImageView(imgTesla);
        } else if (d instanceof TourelleBase) {
            c = new ImageView(imgTourelleBase);
        } else if (d instanceof LanceMissile) {
           c = new ImageView(imgMissile);
        } else if (d instanceof Mine) {
            c = new ImageView(imgMine);
            c.setId(((Piege) d).getId());
        }
        else {
            c = new ImageView(imgNuage);
            c.setId(((Piege) d).getId());
        }

        c.setTranslateX(d.getColonne());
        c.setTranslateY(d.getLigne());

        c.translateXProperty().bind(d.colonneProperty());
        c.translateYProperty().bind(d.ligneProperty());

        pane.getChildren().add(c);

        return c;
    }
}
