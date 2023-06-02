package fr.iut.paris8.towerdefense.modele;

// Classe représentant les balles tirées par les tourelles
public class Balle extends EnMouvement{
    private static int compteurBalle = 0;
    private double directionX;
    private double directionY;
    private double ennemiCibleX;
    private double ennemiCibleY;
    private int rayonAction;

    public Balle(int positionX, int positionY, int vitesse, Environnement env, double ennemiCibleX, double ennemiCibleY, int rayonAction) {
        super(positionX, positionY, vitesse, env);
        this.ennemiCibleX = ennemiCibleX;
        this.ennemiCibleY = ennemiCibleY;
        directionX = Math.cos(directionPourCible(ennemiCibleX,ennemiCibleY));
        directionY = Math.sin(directionPourCible(ennemiCibleX,ennemiCibleY));
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
