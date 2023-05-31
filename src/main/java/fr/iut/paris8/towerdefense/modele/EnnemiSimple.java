package fr.iut.paris8.towerdefense.modele;

public class EnnemiSimple extends Ennemi{

    public EnnemiSimple(Environnement env) {
        super(8, 160, 1, 10, 100, env);
    }

    public EnnemiSimple(Environnement env, int x, int y){
        super(x, y,1, 10, 100, env);
    }

}