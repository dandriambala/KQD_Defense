package fr.iut.paris8.towerdefense.modele;

public class TourelleBase extends Defense{
    private int vitesseAttaque = 10;

    public TourelleBase(Environnement env) {
        super(60, env, 2, 3) ;
    }
    public void agir(){attaquer();}
    public void attaquer(){
        Ennemi e = getEnv().chercherDansPortee(this.getColonne(), this.getLigne(),this.getPortee());
        if (e!= null) {
            getEnv().getEnMouvements().add(creerBallesDansTourelle(e.getPositionXProperty(), e.getPositionYProperty()));
        }
    }
    public Balle creerBallesDansTourelle(double ennemiCibleX, double ennemiCibleY){
        Balle b = new Balle(this.getColonne(), this.getLigne(), this.vitesseAttaque, getEnv(),ennemiCibleX,ennemiCibleY);
        return b;
    }

}

