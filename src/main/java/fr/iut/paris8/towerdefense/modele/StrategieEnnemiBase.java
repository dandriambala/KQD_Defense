package fr.iut.paris8.towerdefense.modele;

import fr.iut.paris8.towerdefense.modele.ennemis.EnnemiBase;

public class StrategieEnnemiBase implements StrategieVague{
    @Override
    public void trouverVague(Environnement env) {
        // Générer un ennemi de type "EnnemiBase"
        env.ajouterEnnemi(new EnnemiBase(env));
    }
}
