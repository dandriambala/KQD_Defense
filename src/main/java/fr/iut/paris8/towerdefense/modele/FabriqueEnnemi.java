package fr.iut.paris8.towerdefense.modele;

import fr.iut.paris8.towerdefense.modele.ennemis.*;

public class FabriqueEnnemi {

        public Ennemi creationEnnemi(String enemyType, Environnement env) {
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

            return newEnemy;
        }

}
