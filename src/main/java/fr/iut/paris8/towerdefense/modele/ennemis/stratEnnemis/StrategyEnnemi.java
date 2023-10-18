package fr.iut.paris8.towerdefense.modele.ennemis.stratEnnemis;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

public abstract class StrategyEnnemi {

    private Environnement env;

    public StrategyEnnemi(Environnement env){
        this.env = env;
    }

    public abstract void reagir(Ennemi e);

    public Environnement getEnv() {
        return env;
    }
}
