package fr.iut.paris8.towerdefense.modele.ennemis;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.ennemis.stratEnnemis.StratFreeze;
import fr.iut.paris8.towerdefense.modele.ennemis.stratEnnemis.StrategyEnnemi;

public class Eclaireur extends Ennemi {

    public Eclaireur() {
        super( 4, 20, 100);
        this.setStrat(new StratFreeze(this.getEnv()));
    }
}