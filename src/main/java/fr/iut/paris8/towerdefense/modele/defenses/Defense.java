package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.TerrainModele;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Defense {

    private IntegerProperty colonne;
    private IntegerProperty ligne;
    private int portee;
    private Environnement env;
    private String id;
    private int degats;
    private int cout;

    public Defense(int cout,  int portee, int degats) {
        this.env = Environnement.getInstance(TerrainModele.getInstance());
        this.colonne = new SimpleIntegerProperty();
        this.ligne = new SimpleIntegerProperty();
        this.cout = cout;
        this.portee = portee*16;
        this.degats = degats;
    }

    public Defense(int cout, int portee, int degats, int colonne, int ligne) {
        this.colonne = new SimpleIntegerProperty(colonne);
        this.ligne = new SimpleIntegerProperty(ligne);
        this.cout = cout;
        this.env = Environnement.getInstance(TerrainModele.getInstance());
        this.portee = portee*16;
        this.degats = degats;
    }

    public  int getColonne() {
        return colonne.getValue();
    }


    public  void setColonne(int n){
        colonne.setValue(n);
    }

    public  int getLigne() {
        return ligne.getValue();
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
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    // Les défenses mettront des dégâts aux ennemis
    public abstract void agir();

}
