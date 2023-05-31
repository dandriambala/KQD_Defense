package fr.iut.paris8.towerdefense.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Defense {

    private IntegerProperty colonne;
    private IntegerProperty ligne;

    private int cout;
    private Environnement env;
    private int portee;
    private int degats;


    public Defense(int cout, Environnement env, int portee, int degats) {
        this.colonne = new SimpleIntegerProperty();
        this.ligne = new SimpleIntegerProperty();
        this.cout = cout;
        this.env = env;
        this.portee = portee * 16;
        this.degats = degats;
    }

    public Defense(int cout, Environnement env, int portee, int degat, int colonne, int ligne) {
        this.colonne = new SimpleIntegerProperty(colonne);
        this.ligne = new SimpleIntegerProperty(ligne);
        this.cout = cout;
        this.env = env;
        this.portee = portee * 16;
        this.degats = degat;
    }

    public int getColonne() {
        return colonne.getValue();
    }

    public IntegerProperty colonneProperty() {
        return colonne;
    }

    public void setColonne(int n) {
        colonne.setValue(n);
    }

    public int getLigne() {
        return ligne.getValue();
    }

    public IntegerProperty ligneProperty() {
        return ligne;
    }

    public void setLigne(int n) {
        ligne.setValue(n);
    }

    public int getDegats() {
        return this.degats;
    }

    // Les défenses mettront des dégâts aux ennemis
    public abstract void attaquer();


// Retourne le premier ennemi qui se trouve dans la portée de la défense (nombre de pixel qui sépare la tourelle de n'ennemi)

    public Ennemi chercherDansPortee() {

        for (Ennemi ennemi : this.env.getEnnemis()) {
            if (((this.getColonne() - this.portee) <= ennemi.getX()) && (ennemi.getX() <= (this.getColonne() + portee))){
                System.out.println("Dans portée");
                return ennemi;
            }
        }
        return null;
    }


}
