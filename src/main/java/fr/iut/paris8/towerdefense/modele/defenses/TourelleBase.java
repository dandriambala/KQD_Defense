package fr.iut.paris8.towerdefense.modele.defenses;


import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.defenses.Tourelle;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;
import fr.iut.paris8.towerdefense.modele.tirTourelle.Balle;

public class TourelleBase extends Tourelle {

    public TourelleBase() {
        super(60, 2, 4,10,1);
    }

    public TourelleBase( int colonne, int ligne) {
        super(60, 2,4,10, colonne, ligne,1);
    }

    @Override
    public Balle creerBalle(int positionX, int positionY, int vitesse, Ennemi e, int rayonAction) {
        return new Balle(positionX, positionY, vitesse, e, rayonAction);
    }
}

