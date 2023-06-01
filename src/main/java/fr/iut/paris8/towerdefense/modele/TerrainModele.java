package fr.iut.paris8.towerdefense.modele;

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
//                //TODO première version de l'affichage qui va être remplacé lorsqu'on aura le bfs
//                if (i == t.length / 2)
//                    t[i][j] = 1;
                if (li == 0  ||  co == 0 || li == t.length -1 || co == t[li].length-1 )
                    t[li][co] = 2;
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

    public int getWidth(){
        return colonne*pixel;
    }
    public int getHeight(){
        return ligne*pixel;
    }

}
