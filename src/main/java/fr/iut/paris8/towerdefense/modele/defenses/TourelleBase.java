package fr.iut.paris8.towerdefense.modele.defenses;


import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.defenses.Tourelle;

public class TourelleBase extends Tourelle {

    public TourelleBase() {
        super(60, 2, 4,10,1);
    }

    public TourelleBase( int colonne, int ligne) {
        super(60, 2,4,10, colonne, ligne,1);
    }
}

