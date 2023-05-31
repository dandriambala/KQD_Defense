package fr.iut.paris8.towerdefense.modele;

public class EnnemiRapide extends Ennemi{

    public EnnemiRapide(Environnement env) {
        super(8, 160, 3, 10, 100, env);
    }

    public EnnemiRapide(Environnement env, int x, int y){

        super(x, y,3, 10, 100, env);
    }
}