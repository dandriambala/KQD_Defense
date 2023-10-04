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
        super(100, env, 4, 30, 4, 1);
        this.balleActuelle = null;
    }
    public LanceMissile(Environnement env, int colonne, int ligne) {
        super(100, env, 4, 30, 4, 1);
        this.balleActuelle = null;
        setColonne(colonne);
        setLigne(ligne);
    }

     private void sniper() {

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
        // on remet la balle à null pour ne pas avoir le cas où le missile ne tire plus car il n'a pas touché sa cible

                balleActuelle = null;

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
            if (cooldown == TEMPS - 1 && balleActuelle != null && balleActuelle.ennemiAtteint()) {
                balleActuelle = null;
            }
        }
    }


    @Override
    public Balle creerBalle(int positionX, int positionY, int vitesse, Environnement env, Ennemi e, int rayonAction) {
        return new Missile(this.getColonne(), this.getLigne(), getVitesseAttaque(), getEnv(),e);
    }

}
