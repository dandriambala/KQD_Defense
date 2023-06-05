package fr.iut.paris8.towerdefense.modele;

import fr.iut.paris8.towerdefense.control.ObservateurPiege;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class NuageRalentisseur extends Piege{
    private static int compteurRalentisseur = 0;
    private double ralentissment = 0.7;

    //La durée de vie à 1 n'a pas d'impact
    public NuageRalentisseur (Environnement env){
        super(40,env,3,0,10);
        setId("NR" + compteurRalentisseur);
        compteurRalentisseur++;
    }
    public void agir(){
        ralentir();
    }
    private void ralentir(){

        ObservableList<Ennemi> ennemisaRalentir = this.getEnv().chercherDansPortee(getColonne(),getLigne(),getPortee(), 10);
        ArrayList<Ennemi> ennemisaAccelerer = new ArrayList<>();
        if(!ennemisaRalentir.isEmpty()) {
            for (int i = 0; i < ennemisaRalentir.size(); i++) {
                ennemisaRalentir.get(i).setVitesse(ennemisaRalentir.get(i).getVitesse() * 0.7);
                if (Math.abs(this.getColonne() - ennemisaRalentir.get(i).getX()) < getPortee() && Math.abs(this.getLigne() - ennemisaRalentir.get(i).getY()) < getPortee())
                    ennemisaAccelerer.add(ennemisaRalentir.get(i));
            }
        }
//        ArrayList<Ennemi> ennemisHorsPortee = ennemisaRalentir;

    }

}
