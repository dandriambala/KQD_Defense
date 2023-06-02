package fr.iut.paris8.towerdefense.modele;

import java.util.ArrayList;

public class Tesla extends Tourelle{

    public Tesla(Environnement env) {
        super(150, env, 5, 1, 10,5);
    }

    public Tesla(Environnement env, int colonne, int ligne) {
        super(150, env, 5,10, colonne, ligne,5);
    }


}
