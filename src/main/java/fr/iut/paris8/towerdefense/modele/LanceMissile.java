package fr.iut.paris8.towerdefense.modele;

import java.util.ArrayList;

public class LanceMissile extends Tourelle {

    private static int TEMPS = 10;
    private static int cooldown = 0;
    private Balle balleActuelle;

    public LanceMissile(Environnement env) {
        super(1500, env, 3, 10, 4, 1);
        this.balleActuelle = null;
    }

    public void sniper() {

        ArrayList<Ennemi> ennemis = getEnv().chercherDansPortee(this.getColonne(), this.getLigne(), this.getPortee(), getNbCible());

        if (!ennemis.isEmpty() && balleActuelle == null) {
            ArrayList<Ennemi> degatEnChaine = getEnv().chercherDansPortee(ennemis.get(0).getX(), ennemis.get(0).getY(), 32, 5);

            if (!degatEnChaine.isEmpty()) {
                balleActuelle = creerBallesDansTourelle(ennemis.get(0).getX(), ennemis.get(0).getY());

                for (int j = 0; j < degatEnChaine.size(); j++) {
                    degatEnChaine.get(j).decrementerPv(this.getDegats());
                }
            }
        } else
                balleActuelle = null;

    }

    public void attaquer() {
        if (cooldown == 0) {
            cooldown = TEMPS;
            sniper();
        }

        if (cooldown > 0) {
            cooldown--;
            if (cooldown == TEMPS - 1 && balleActuelle != null && balleActuelle.ennemiAtteint()) {
                balleActuelle = null;
            }
        }
    }
}
