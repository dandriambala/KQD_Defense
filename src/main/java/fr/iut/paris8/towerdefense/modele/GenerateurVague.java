package fr.iut.paris8.towerdefense.modele;

import fr.iut.paris8.towerdefense.modele.ennemis.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GenerateurVague {

    private static int pourcentageDifficulte = 6;
    private IntegerProperty nbVague;
    private int nbEnnemisCreeeDansVague;
    private int nbToursDerniereVagueTerminee; // -1 signifie que la vague précédente est terminée
    private static int limiteur = 1;
    private double augmentationPv = 1.0;

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

    //gerer par des pourcentage, plus pourcentageDifficulté sera grand, plus on a de chance d'avoir des ennemis fort
    //il y a un limiteur pour pas qu'il y ait trop de Tank créer par vague
    public void genererUnElementDeLaVague(Environnement env) {

        int compteur = 0;


        if (!reussitProba(pourcentageDifficulte)) {
            if (getNbVague() >= 5){
                env.ajouterEnnemi(new Eclaireur(env));
            }
            else

                env.ajouterEnnemi(new EnnemiBase(env));

        } else {
            if (compteur <= limiteur && getNbVague()>12) {
                env.ajouterEnnemi(new Tank(env));
                compteur++;
            } else {
                env.ajouterEnnemi(new Mastodonte(env));
            }
        }
        limiteur += 2;
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


    //A la fin d'une vague on met à jour les attribut pour la prochaine vague
    public void finDUneVague(){
        setNbVague(getNbVague() + 1);
        pourcentageDifficulte +=6;
        nbEnnemisCreeeDansVague = 0;
        nbToursDerniereVagueTerminee = -1;
    }




}
