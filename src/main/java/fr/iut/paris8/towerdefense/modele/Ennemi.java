package fr.iut.paris8.towerdefense.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Ennemi {

    private String id;
    private static int compteur = 0;

    private IntegerProperty x;
    private IntegerProperty y;
    private int vitesse;
    private int prime; //L'argent que donnera l'ennemi à sa mort
    private int pv;
    private Environnement env;



    public Ennemi (int x, int y, int vitesse, int prime, int pv, Environnement env ) {
        this.env = env;
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.vitesse = vitesse;
        this.prime = prime;
        this.pv = pv;
        this.id = "E" + compteur;
        compteur++;
    }
    public String getId() {
        return id;
    }
    public final int getX () {
        return this.x.getValue();
    }

    public final IntegerProperty getXProperty () {
        return this.x;
    }

    public final int getY () {
        return this.y.getValue();
    }

    public final IntegerProperty getYProperty () {
        return this.y;
    }

    public boolean estVivant () {
        return this.pv >= 0;
    }

    public int getPrime () {
        return prime;
    }
    public void decrementerPv(int nb){
        this.pv-=nb;
    }

    //TODO à enlever lorsque le BFS fonctionnera
    public void agir () {
        if (this.getX() < env.getTerrainModele().getWidth()) {
            this.x.setValue(getX() + this.vitesse);
            //System.out.println("Ennemi :" +this.getX()+ " " +this.getY());
        }
    }
    public int getVitesse(){
        return this.vitesse;
    }
    public int getPv(){return this.pv;}
}