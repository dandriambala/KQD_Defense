package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.defenses.Tourelle;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;
import fr.iut.paris8.towerdefense.modele.tirTourelle.Balle;
import fr.iut.paris8.towerdefense.modele.tirTourelle.Eclair;

public class Tesla extends Tourelle {

    public Tesla(Environnement env) {
        super(150, env, 5, 3, 5,5);
    }

    public Tesla(Environnement env, int colonne, int ligne) {
        super(150, env, 5, 1, colonne,ligne,5);
    }

    public Balle creerBallesDansTourelle(Ennemi e){
        Balle b = new Eclair(this.getColonne(), this.getLigne(), getVitesseAttaque(), getEnv(),e);
        getEnv().getEnMouvements().add(b);
        return b;
    }

}
