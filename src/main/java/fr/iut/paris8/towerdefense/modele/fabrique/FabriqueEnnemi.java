package fr.iut.paris8.towerdefense.modele.fabrique;

import fr.iut.paris8.towerdefense.modele.ennemis.*;

public class FabriqueEnnemi {

        public Ennemi creationEnnemi(String enemyType) {
            Ennemi newEnemy = null;

            switch (enemyType) {
                case "Eclaireur":
                    newEnemy = new Eclaireur();
                    break;
                case "EnnemiBase":
                    newEnemy = new EnnemiBase();
                    break;
                case "Mastodonte":
                    newEnemy = new Mastodonte();
                    break;
                case "Tank":
                    newEnemy = new Tank();
                    break;
            }

            return newEnemy;
        }

}
