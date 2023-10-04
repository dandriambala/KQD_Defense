package fr.iut.paris8.towerdefense.modele;

import fr.iut.paris8.towerdefense.modele.ennemis.Mastodonte;
import fr.iut.paris8.towerdefense.modele.ennemis.Tank;

public class VaguePermutation extends Vague {
    private boolean permuter = false; // Cette variable détermine l'alternance entre les types d'ennemis

    @Override
    public void faireApparaitreEnnemi(Environnement env) {
        if (permuter) {
            env.ajouterEnnemi(new Tank(env)); // Ajoutez le premier type d'ennemi
        } else {
            env.ajouterEnnemi(new Mastodonte(env)); // Ajoutez le deuxième type d'ennemi
        }
        permuter = !permuter; // Alternez entre les types d'ennemis à chaque tour
    }
}