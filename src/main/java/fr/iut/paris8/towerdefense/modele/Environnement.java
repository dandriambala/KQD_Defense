package fr.iut.paris8.towerdefense.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Environnement {

    private int width, height;
    private ObservableList<Ennemi> ennemis;
    private ObservableList<Defense> defenses;
    private IntegerProperty nbToursProperty;
    private TerrainModele t;

    public Environnement(TerrainModele t) {
        super();
        this.nbToursProperty = new SimpleIntegerProperty();
        this.nbToursProperty.setValue(0);
        this.defenses = FXCollections.observableArrayList();
        this.ennemis = FXCollections.observableArrayList();
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

    public ObservableList<Ennemi> getEnnemis() {
        return ennemis;
    }
    public ObservableList<Defense> getDefense() {
        return defenses;
    }

    public Ennemi getEnnemiID(String id) {
        for (Ennemi a : this.ennemis) {
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
        ennemis.add(a);
    }
    public void ajouterDefense(Defense d) {
        defenses.add(d);
    }

    public void unTour(){
        for (Defense d : getDefense()){
            d.attaquer();
        }
        for (int i=ennemis.size()-1; i >= 0; i-- ){

            if (ennemis.get(i).estVivant()){
                ennemis.get(i).agir();
            }else {
                ennemis.remove(ennemis.get(i));
            }
        }
    }

}

