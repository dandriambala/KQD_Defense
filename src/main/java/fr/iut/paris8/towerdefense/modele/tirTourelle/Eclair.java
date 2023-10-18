package fr.iut.paris8.towerdefense.modele.tirTourelle;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

public class Eclair extends Balle{
    private Ennemi e;
    private int Xdepart;
    private int Ydepart;

    public Eclair(int positionX, int positionY, int vitesse, Environnement env, Ennemi e) {
        super(positionX, positionY, vitesse, env, e, 10);
        this.e = e;
        this.Xdepart = positionX-8;
        this.Ydepart = positionY-8;
    }
    public void seDeplacer() {
        setDirectionX(Math.cos(directionPourCible(getEnnemi().getX()+16,getEnnemi().getY()+16)));
        setDirectionY(Math.sin(directionPourCible(getEnnemi().getX()+16,getEnnemi().getY()+16)));

        super.seDeplacer();
    }

    // Retourne vrai si la balle détecte un ennemi ou si l'ennemi sort de la portée de la tesla
    public boolean ennemiAtteint(){
        return (!getEnv().chercherEnnemisDansPortee(getX(),getY(),getRayonAction(),1).isEmpty() || !(getEnv().chercherEnnemisDansPortee(this.Xdepart,this.Ydepart,5*16,5).contains(e)));
    }
    public Ennemi getEnnemi() {
        return e;
    }
}
