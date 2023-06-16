package fr.iut.paris8.towerdefense.modele.tirTourelle;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

public class Eclair extends Balle{

    public Eclair(int positionX, int positionY, int vitesse, Environnement env, Ennemi e) {
        super(positionX + 8, positionY + 8, vitesse, env, e, 16, "eclair");
    }
    public void seDeplacer() {

        setDirectionX(Math.cos(directionPourCible(getEnnemi().getX()+16,getEnnemi().getY()+16)));
        setDirectionY(Math.sin(directionPourCible(getEnnemi().getX()+16,getEnnemi().getY()+16)));

        setPositionXProperty((int) (getX() + getVitesse() * getDirectionX()));
        setPositionYProperty((int) (getY() + getVitesse() * getDirectionY()));

    }
}
