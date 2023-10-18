package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.TerrainModele;
import fr.iut.paris8.towerdefense.modele.tirTourelle.Balle;
import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

import java.util.ArrayList;

public abstract class Tourelle extends Defense {

    private int nbCible;
    private int vitesseAttaque;
    private static int compteurTourelle = 0;
    public Tourelle(int cout, int portee, int degats, int vitesseAttaque, int nbCible) {
        super(cout, portee, degats);
        this.vitesseAttaque = vitesseAttaque;
        this.nbCible = nbCible;
        this.setId("T" + compteurTourelle);
        compteurTourelle++;
    }


    public Tourelle(int cout, int portee, int degat, int vitesseAttaque, int colonne, int ligne, int nbCible) {
        super(cout, portee, degat, colonne, ligne);
        this.nbCible = nbCible;
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

    public abstract Balle creerBalle(int positionX, int positionY, int vitesse, Ennemi e, int rayonAction);

    public Balle creerBallesDansTourelle(Ennemi e){
        Balle b = creerBalle(this.getColonne(), this.getLigne(), this.vitesseAttaque, e, this.vitesseAttaque+1);
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
