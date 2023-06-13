package fr.iut.paris8.towerdefense.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GenerateurVague {

    private static int pourcentageDifficulte = 6;
    private IntegerProperty nbVague;
    private int nbEnnemisCreeeDansVague;
    private int nbToursDerniereVagueTerminee; // -1 signifie vague en cours


    public GenerateurVague() {
        this.nbVague = new SimpleIntegerProperty(0);
        this.nbEnnemisCreeeDansVague = 0;
        this.nbToursDerniereVagueTerminee = -1;
    }

    public int getNbVague() {
        return nbVague.getValue();
    }

    public IntegerProperty nbVagueProperty() {
        return nbVague;
    }

    public void setNbVague(int nbVague) {
        this.nbVague.set(nbVague);
    }

    public void vaguePourChaqueTour(Environnement env){

        if (nbToursDerniereVagueTerminee == -1 && env.getEnnemis().isEmpty() && !finPartie()){
        nbToursDerniereVagueTerminee = env.getNbTours();
        }

        if (nbToursDerniereVagueTerminee != -1 && env.getNbTours()- nbToursDerniereVagueTerminee >= 60) {
            if (env.getNbTours() % 20 == 0) {
                genererUnElementDeLaVague(env);
                nbEnnemisCreeeDansVague++;
            }

            if (nbEnnemisCreeeDansVague == getNbVague()+5) {
               finDUneVague();
            }
        }
    }

    //gerer par des pourcentage, plus pourcentageDifficulté sera grand, plus on a de chance d'avoir des ennemis fort
    //todo ajouter un limiteur lorsqu'on aura des ennemis plus forts : boss
    public void genererUnElementDeLaVague(Environnement env){
            if (!reussitProba(pourcentageDifficulte)) {
                env.ajouterEnnemi(new EnnemiSimple(env));
            } else {
                env.ajouterEnnemi(new EnnemiRapide(env));
            }
    }

    public static boolean reussitProba(double pourcent){
        double x = Math.random();
        double pp = pourcent/100;
        return (x<=pp);
    }

    //retourne vrai si on est arrivé à la fin de toutes les vagues
    public boolean finPartie(){
        return getNbVague() ==50;
    }

    public void finDUneVague(){
        setNbVague(getNbVague() + 1);
        pourcentageDifficulte +=6;
        nbEnnemisCreeeDansVague = 0;
        nbToursDerniereVagueTerminee = -1;
    }




}
