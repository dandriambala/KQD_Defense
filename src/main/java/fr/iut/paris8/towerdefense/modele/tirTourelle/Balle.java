package fr.iut.paris8.towerdefense.modele.tirTourelle;

import fr.iut.paris8.towerdefense.modele.EnMouvement;
import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.TerrainModele;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

// Classe représentant les balles tirées par les tourelles
public class Balle extends EnMouvement {
    private static int compteurBalle = 0;
    private double directionX;
    private double directionY;
    private String type;

    //le rayon dans lequel la balle détectera un ennemi
    private int rayonAction;

    public Balle(int positionX, int positionY, int vitesse, Ennemi e, int rayonAction, String type) {
        super(positionX + 8, positionY + 8, vitesse);
        directionX = Math.cos(directionPourCible(e.getX()+16,e.getY()+16));
        directionY = Math.sin(directionPourCible(e.getX()+16,e.getY()+16));

        this.rayonAction = rayonAction;
        this.type = type;
        setId("B" + compteurBalle);
        compteurBalle++;
         }

    public void agir(){
        if (!ennemiAtteint())
            seDeplacer();
    }

    public double directionPourCible(double ennemiCibleX, double ennemiCibleY){

        // Calculer la direction vers l'ennemi cible
        double direction = Math.atan2(ennemiCibleY - getY(), ennemiCibleX - getX());
        return direction;
    }
   
    // Méthode pour mettre à jour la position de la balle à chaque itération
    public void seDeplacer() {
            setPositionXProperty((int) (getX() + getVitesse() * this.directionX));
            setPositionYProperty((int) (getY() + getVitesse() * this.directionY));
    }

    public boolean ennemiAtteint(){
        return !getEnv().chercherEnnemisDansPortee(getX()-8,getY()-8,rayonAction,1).isEmpty();
    }

    public String getType() {
        return type;
    }

    public double getDirectionX() {
        return directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public int getRayonAction() {
        return rayonAction;
    }


    public boolean estTerminé() {
        return ennemiAtteint();
    }
    public void setDirectionX(double directionX) {
        this.directionX = directionX;
    }

    public void setDirectionY(double directionY) {
        this.directionY = directionY;
    }
}
