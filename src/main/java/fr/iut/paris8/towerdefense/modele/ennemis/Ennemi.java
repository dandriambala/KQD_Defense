package fr.iut.paris8.towerdefense.modele.ennemis;

import fr.iut.paris8.towerdefense.BFS.Case;
import fr.iut.paris8.towerdefense.modele.EnMouvement;
import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.TerrainModele;

import java.util.ArrayList;

public abstract class Ennemi extends EnMouvement {
    private static int compteurEnnemi = 0;
    private int prime; //L'argent que donnera l'ennemi à sa mort
    private int pv;
    private Case destinationCase;
    private BarreDeVie barreDeVie;
    private int pvMax;



    public Ennemi( int vitesse,  int prime, int pv) {
      super(0,160,vitesse);
        this.prime = prime;
        this.pv = pv;
        this.pvMax=pv;
        setId("E"+ compteurEnnemi);
        compteurEnnemi++;
        this.barreDeVie = new BarreDeVie(getPv(), getPvMax(), getId(), getX(), getY());

    }

    public Ennemi(int x, int y, int vitesse,  int prime, int pv){
        super(x, y, vitesse);
        this.prime = prime;
        this.pv = pv;
        this.pvMax=pv;
        setId("E"+ compteurEnnemi);
        compteurEnnemi++;
        this.barreDeVie = new BarreDeVie(getPv(), getPvMax(), getId(), getX(), getY());

    }

    public int getPv(){return this.pv;}

    public int getPvMax() {
        return pvMax;
    }

    public BarreDeVie getBarreDeVie() {
        return barreDeVie;
    }

    public boolean estVivant() {
        return this.pv > 0;
    }

    public int getPrime () {
        return prime;
    }

    public void decrementerPv(int nb){
        this.pv-=nb;
    }


    public void agir () {
        TerrainModele t = getEnv().getTerrainModele();
       if (t.dansTerrain(this.getY() / 16, this.getX() / 16)  && estVivant()) {

            if (destinationCase == null)
                setDestinationSommet();

            for (int i = 0; i <= getVitesse(); i++) {

                if (getEnv().getTerrainModele().dansTerrainEnnemie(this.getY() / 16, this.getX() / 16) || this.getX() <= 16) {
                    if (getX() != destinationCase.getX()) {
                        if (destinationCase.getX() - getX() > 0) {
                            avancerEnX();
                            if (destinationCase.getX() - getX() < 0)
                                setDestinationSommet();
                        } else {
                            reculerEnX();
                            if (destinationCase.getX() - getX() > 0)
                                setDestinationSommet();
                        }
                    } else if (getY() != destinationCase.getY()) {
                        if (destinationCase.getY() - getY() > 0) {
                            descendreEnY();
                            if (destinationCase.getY() - getY() < 0)
                                setDestinationSommet();
                        } else {
                            monterEnY();
                            if (destinationCase.getY() - getY() > 0)
                                setDestinationSommet();
                        }
                    } else {
                        setDestinationSommet();
                    }
                } else {
                    avancerEnX();
                }
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

        Case sommet = new Case(getX()/16,getY()/16);

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

    public boolean estTerminé(){
        if (estVivant() && !(getEnv().getTerrainModele().dansTerrainEnnemie(this.getY() / 16, this.getX() / 16))){
            int pvAEnlever = getPv()/25;
            if (pvAEnlever < 1){
                pvAEnlever = 1;
            }
            getEnv().getRessourceJeu().diminuePv(pvAEnlever);
            return true;
        }
        else if (!estVivant()  && getEnv().getTerrainModele().dansTerrainEnnemie(this.getY() / 16, this.getX() / 16)){
            getEnv().getRessourceJeu().mortDUnEnnemi(getPrime());
            return true;
        }
        return false;
    }
}


