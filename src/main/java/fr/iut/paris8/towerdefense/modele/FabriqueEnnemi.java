package fr.iut.paris8.towerdefense.modele;

import fr.iut.paris8.towerdefense.modele.ennemis.*;

public class FabriqueEnnemi {

        public void createEnemyBasedOnType(String enemyType, Environnement env) {
            Ennemi newEnemy = null;

            switch (enemyType) {
                case "Eclaireur":
                    newEnemy = new Eclaireur(env);
                    break;
                case "EnnemiBase":
                    newEnemy = new EnnemiBase(env);
                    break;
                case "Mastodonte":
                    newEnemy = new Mastodonte(env);
                    break;
                case "Tank":
                    newEnemy = new Tank(env);
                    break;
            }

            if (newEnemy != null) {
                env.ajouterEnnemi(newEnemy);
            }
        }

}
