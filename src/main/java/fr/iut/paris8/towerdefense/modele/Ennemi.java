package fr.iut.paris8.towerdefense.modele;

import fr.iut.paris8.towerdefense.BFS.Case;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.Map;

public abstract class Ennemi extends EnMouvement{
    private static int compteurEnnemi = 0;
    private int prime; //L'argent que donnera l'ennemi à sa mort
    private int pv;
    private Case destinationSommet;

    public Ennemi(int x, int y, int vitesse,  int prime, int pv, Environnement env) {
      super(x,y,vitesse,env);
        this.prime = prime;
        this.pv = pv;
        setId("E"+ compteurEnnemi);
        compteurEnnemi++;
    }
    public int getPv(){return this.pv;}

    public boolean estVivant () {
        return this.pv >= 0;
    }

    public int getPrime () {
        return prime;
    }
    public void decrementerPv(int nb){
        this.pv-=nb;
    }


    public void agir () {
        if ( destinationSommet == null )
            setDestinationSommet();

        System.out.println("Pos ennemie " + getId() + "\n X : " + getX() + " Y : " + getY() + "  I : " + getX() / 16 + " J : " + getY() / 16);
//
//        System.out.println("Sommet\nX : " + destinationSommet.getX() + " Y : " + destinationSommet.getY());
//        System.out.println("I : " + destinationSommet.getColonne() + " J : " + destinationSommet.getLigne());
        for (int i = 1; i <= getVitesse(); i++) {
            if ( getEnv().getTerrainModele().dansTerrainEnnemie(this.getY() / 16, this.getX() / 16) || this.getX() <= 16 ) {
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
                        descendreEnY();
                        if ( destinationSommet.getY() - getY() < 0 )
                            setDestinationSommet();
                    }
                    else {
                        monterEnY();
                        if ( destinationSommet.getY() - getY() > 0 )
                            setDestinationSommet();
                    }
                }
                else {
                    setDestinationSommet();
                }
             }
            else{
                System.out.println("jfiozfnoznfpoak,dpladpl");
                avancerEnX();
            }
        }
    }

    private void avancerEnX(){
        setPositionXProperty(getX() + 1);
    }
    public void reculerEnX(){
        setPositionXProperty(getX() - 1 );
    }
    private void descendreEnY(){
        setPositionYProperty(getY() + 1);
    }
    private void monterEnY(){
        setPositionYProperty(getY() - 1);
    }

    public void setDestinationSommet(){
        ArrayList<Case> Sommets = getEnv().getBfs().getParcours();

        Case sommet = new Case();
        for (Case s : Sommets) {
            if ( s.getColonne() == getX() / 16 && s.getLigne() == getY() / 16 ) {
                sommet = s;
                break;
            }
        }

        ArrayList<Case> chemin = getEnv().getBfs().cheminVersSource(sommet);

        for (int i = 0; i < chemin.size(); i++) {
            if ( chemin.get(i) == sommet ) {
                if ( chemin.get(i) != chemin.get(chemin.size() - 1) )
                    destinationSommet = chemin.get(i + 1);
                else
                    destinationSommet = getEnv().getBfs().getSource();
                break;
            }
        }
    }
}


