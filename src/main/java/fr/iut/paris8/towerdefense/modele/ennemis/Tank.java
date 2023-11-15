package fr.iut.paris8.towerdefense.modele.ennemis;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.TerrainModele;
import fr.iut.paris8.towerdefense.modele.defenses.Defense;
import fr.iut.paris8.towerdefense.modele.ennemis.stratEnnemis.StratFreeze;
import fr.iut.paris8.towerdefense.modele.ennemis.stratEnnemis.StratTank;
import fr.iut.paris8.towerdefense.modele.ennemis.stratEnnemis.StrategyEnnemi;

public class Tank extends Ennemi{

    private StrategyEnnemi stratTank;

    public Tank () {
        super(1,100,450);
        this.stratTank = new StratFreeze(this.getEnv());
    }
    public Tank ( int x, int y) {
        super( 1,100,450);
        setPositionXProperty(x);
        setPositionYProperty(y);
    }

    @Override
    public void agir() {
        this.setPositionXProperty(this.getX()+1);
        stratTank.reagir(this);
    }
}
