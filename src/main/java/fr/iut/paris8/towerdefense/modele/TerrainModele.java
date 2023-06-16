package fr.iut.paris8.towerdefense.modele;

import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class TerrainModele {

    private int[][] terrain;
    private final int colonne = 60, ligne = 22, pixel = 16;
    // tableau de 60*22

    public TerrainModele() {

        int[][] t = new int[ligne][colonne];


        for (int li = 0; li < t.length; li++) {
            for (int co = 0; co < t[li].length; co++) {
                if (li == 0 || li == t.length - 1 || co == 0 || co == t[li].length - 1)
                    t[li][co] = 0;
                if (li == 0  ||  co == 0 || li == t.length -1 || co == t[li].length-1 )
                    t[li][co] = 2;
                if (li == 10 && co == 0 || li == 10 && co == t[li].length-1)
                    t[li][co] = 1;
            }
        }


        this.terrain = t;
    }

    public int[][] getTerrain() {
        return terrain;
    }

    public boolean dansTerrain(int ligne, int colonne) {
        return (0 <= ligne && ligne < this.ligne && 0 <= colonne && colonne < this.colonne);
    }
    public boolean dansTerrainEnnemie(int ligne, int colonne) {
        return (1 <= ligne && ligne < this.ligne-1 && 0 <= colonne && colonne < this.colonne-1);
    }

    public int getWidth(){
        return colonne*pixel;
    }
    public int getHeight(){
        return ligne*pixel;
    }

    public void caseAZero(double colonne, double ligne){
        getTerrain()[(int)ligne][(int)colonne] = 0;
    }

    public void ajouterDefenseDansModele(double colonne, double ligne) {
        int co = (int) (Math.round(colonne / 16.0));
        int li = (int) (Math.round(ligne / 16.0));

        if (dansTerrain(li, co) && getTerrain()[li][co] == 0) {
            getTerrain()[li][co] = 3;
        }
    }

    public void ajusterEmplacementDefense(ImageView c, double colonne, double ligne ) {
        c.setTranslateX(colonne * 16 );
        c.setTranslateY(ligne * 16 );
    }
}
