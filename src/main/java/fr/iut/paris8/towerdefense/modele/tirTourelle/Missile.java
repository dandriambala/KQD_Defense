package fr.iut.paris8.towerdefense.modele.tirTourelle;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

public class Missile extends Balle{
    private double distance;
    private int Xdepart;
    private int Ydepart;


    public Missile(int positionX, int positionY, int vitesse, Environnement env, Ennemi e) {
        super(positionX, positionY, vitesse, env, e, 10, "missile");
        distance = Math.sqrt(Math.pow((e.getX()+e.getVitesse()-positionX),2)+Math.pow((e.getY()+e.getVitesse()-positionY),2));
        this.Xdepart = positionX;
        this.Ydepart = positionY;
        setDirectionX(Math.cos(Math.atan2(e.getY()+e.getVitesse()*16 - getY(), e.getX()+e.getVitesse()*16 - getX())));
        setDirectionY(Math.sin(Math.atan2(e.getY()+e.getVitesse()*16 - getY(), e.getX()+e.getVitesse()*16 - getX())));
    }

    // Retourne vrai si le missile a parcouru sa distance
    public boolean ennemiAtteint(){
        return Math.sqrt(Math.pow((getX()-Xdepart),2)+Math.pow((getY()-Ydepart),2))>= distance;
    }
}
