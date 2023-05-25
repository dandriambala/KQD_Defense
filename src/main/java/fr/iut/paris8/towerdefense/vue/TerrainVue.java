package fr.iut.paris8.towerdefense.vue;

import fr.iut.paris8.towerdefense.Main1;
import fr.iut.paris8.towerdefense.modele.TerrainModele;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

import java.net.URL;

public class TerrainVue {
    private TerrainModele terrainModele;
    private TilePane pane;

    public TerrainVue(TerrainModele terrainModele, TilePane pane) {
        this.terrainModele = terrainModele;
        this.pane = pane;
    }

    public void afficherTuile() {
        URL urlgrass = Main1.class.getResource("grass.png");
        Image imgrass = new Image(String.valueOf(urlgrass));

        URL urlearth = Main1.class.getResource("earth.png");
        Image imearth = new Image(String.valueOf(urlearth));

        URL urlwall = Main1.class.getResource("wall.png");
        Image imwall = new Image(String.valueOf(urlwall));

        for (int i = 0; i < terrainModele.getTerrain().length; i++) {
            for (int j = 0; j < terrainModele.getTerrain()[i].length; j++) {

                switch (terrainModele.getTerrain()[i][j]) {
                    default:
                        ImageView imagegrass = new ImageView(imgrass);
                        pane.getChildren().add(imagegrass);
                        break;
                    case 1:
                        ImageView imageearth = new ImageView(imearth);
                        pane.getChildren().add(imageearth);
                        break;
                    case 2:
                        ImageView imagewall = new ImageView(imwall);
                        pane.getChildren().add(imagewall);
                        break;
                }

            }
        }
    }

}

