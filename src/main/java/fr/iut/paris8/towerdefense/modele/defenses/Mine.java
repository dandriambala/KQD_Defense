package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

import java.util.ArrayList;

public class Mine extends Piege {
    private static int compteurMine = 0;
    private boolean aExplosé = false;

    //La durée de vie à 1 n'a pas d'impact
    public Mine (){
        super(40,1,200);

        setId("M" + compteurMine);
        compteurMine++;
    }

    public Mine( int colonne, int ligne){
        super(25,  1, 200, 1, colonne, ligne);
    }

    public void faireEffet(){
        exploser();
    }

/**
 * La méthode est responsable de l'explosion de la mine.
 * Elle recherche les ennemis à portée de 1 et leur inflige des dégâts équivalents à la valeur de dégâts de la mine.
 * Après avoir infligé des dégâts aux ennemis, la méthode met à jour la durée de vie de la tourelle à zéro, ce qui signifie qu'elle est détruite.
 */
    private void exploser(){
       ArrayList<Ennemi> ennemis = this.getEnv().chercherEnnemisDansPortee(getColonne(),getLigne(),getPortee(), 1);

       //Explose sur le premier ennemi qu'il a dans sa portée
        if(!ennemis.isEmpty()) {
                ennemis.get(0).decrementerPv(this.getDegats());
                this.aExplosé = true;
        }
    }

    public boolean finDeVie(){
        return this.aExplosé;
    }
}
