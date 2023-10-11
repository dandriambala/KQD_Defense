package fr.iut.paris8.towerdefense.modele.ennemis;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.TerrainModele;

public class EnnemiBase extends Ennemi {

    public EnnemiBase() {

        super( 2, 10, 100);
    }
    public EnnemiBase(int x, int y) {

        super( 2, 10, 100);
        setPositionXProperty(x);
        setPositionYProperty(y);
    }

}