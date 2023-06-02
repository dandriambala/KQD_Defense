package fr.iut.paris8.towerdefense.modele;

import java.util.ArrayList;

public class Tourelle extends Defense {

    private int nbCible;
    private int vitesseAttaque;

    public Tourelle(int cout, Environnement env, int portee, int degats, int vitesseAttaque, int nbCible) {
        super(cout, env, portee, degats);
        this.vitesseAttaque = vitesseAttaque;
        this.nbCible = nbCible;
    }

    public Tourelle(int cout, Environnement env, int portee, int degat, int colonne, int ligne, int nbCible) {
        super(cout, env, portee, degat, colonne, ligne);
        this.nbCible = nbCible;
    }

    public void attaquer(){
        ArrayList<Ennemi> ennemis = getEnv().chercherDansPortee(this.getColonne(), this.getLigne(),this.getPortee(), nbCible);

        if (!ennemis.isEmpty()) {
            for (int i = 0; i < ennemis.size(); i++) {
                getEnv().getEnMouvements().add(creerBallesDansTourelle(ennemis.get(i).getX(), ennemis.get(i).getY()));
                ennemis.get(i).decrementerPv(this.getDegats());
            }
        }
    }

    public Balle creerBallesDansTourelle(double ennemiCibleX, double ennemiCibleY){
        Balle b = new Balle(this.getColonne(), this.getLigne(), this.vitesseAttaque, getEnv(),ennemiCibleX,ennemiCibleY, 5);
        return b;
    }

    public void agir(){attaquer();}

}
