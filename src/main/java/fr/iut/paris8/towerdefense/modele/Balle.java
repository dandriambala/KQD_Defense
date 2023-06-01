package fr.iut.paris8.towerdefense.modele;

// Classe représentant les balles tirées par les tourelles
public class Balle extends EnMouvement{
    private static int compteurBalle = 0;

    private double ennemiCibleX;
    private double ennemiCibleY;

    public Balle(int positionX, int positionY, int vitesse, Environnement env, double ennemiCibleX, double ennmiCibleY) {
        super(positionX, positionY, vitesse, env);
        this.ennemiCibleX = ennemiCibleX;
        this.ennemiCibleY = ennmiCibleY;
        setDirection(directionPourCible(ennemiCibleX,ennmiCibleY));
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
//        double direction = Math.atan2(ennemiCibleY - getPositionXProperty(), ennemiCibleX - getPositionYProperty());
        double direction = Math.atan((ennemiCibleY - getPositionXProperty())/(ennemiCibleX - getPositionYProperty()));
        return direction;
    }
   
    // Méthode pour mettre à jour la position de la balle à chaque itération
    public void seDeplacer() {

//        System.out.println("PosX : " + getPositionXProperty() + getVitesse() * Math.cos(getDirection()) + " PosY : " + getPositionYProperty() + getVitesse() * Math.sin(getDirection()));

        if(!(this.getPositionXProperty() >= this.ennemiCibleX && this.getPositionYProperty() >= this.ennemiCibleY)){
            setPositionXProperty(getPositionXProperty() + getVitesse() * Math.cos(getDirection()));
            setPositionYProperty(getPositionYProperty() + getVitesse() * Math.sin(getDirection()));
//            setDirection(0);

        }
        else
            setVitesse(0);
    }
}
