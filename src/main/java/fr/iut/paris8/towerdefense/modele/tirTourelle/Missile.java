package fr.iut.paris8.towerdefense.modele.tirTourelle;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

public class Missile extends Balle{

    public Missile(int positionX, int positionY, int vitesse, Environnement env, Ennemi e, int rayonAction) {
        super(positionX + 8, positionY + 8, vitesse, env, e, 10, "missile");
    }

}
