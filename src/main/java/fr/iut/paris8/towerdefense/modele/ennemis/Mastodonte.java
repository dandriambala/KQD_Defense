package fr.iut.paris8.towerdefense.modele.ennemis;

import fr.iut.paris8.towerdefense.modele.Environnement;

public class Mastodonte extends Ennemi{


    public Mastodonte(Environnement env, int x, int y){
        super(x, y, 1,50,300, env);
    }

    public Mastodonte (Environnement env) { super(1,50,300, env);}

}
