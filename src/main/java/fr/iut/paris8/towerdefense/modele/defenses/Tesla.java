package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.defenses.Tourelle;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;
import fr.iut.paris8.towerdefense.modele.tirTourelle.Balle;
import fr.iut.paris8.towerdefense.modele.tirTourelle.Eclair;

public class Tesla extends Tourelle {

    public Tesla() {
        super(150, 5, 3, 5,5);
    }

    public Tesla(int colonne, int ligne) {
        super(150, 5, 3,5, colonne,ligne,5);
    }

    @Override
    public Balle creerBalle(int positionX, int positionY, int vitesse, Ennemi e, int rayonAction) {
        return new Eclair(this.getColonne(), this.getLigne(), getVitesseAttaque(),e);

    }

}
