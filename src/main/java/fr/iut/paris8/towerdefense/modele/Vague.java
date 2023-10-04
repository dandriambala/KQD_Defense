package fr.iut.paris8.towerdefense.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Vague {

    private IntegerProperty nbVague;
    private int nbEnnemisCreeeDansVague;
    private int nbToursDerniereVagueTerminee; // -1 signifie que la vague précédente est terminée

    public Vague() {
        this.nbVague = new SimpleIntegerProperty(0);
        this.nbEnnemisCreeeDansVague = 0;
        this.nbToursDerniereVagueTerminee = -1;
    }

    public abstract void faireApparaitreEnnemi(Environnement env);

    public int getNbVague() {
        return nbVague.getValue();
    }

    public IntegerProperty nbVagueProperty() {
        return nbVague;
    }

    public void setNbVague(int nbVague) {
        this.nbVague.set(nbVague);
    }

    public void genererUnElementDeLaVague(Environnement env) {
        faireApparaitreEnnemi(env);
    }

    /**
     * La méthode "vaguePourChaqueTour" est appelée à chaque tour du jeu pour gérer la génération des vagues d'ennemis.
     * Elle vérifie d'abord si la dernière vague est terminée et si aucune vague n'est en cours.
     * Ensuite, elle vérifie si suffisamment de tours se sont écoulés depuis la fin de la dernière vague pour commencer une nouvelle vague.
     * À chaque multiple de 20 tours, elle génère un nouvel élément de la vague en appelant la méthode "genererUnElementDeLaVague".
     * Elle compte également le nombre d'ennemis créés dans la vague en cours.
     * Lorsque le nombre d'ennemis créés atteint un certain seuil (la taille de la vague actuelle + 5), la méthode appelle "finDUneVague" pour marquer la fin de la vague en cours.
     */

    public void vaguePourChaqueTour(Environnement env){
        if (nbToursDerniereVagueTerminee == -1 && env.getEnnemis().isEmpty() && !finPartie()){
        nbToursDerniereVagueTerminee = env.getNbTours();
        }
        if (nbToursDerniereVagueTerminee != -1) {
            if (env.getNbTours() % 20 == 0) {
                genererUnElementDeLaVague(env);
                nbEnnemisCreeeDansVague++;
            }
            if (nbEnnemisCreeeDansVague == getNbVague()+5) {
               finDUneVague();
            }
        }
    }


    //retourne vrai si on est arrivé à la fin de toutes les vagues
    public boolean finPartie(){
        return getNbVague() ==50;
    }

    //A la fin d'une vague on met à jour les attribut pour la prochaine vague
    public void finDUneVague(){
        setNbVague(getNbVague() + 1);
        nbEnnemisCreeeDansVague = 0;
        nbToursDerniereVagueTerminee = -1;
    }
}
