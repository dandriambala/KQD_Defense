package fr.iut.paris8.towerdefense.modele.ennemis.stratEnnemis;

import fr.iut.paris8.towerdefense.modele.Direction;
import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.defenses.Defense;
import fr.iut.paris8.towerdefense.modele.defenses.Tourelle;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

import java.util.ArrayList;

public class StratFreeze extends StrategyEnnemi {

    public StratFreeze(Environnement env) {
        super(env);

    }

    @Override
    public void reagir(Ennemi e) {
        if (e.estTerminé()) {
            ArrayList<Tourelle> def = this.getEnv().chercherTourelleDansPorteeEnnemi(e.getX(), e.getY(), 32, 24, Direction.toute);

            if (!def.isEmpty()) {
                for (int i = 0; i < def.size(); i++) {
                    def.get(i).neutralise();
                }

            }
        }
    }
}
