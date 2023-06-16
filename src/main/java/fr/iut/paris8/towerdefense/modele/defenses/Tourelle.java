package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.tirTourelle.Balle;
import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

import java.util.ArrayList;

public class Tourelle extends Defense {

    private int nbCible;
    private int vitesseAttaque;
    private static int compteurTourelle = 0;
    public Tourelle(int cout, Environnement env, int portee, int degats, int vitesseAttaque, int nbCible) {
        super(cout, env, portee, degats);
        this.vitesseAttaque = vitesseAttaque;
        this.nbCible = nbCible;
        this.setId("T" + compteurTourelle);
        compteurTourelle++;
    }


    public void attaquer(){
        ArrayList<Ennemi> ennemis = getEnv().chercherEnnemisDansPortee(this.getColonne(), this.getLigne(),this.getPortee(), nbCible);

        if (!ennemis.isEmpty()) {
            for (int i = 0; i < ennemis.size(); i++) {
                creerBallesDansTourelle(ennemis.get(i));
                ennemis.get(i).decrementerPv(this.getDegats());
            }
        }
    }

    public Balle creerBallesDansTourelle(Ennemi e){
        Balle b = new Balle(this.getColonne(), this.getLigne(), this.vitesseAttaque, getEnv(),e, 16, "normal");
        getEnv().getEnMouvements().add(b);
        return b;
    }

    public int getNbCible() {
        return nbCible;
    }

    public void agir(){attaquer();}

    public int getVitesseAttaque() {
        return vitesseAttaque;
    }
}
