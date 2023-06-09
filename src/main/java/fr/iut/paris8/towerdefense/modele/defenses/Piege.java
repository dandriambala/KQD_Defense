package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.defenses.Defense;

public abstract class Piege extends Defense {
    private int dureeDeVie;

    public Piege (int cout, Environnement env, int portee, int degats, int dureeDeVie){
        super(cout,env,portee,degats);
        this.dureeDeVie = dureeDeVie;
    }
    public abstract void agir();

    public boolean finDeVie(){
        return this.dureeDeVie == 0;
    }

    public int getDureeDeVie() {
        return dureeDeVie;
    }

    public void setDureeDeVie(int dureeDeVie) {
        this.dureeDeVie = dureeDeVie;
    }
}
