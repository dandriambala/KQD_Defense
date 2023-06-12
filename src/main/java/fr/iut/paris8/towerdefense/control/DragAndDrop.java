package fr.iut.paris8.towerdefense.control;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.defenses.*;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class DragAndDrop implements EventHandler<MouseEvent> {


    private Pane pane;
    private HBox Top;
    private Environnement env;
    private TilePane tilepane;
    private ImageView iW;
    private int numeroDef;

    public DragAndDrop(Pane pane, HBox top, Environnement env, TilePane tilepane, ImageView iW, int numeroDef) {
        this.pane = pane;
        Top = top;
        this.env = env;
        this.tilepane = tilepane;
        this.iW = iW;
        this.numeroDef = numeroDef;
    }


    @Override
    public void handle(MouseEvent mouseEvent) {
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
    }


    public void drag(ImageView copie, MouseEvent mouseEvent){

        copie.setTranslateX(mouseEvent.getSceneX());
        copie.setTranslateY(mouseEvent.getSceneY() - Top.getHeight());

    }

    public void releasedIW(ImageView copie){

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
                env.getTerrainModele().ajusterEmplacementDefense(copie, (int) (copie.getTranslateX() / 16), (int) (copie.getTranslateY() / 16));
                d.setColonne((int) copie.getTranslateX());
                d.setLigne((int) copie.getTranslateY());
                env.ajouterDefense(d);

                env.getBfs().testBFS();

                int nbDefenseCourant = env.getDefense().size();
                if (nbDefenseAncien == nbDefenseCourant) {
                    pane.getChildren().remove(copie);
                }
            } else
                pane.getChildren().remove(copie);

    }


    private boolean defenseBienPlacé(double x, double y) {
        return ((x < tilepane.getMaxWidth() && y < tilepane.getMaxHeight()) && (x > tilepane.getMinWidth() && y > tilepane.getMinHeight()) && (env.getTerrainModele().getTerrain()[(int) y /16][(int) x /16] == 0));
    }


}
