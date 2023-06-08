package fr.iut.paris8.towerdefense.modele;


public class TourelleBase extends Tourelle{

    //vitesse de déplacement des balles
    private int vitesseAttaque = 10;


    public TourelleBase(Environnement env) {
        super(60, env, 2, 0,10,1);
    }

}

