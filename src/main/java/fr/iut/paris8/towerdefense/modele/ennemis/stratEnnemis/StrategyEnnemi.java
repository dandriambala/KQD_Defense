package fr.iut.paris8.towerdefense.modele.ennemis.stratEnnemis;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

public abstract class StrategyEnnemi {


    public StrategyEnnemi(){
    }

    public abstract void reagir(Ennemi e);

}
