package fr.iut.paris8.towerdefense.modele;

import fr.iut.paris8.towerdefense.BFS.Sommet;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

public class Ennemi {

    private String id;
    private static int compteur = 0;

    private IntegerProperty x;
    private IntegerProperty y;
    private int vitesse;
    private int prime; //L'argent que donnera l'ennemi à sa mort
    private int pv;
    private Environnement env;
    private Sommet destinationSommet;


    public Ennemi(int x, int y, int vitesse, int prime, int pv, Environnement env) {
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

    public final int getX() {
        return this.x.getValue();
    }

    public final IntegerProperty getXProperty() {
        return this.x;
    }

    public final int getY() {
        return this.y.getValue();
    }

    public final IntegerProperty getYProperty() {
        return this.y;
    }

    public boolean estVivant() {
        return this.pv >= 0;
    }

    public int getPrime() {
        return prime;
    }

    public void decrementerPv(int nb) {
        this.pv -= nb;
    }

    public int getPv () {
        return pv;
    }

    public void agir () {
        if ( destinationSommet == null )
            setDestinationSommet();

        System.out.println(getX() + " " + getY());


        if ( env.getT().dansTerrainEnnemie(this.getY() / 16, this.getX() / 16) ) {
            if ( getX() != destinationSommet.getX() ) {
                if ( destinationSommet.getX() - getX() > 0 ) {
                    avancerEnX();
                    if ( destinationSommet.getX() - getX() < 0 )
                        setDestinationSommet();
                }
                else {
                    reculerEnX();
                    if ( destinationSommet.getX() - getX() > 0 )
                        setDestinationSommet();
                }
            }
            else if ( getY() != destinationSommet.getY() ) {
                if ( destinationSommet.getY() - getY() > 0 ) {
                    monterEnY();
                    if ( destinationSommet.getY() - getY() < 0 )
                        setDestinationSommet();
                }
                else {
                    descendreEnY();
                    if ( destinationSommet.getY() - getY() > 0 )
                        setDestinationSommet();
                }
            }
            else {
                setDestinationSommet();
            }
            //TODO mettre dans l'environnement et calibrer l'ennemie rapide a cause des saut de pixel
//        else{
//        }
//
//
//
//
//
//
        }
        else
            avancerEnX();
    }


    public int getVitesse(){
        return this.vitesse;
    }

    private void avancerEnX(){
        this.x.setValue(this.x.getValue() + this.vitesse);
    }
    public void reculerEnX(){
        this.x.setValue(this.x.getValue() - this.vitesse);
    }
    private void descendreEnY(){
        this.y.setValue(this.y.getValue() + this.vitesse);
    }
    private void monterEnY(){
        this.y.setValue(this.y.getValue() - this.vitesse);
    }

    public void setDestinationSommet(){
        ArrayList<Sommet> Sommets = env.getBfs().getParcours();

        Sommet sommet = new Sommet();
        for (Sommet s : Sommets) {
            if ( s.getI() == this.x.getValue() / 16 && s.getJ() == this.y.getValue() / 16 ) {
                sommet = s;
                break;
            }
        }

        ArrayList<Sommet> chemin = env.getBfs().cheminVersSource(sommet);

        for (int i = 0; i < chemin.size(); i++) {
            if ( chemin.get(i) == sommet ) {
                if ( chemin.get(i) != chemin.get(chemin.size() - 1) )
                    destinationSommet = chemin.get(i + 1);
                else
                    destinationSommet = env.getBfs().getSource();
                break;
            }
        }
    }
}
