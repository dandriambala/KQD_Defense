package fr.iut.paris8.towerdefense.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RessourceJeu {

    private IntegerProperty pv;
    private IntegerProperty argent;

    public RessourceJeu() {
        this.pv = new SimpleIntegerProperty(150);
        this.argent = new SimpleIntegerProperty(150);
    }

    public int getPv() {
        return pv.getValue();
    }

    public IntegerProperty pvProperty() {
        return pv;
    }

    public int getArgent() {
        return argent.getValue();
    }

    public IntegerProperty argentProperty() {
        return argent;
    }

    public void setPv(int pv) {
        this.pv.set(pv);
    }

    public void setArgent(int argent) {
        this.argent.set(argent);
    }

    public void mortDUnEnnemi(int prime){
        setArgent(getArgent() + prime);
    }

    public void ennemiEntrerDansLaBase(int a){
        setPv(getPv()-a);
    }


    public boolean partiePerdu(){return getPv() <= 0;}

    public void achatTourelle(int cout){

        if (peutEncoreAcheter(cout)){
            setArgent(getArgent() - cout);
        }

    }

    public boolean peutEncoreAcheter(int cout){

            return (getArgent()>= cout);
    }


}
