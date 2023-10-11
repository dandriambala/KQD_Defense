package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.TerrainModele;
import fr.iut.paris8.towerdefense.modele.defenses.Defense;

public abstract class Piege extends Defense {
    private int dureeDeVie;

    public Piege (int cout, int portee, int degats, int dureeDeVie){
        super(cout, portee, degats);
        this.dureeDeVie = dureeDeVie;
    }

    public Piege (int cout,  int portee, int degats, int dureeDeVie, int colonne, int ligne){
        super(cout,portee,degats, colonne, ligne);
        this.dureeDeVie = dureeDeVie;
    }

    public void agir(){
        if (finDeVie()){
            getEnv().enleverDefense(this);
        }
    }

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
