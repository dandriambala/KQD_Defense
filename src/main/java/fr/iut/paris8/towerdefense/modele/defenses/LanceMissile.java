package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.tirTourelle.Balle;
import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;
import fr.iut.paris8.towerdefense.modele.tirTourelle.Missile;

import java.util.ArrayList;

public class LanceMissile extends Tourelle {

    private int TEMPS = 10;
    private int cooldown = 0;
    private Balle balleActuelle;

    public LanceMissile(Environnement env) {
        super(100, env, 4, 20, 4, 1);
        this.balleActuelle = null;
    }

    public void sniper() {

        ArrayList<Ennemi> ennemis = getEnv().chercherEnnemisDansPortee(this.getColonne(), this.getLigne(), this.getPortee(), getNbCible());

        if (!ennemis.isEmpty() && balleActuelle == null) {
            ArrayList<Ennemi> degatEnChaine = getEnv().chercherEnnemisDansPortee(ennemis.get(0).getX(), ennemis.get(0).getY(), 32, 5);

            if (!degatEnChaine.isEmpty()) {
                balleActuelle = creerBallesDansTourelle(ennemis.get(0));

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
    public Balle creerBallesDansTourelle(Ennemi e){
        Balle b = new Missile(this.getColonne(), this.getLigne(), getVitesseAttaque(), getEnv(),e);
        getEnv().getEnMouvements().add(b);
        return b;
    }
}
