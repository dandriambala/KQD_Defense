package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.TerrainModele;
import fr.iut.paris8.towerdefense.modele.defenses.Defense;

public abstract class Piege extends Defense {


    public Piege (int cout, int portee, int degats){
        super(cout,portee,degats);
    }

    public Piege (int cout,int portee, int degats, int colonne, int ligne){
        super(cout,portee,degats, colonne, ligne);

    }

    public void agir(){
        faireEffet();
    }

    public abstract void faireEffet();

}
