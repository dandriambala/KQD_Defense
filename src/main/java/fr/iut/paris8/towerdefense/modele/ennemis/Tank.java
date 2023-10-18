package fr.iut.paris8.towerdefense.modele.ennemis;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.TerrainModele;
import fr.iut.paris8.towerdefense.modele.defenses.Defense;

public class Tank extends Ennemi{
    public Tank () { super(1,100,450);}
    public Tank ( int x, int y) {
        super( 1,100,450);
        setPositionXProperty(x);
        setPositionYProperty(y);
    }

    @Override
    public void agir() {
        this.setPositionXProperty(this.getX()+1);
        detruireDefenses();
    }

    // enlève les défenses qui sont sur son passage
    public void detruireDefenses(){
        Defense d = this.getEnv().chercherDefenseDansPorteeEnnemi(this.getX(), this.getY(), 16);
        if(d != null) {
            this.getEnv().enleverDefense(d);
        }
    }
}
