package fr.iut.paris8.towerdefense.modele.ennemis;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.TerrainModele;

public class Mastodonte extends Ennemi{


    public Mastodonte( int x, int y){
        super(x, y, 1,50,300);
    }

    public Mastodonte () { super(1,50,300);}

}
