package fr.iut.paris8.towerdefense.modele.ennemis;

import fr.iut.paris8.towerdefense.BFS.Case;
import fr.iut.paris8.towerdefense.modele.EnMouvement;
import fr.iut.paris8.towerdefense.modele.Environnement;

import java.util.ArrayList;

public abstract class Ennemi extends EnMouvement {
    private static int compteurEnnemi = 0;
    private int prime; //L'argent que donnera l'ennemi à sa mort
    private int pv;
    private Case destinationCase;

    public Ennemi( int vitesse,  int prime, int pv, Environnement env) {
      super(0,160,vitesse,env);
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
        if ( destinationCase == null )
            setDestinationSommet();


//
//        System.out.println("Sommet\nX : " + destinationSommet.getX() + " Y : " + destinationSommet.getY());
//        System.out.println("I : " + destinationCase.getColonne() + " J : " + destinationCase.getLigne());
        for (int i = 0; i <= getVitesse(); i++) {

            if ( getEnv().getTerrainModele().dansTerrainEnnemie(this.getY() / 16, this.getX() / 16) || this.getX() <= 16 ) {
                if ( getX() != destinationCase.getX() ) {
                    if ( destinationCase.getX() - getX() > 0 ) {
                        avancerEnX();
                        if ( destinationCase.getX() - getX() < 0 )
                            setDestinationSommet();
                    }
                    else {
                        reculerEnX();
                        if ( destinationCase.getX() - getX() > 0 )
                            setDestinationSommet();
                    }
                }
                else if ( getY() != destinationCase.getY() ) {
                    if ( destinationCase.getY() - getY() > 0 ) {
                        descendreEnY();
                        if ( destinationCase.getY() - getY() < 0 )
                            setDestinationSommet();
                    }
                    else {
                        monterEnY();
                        if ( destinationCase.getY() - getY() > 0 )
                            setDestinationSommet();
                    }
                }
                else {
                    setDestinationSommet();
                }
             }
            else{
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
                    destinationCase = chemin.get(i + 1);
                else
                    destinationCase = getEnv().getBfs().getSource();
                break;
            }
        }
    }
}


