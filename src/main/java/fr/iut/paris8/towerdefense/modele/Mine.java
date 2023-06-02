package fr.iut.paris8.towerdefense.modele;

import java.util.ArrayList;

public class Mine extends Piege{
    private static int compteurMine = 0;

    //La durée de vie à 1 n'a pas d'impact
    public Mine (Environnement env){
        super(25,env,1,200,1);
        setId("M" + compteurMine);
        compteurMine++;
    }
    public void agir(){
        exploser();
    }
    private void exploser(){
       ArrayList<Ennemi> ennemis = this.getEnv().chercherDansPortee(getColonne(),getLigne(),getPortee(), 1);
        if(!ennemis.isEmpty()) {
            for (int i =0; i< ennemis.size(); i++) {
                ennemis.get(i).decrementerPv(this.getDegats());
                setDureeDeVie(0);
            }
        }
    }

}
