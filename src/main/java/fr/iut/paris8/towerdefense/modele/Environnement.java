package fr.iut.paris8.towerdefense.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Environnement {
    private ObservableList<EnMouvement> enMouvements;
    private ObservableList<Defense> defenses;
    private IntegerProperty nbToursProperty;
    private TerrainModele t;

    public Environnement(TerrainModele t) {
        super();
        this.nbToursProperty = new SimpleIntegerProperty();
        this.nbToursProperty.setValue(0);
        this.defenses = FXCollections.observableArrayList();
        this.enMouvements = FXCollections.observableArrayList();
        this.t = t;
    }

    public final IntegerProperty nbToursProperty() {
        return this.nbToursProperty;
    }

    public final int getNbToursProperty() {
        return this.nbToursProperty.getValue();
    }

    public final void setNbToursProperty(int n) {
        this.nbToursProperty.setValue(n);
    }

    public ObservableList<Ennemi> getEnnemis(){
            ObservableList<Ennemi> listeEnnemis = FXCollections.observableArrayList();
        for (EnMouvement em: enMouvements) {
            if (em instanceof Ennemi){
                listeEnnemis.add((Ennemi) em);
            }
        }
        return listeEnnemis;
    }
    public ObservableList<Balle> getBalles(){
        ObservableList<Balle> listeBalles = FXCollections.observableArrayList();
        for (EnMouvement em: enMouvements) {
            if (em instanceof Balle){
                listeBalles.add((Balle) em);
            }
        }
        return listeBalles;
    }
    public ObservableList<EnMouvement> getEnMouvements() {
        return enMouvements;
    }
    public ObservableList<Defense> getDefense() {
        return defenses;
    }

    public Ennemi getEnnemiID(String id) {
        for (Ennemi a : this.getEnnemis()) {
            if (a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }
    public TerrainModele getTerrainModele(){
        return this.t;
    }

    public void ajouterEnnemi(Ennemi a) {
        enMouvements.add(a);
    }
    public void ajouterDefense(Defense d) {
        defenses.add(d);
    }

    public void unTour(){
        for (Defense d : getDefense()){
            d.agir();
        }
        for (int i = enMouvements.size()-1; i >= 0; i-- ){

            if ((enMouvements.get(i) instanceof Ennemi && ((Ennemi) enMouvements.get(i)).estVivant()) || enMouvements.get(i) instanceof Balle){
                (enMouvements.get(i)).agir();
//                System.out.println("EnnemiX : " + enMouvements.get(i).getPositionXProperty() + "EnnemiY : "+ enMouvements.get(i).getPositionYProperty());
            }
            else {
                enMouvements.remove(enMouvements.get(i));
            }
        }
    }

    // Retourne le premier ennemi qui se trouve dans la portée de la défense (nombre de pixel qui sépare la tourelle de n'ennemi)

    public Ennemi chercherDansPortee(int colonne, int ligne, int portee){

        for (Ennemi ennemi : this.getEnnemis()) {
            if (((colonne-portee) <= ennemi.getPositionXProperty()) && (ennemi.getPositionXProperty()<=(colonne + portee))
                    && ((ligne- portee) <= ennemi.getPositionYProperty()) && (ennemi.getPositionYProperty() <= ligne + portee)) {
//                System.out.println("Dans portée");
                return ennemi;
            }
        }
        return null;
    }

}

