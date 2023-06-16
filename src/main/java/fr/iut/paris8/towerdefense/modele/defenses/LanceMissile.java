package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.tirTourelle.BalleTourelleBase;
import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

import java.util.ArrayList;

public class LanceMissile extends Tourelle {

    private static int TEMPS = 10;
    private static int cooldown = 0;
    private BalleTourelleBase balleTourelleBaseActuelle;

    public LanceMissile(Environnement env) {
        super(1500, env, 3, 10, 4, 1);
        this.balleTourelleBaseActuelle = null;
    }


    public LanceMissile(Environnement env, int colonne, int ligne) {
        super(1500, env, 3, 10, colonne, ligne, 1 );
        this.balleTourelleBaseActuelle = null;
    }

     private void sniper() {

        ArrayList<Ennemi> ennemis = getEnv().chercherEnnemisDansPortee(this.getColonne(), this.getLigne(), this.getPortee(), getNbCible());

        if (!ennemis.isEmpty() && balleTourelleBaseActuelle == null) {
            ArrayList<Ennemi> degatEnChaine = getEnv().chercherEnnemisDansPortee(ennemis.get(0).getX(), ennemis.get(0).getY(), 32, 5);

            if (!degatEnChaine.isEmpty()) {
                balleTourelleBaseActuelle = creerBallesDansTourelle(ennemis.get(0).getX(), ennemis.get(0).getY());

                for (int j = 0; j < degatEnChaine.size(); j++) {
                    degatEnChaine.get(j).decrementerPv(this.getDegats());
                }
            }
        } else
                balleTourelleBaseActuelle = null;
        // on remet la balle à null pour ne pas avoir le cas où le missile ne tire plus car il n'a pas touché sa cible
    }

    /**
     * La tourelle effectue une attaque lorsqu'elle n'est pas en période de "cooldown".
     * Lors de l'attaque, la tourelle recherche les ennemis à portée et sélectionne le premier ennemi trouvé.
     * Ensuite, elle recherche les ennemis à portée de ce premier ennemi, et si elle en trouve, elle inflige des dégâts à ces ennemis.
     * Les dégâts en chaîne sont réalisés grâce à une balle spécifique (balleTourelleBaseActuelle) créée par la tourelle.
     * Cette balle est utilisée pour infliger des dégâts aux ennemis dans la zone de dégâts en chaîne.
     * Une fois que l'attaque est terminée, la période de "cooldown" est réinitialisée.
     */
    public void attaquer() {
        if (cooldown == 0) {
            cooldown = TEMPS;
            sniper();
        }

        if (cooldown > 0) {
            cooldown--;
            if (cooldown == TEMPS - 1 && balleTourelleBaseActuelle != null && balleTourelleBaseActuelle.ennemiAtteint()) {
                balleTourelleBaseActuelle = null;
            }
        }
    }
}
