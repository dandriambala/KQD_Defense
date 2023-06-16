package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

import java.util.ArrayList;

public class Mine extends Piege {
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
       ArrayList<Ennemi> ennemis = this.getEnv().chercherEnnemisDansPortee(getColonne(),getLigne(),getPortee(), 1);

       //Explose sur le premier ennemi qu'il a dans sa portée
        if(!ennemis.isEmpty()) {
                ennemis.get(0).decrementerPv(this.getDegats());
                setDureeDeVie(0);
        }
    }

}
