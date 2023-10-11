package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.Environnement;

public abstract class PiegeTemporaire extends Piege{

    private long timer; //on récupère le temps d’exécution du programme au lancement du timer
    private int dureeDeVie;

    public PiegeTemporaire (int cout, Environnement env, int portee, int degats, int dureeDeVie){
        super(cout,env,portee,degats);
        timer = System.currentTimeMillis();
        this.dureeDeVie = dureeDeVie;
    }

    @Override
    public boolean finDeVie() {
        if(System.currentTimeMillis() - timer > dureeDeVie){
            finEffet();
            return true;
        }; //tant que le temps écoulé depuis qu'on a initialisé le timer est inférieur au delay
        return false;
    }

    public abstract void finEffet();
}
