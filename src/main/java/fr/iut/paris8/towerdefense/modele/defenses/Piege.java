package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.defenses.Defense;

public abstract class Piege extends Defense {

    public Piege (int cout, Environnement env, int portee, int degats){
        super(cout,env,portee,degats);
    }

    public Piege (int cout, Environnement env, int portee, int degats, int dureeDeVie, int colonne, int ligne){
        super(cout,env,portee,degats, colonne, ligne);
    }

    public void agir(){
        if (finDeVie()){
            getEnv().enleverDefense(this);
        }
        else faireEffet();
    }

    public abstract void faireEffet();
    public abstract boolean finDeVie();

}
