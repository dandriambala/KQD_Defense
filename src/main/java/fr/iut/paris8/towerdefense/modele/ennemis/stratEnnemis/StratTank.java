package fr.iut.paris8.towerdefense.modele.ennemis.stratEnnemis;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.defenses.Defense;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

import java.util.ArrayList;

public class StratTank extends StrategyEnnemi{
    public StratTank(Environnement env) {
        super(env);
    }

    @Override
    // enlève les défenses qui sont sur son passage
    public void reagir(Ennemi e) {
        ArrayList<Defense> def = this.getEnv().chercherDefenseDansPorteeEnnemi(e.getX(), e.getY(), 16, 1);
        if (!def.isEmpty()) {
            for (int i = 0; i < def.size(); i++)
                def.get(i).estMort();
        }
    }
}
