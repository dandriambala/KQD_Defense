package fr.iut.paris8.towerdefense.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Defense {

    private IntegerProperty colonne;
    private IntegerProperty ligne;
    private int portee;
    private Environnement env;

    private int degats;
    private int cout;

    public Defense(int cout, Environnement env, int portee, int degats) {
        this.colonne = new SimpleIntegerProperty();
        this.ligne = new SimpleIntegerProperty();
        this.cout = cout;
        this.env = env;
        this.portee = portee*16;
        this.degats = degats;
    }

    public Defense(int cout, Environnement env, int portee, int degat, int colonne, int ligne) {
        this.colonne = new SimpleIntegerProperty(colonne);
        this.ligne = new SimpleIntegerProperty(ligne);
        this.cout = cout;
        this.env = env;
        this.portee = portee*16;
        this.degats = degat;
    }

    public  int getColonne() {
        return colonne.getValue();
    }

    public IntegerProperty colonneProperty(){
        return colonne;
    }

    public  void setColonne(int n){
        colonne.setValue(n);
    }

    public  int getLigne() {
        return ligne.getValue();
    }

    public IntegerProperty ligneProperty(){
        return ligne;
    }

    public  void setLigne(int n){
        ligne.setValue(n);
    }

    public int getDegats(){
        return this.degats;
    }

    public int getPortee() {
        return portee;
    }

    public int getCout() {
        return cout;
    }

    public Environnement getEnv() {
        return env;
    }

    // Les défenses mettront des dégâts aux ennemis
    public abstract void agir();

}
