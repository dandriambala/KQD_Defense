package fr.iut.paris8.towerdefense.modele;

import fr.iut.paris8.towerdefense.modele.ennemis.EnnemiBase;

public class VagueEnnemiBase extends Vague{
    @Override
    public void faireApparaitreEnnemi(Environnement env) {
        // Générer un ennemi de type "EnnemiBase"
        env.ajouterEnnemi(new EnnemiBase(env));
    }
}
