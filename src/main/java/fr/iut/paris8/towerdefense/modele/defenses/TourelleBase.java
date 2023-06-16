package fr.iut.paris8.towerdefense.modele.defenses;


import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.defenses.Tourelle;

public class TourelleBase extends Tourelle {

    public TourelleBase(Environnement env) {
        super(60, env, 2, 2,10,1);
    }

    public TourelleBase(Environnement env, int colonne, int ligne) {
        super(60, env, 2,2, colonne, ligne,1);
    }
}

