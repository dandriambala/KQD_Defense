package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.Environnement;

public abstract class PiegeTemporaire extends Piege{

    private long timer; //on récupère le temps d’exécution du programme au lancement du timer
    private int dureeDeVie;

    public PiegeTemporaire (int cout,  int portee, int degats, int dureeDeVie, int x, int y){
        super(cout,portee,degats, x, y);
        timer = System.currentTimeMillis();
        this.dureeDeVie = dureeDeVie;
    }

    public PiegeTemporaire (int cout,  int portee, int degats, int dureeDeVie){
        this(cout, portee, degats, dureeDeVie,0,0);
    }

    public void faireEffet(){
        if(System.currentTimeMillis() - timer > dureeDeVie){
            finEffet();
            this.estMort();
        }
    }
    public abstract void finEffet();
}
