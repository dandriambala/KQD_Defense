package fr.iut.paris8.towerdefense.modele.fabrique;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.defenses.*;

public class FabDefense {

   public Defense ajouterDefense(int type, int x,int y ){

       Defense d;

       switch (type) {
           case 1:
               d = new TourelleBase(x, y);
               break;
           case 2:
               d = new Tesla(x, y);
               break;
           case 3:
               d = new NuageRalentisseur(x, y);
               break;
           case 4:
               d = new LanceMissile(x, y);
               break;
           default:
               d = new Mine(x, y);
               break;
       }
       return d;
   }

}
