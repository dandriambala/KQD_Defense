package fr.iut.paris8.towerdefense.modele.defenses;


import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.defenses.Tourelle;

public class TourelleBase extends Tourelle {

    //vitesse de déplacement des balles
    private int vitesseAttaque = 10;


    public TourelleBase(Environnement env) {
        super(60, env, 2, 2,10,1);
    }

}

