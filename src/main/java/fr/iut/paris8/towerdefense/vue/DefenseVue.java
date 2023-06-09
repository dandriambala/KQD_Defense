package fr.iut.paris8.towerdefense.vue;

import fr.iut.paris8.towerdefense.Main1;
import fr.iut.paris8.towerdefense.modele.*;
import javafx.scene.control.Button;
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


    public ImageView creerSpriteDefense(int numeroButton) {

        ImageView c;

        switch (numeroButton){
            case 1:
                c = new ImageView(imgTourelleBase);
                break;
            case 2:
                c = new ImageView(imgTesla);
                break;
            case 3:
                c = new ImageView(imgNuage);
                break;
            case 4:
                c = new ImageView(imgMissile);
                break;
            default:
                c = new ImageView(imgMine);
                break;
        }

        pane.getChildren().add(c);

        return c;
    }
}
