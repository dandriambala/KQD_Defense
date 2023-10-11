package fr.iut.paris8.towerdefense.modele.fabrique;

import fr.iut.paris8.towerdefense.modele.Environnement;

public abstract class FabDefense {
    private Environnement env;
    public FabDefense(Environnement env){
        this.env = env;
    }

}
