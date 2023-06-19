package fr.iut.paris8.towerdefense.modele.ennemis;

import fr.iut.paris8.towerdefense.modele.Environnement;

public class EnnemiBase extends Ennemi {

    public EnnemiBase(Environnement env) {

        super( 2, 10, 100, env);
    }
    public EnnemiBase(Environnement env, int x, int y) {

        super( 2, 10, 100, env);
        setPositionXProperty(x);
        setPositionYProperty(y);
    }

}