package fr.iut.paris8.towerdefense.modele.ennemis;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.defenses.Defense;

public class Tank extends Ennemi{
    public Tank (Environnement env) { super(1,100,450, env);}

    @Override
    public void agir() {
        this.setPositionXProperty(this.getX()+1);
        detruireDefenses();
    }
    public void detruireDefenses(){
        Defense d = this.getEnv().chercherDefenseDansPorteeEnnemi(this.getX(), this.getY(), 16);
        if(d != null) {
            System.out.println("defense enlevée");
            this.getEnv().enleverDefense(d);
        }
    }
}
