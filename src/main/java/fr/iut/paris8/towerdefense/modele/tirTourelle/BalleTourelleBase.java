package fr.iut.paris8.towerdefense.modele.tirTourelle;

import fr.iut.paris8.towerdefense.modele.EnMouvement;
import fr.iut.paris8.towerdefense.modele.Environnement;

// Classe représentant les balles tirées par les tourelles
public class BalleTourelleBase extends EnMouvement {
    private static int compteurBalle = 0;
    private double directionX;
    private double directionY;
    private double ennemiCibleX;
    private double ennemiCibleY;

    //le rayon dans lequel la balle détectera un ennemi
    private int rayonAction;

    public BalleTourelleBase(int positionX, int positionY, int vitesse, Environnement env, double ennemiCibleX, double ennemiCibleY, int rayonAction) {
        super(positionX + 8, positionY + 8, vitesse, env);
        this.ennemiCibleX = ennemiCibleX+16;
        this.ennemiCibleY = ennemiCibleY;
        directionX = Math.cos(directionPourCible(this.ennemiCibleX,this.ennemiCibleY));
        directionY = Math.sin(directionPourCible(this.ennemiCibleX,this.ennemiCibleY));
        this.rayonAction = rayonAction;
        setId("B" + compteurBalle);
        compteurBalle++;
         }
    public void agir(){
        seDeplacer();
}

    public double directionPourCible(double x, double y){
        double ennemiCibleX = x;
        double ennemiCibleY = y;

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
        return (Math.abs(this.getX() - this.ennemiCibleX) < rayonAction && Math.abs(this.getY() - this.ennemiCibleY) < rayonAction);
    }

}
