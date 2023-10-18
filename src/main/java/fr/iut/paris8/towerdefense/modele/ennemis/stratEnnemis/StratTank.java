package fr.iut.paris8.towerdefense.modele.ennemis.stratEnnemis;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.defenses.Defense;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

public class StratTank extends StrategyEnnemi{
    public StratTank(Environnement env) {
        super(env);
    }

    @Override
    // enlève les défenses qui sont sur son passage
    public void reagir(Ennemi e) {
        Defense d = this.getEnv().chercherDefenseDansPorteeEnnemi(e.getX(), e.getY(), 16);
        if(d != null) {
            d.estMort();
        }
    }
}
