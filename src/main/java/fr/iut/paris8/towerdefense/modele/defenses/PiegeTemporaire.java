package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.Environnement;

public abstract class PiegeTemporaire extends Piege{

    private long timer; //on récupère le temps d’exécution du programme au lancement du timer
    private int dureeDeVie;

    public PiegeTemporaire (int cout,  int portee, int degats, int dureeDeVie){
        super(cout,portee,degats);
        timer = System.currentTimeMillis();
        this.dureeDeVie = dureeDeVie;
    }

    public void faireEffet(){
        if(System.currentTimeMillis() - timer > dureeDeVie){
            finEffet();
            this.estMort();
        }
    }
    public abstract void finEffet();
}
