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

    public void sniper() {

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

    }

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
