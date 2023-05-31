package fr.iut.paris8.towerdefense.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Environnement {

    private static int pourcentageDifficulté = 6;

    private int nbVague;
    private int nbToursDernièreVagueTerminée = -1; // -1 signifie vague en cours

    private ObservableList<Defense> defenses;
    private ObservableList<Ennemi> ennemis;
    private IntegerProperty nbToursProperty;
    private TerrainModele t;

    public Environnement(TerrainModele t) {
        super();
        this.nbToursProperty = new SimpleIntegerProperty();

        this.nbToursProperty.setValue(0);
        this.ennemis = FXCollections.observableArrayList();
        this.defenses = FXCollections.observableArrayList();
        this.t = t;
    }

    public final IntegerProperty nbToursProperty() {
        return this.nbToursProperty;
    }

    public final int getNbToursProperty() {
        return this.nbToursProperty.getValue();
    }

    public final void setNbToursProperty(int n) {
        this.nbToursProperty.setValue(n);
    }

    public ObservableList<Defense> getDefense() {
        return defenses;
    }

    public TerrainModele getTerrainModele(){
        return this.t;
    }

    public void ajouterDefense(Defense d) {
        defenses.add(d);
    }

    public ObservableList<Ennemi> getEnnemis() {
        return ennemis;
    }

    public void ajouterEnnemi(Ennemi a) {
        ennemis.add(a);
    }


    public void unTour(){

        nbToursProperty.setValue(nbToursProperty.getValue() + 1);

        for (Defense d : getDefense()) {
            d.attaquer();
        }

        if (nbToursDernièreVagueTerminée == -1 && ennemis.isEmpty() && !finPartie()){
            nbToursDernièreVagueTerminée = getNbToursProperty();
        }

        if (nbToursDernièreVagueTerminée != -1 && getNbToursProperty()-nbToursDernièreVagueTerminée >= 60) {
            genererVague();
            nbToursDernièreVagueTerminée = -1;
        }

        for (int i = ennemis.size() - 1; i >= 0; i--) {
            if (ennemis.get(i).estVivant()) {
                ennemis.get(i).agir();
            } else {
                ennemis.remove(ennemis.get(i));
            }
        }

    }

    public static boolean reussitProba(double pourcent){
        double x = Math.random();
        double pp = pourcent/100;
        return (x<=pp);
    }

    //gerer par des pourcentage, plus pourcentageDifficulté sera grand, plus on a de chance d'avoir des ennemis fort
    //todo ajouter un limiteur lorsqu'on aura des ennemis plus forts : boss
    public void genererVague(){
        nbVague++;
        for (int i=0; i<nbVague+5; i++){

            if (!reussitProba(pourcentageDifficulté)) {
                ajouterEnnemi(new EnnemiSimple(this));
            } else {
                ajouterEnnemi(new EnnemiRapide(this));
            }
        }
        pourcentageDifficulté +=10;
    }

    public boolean finPartie(){
        return nbVague ==50;
    }




}

