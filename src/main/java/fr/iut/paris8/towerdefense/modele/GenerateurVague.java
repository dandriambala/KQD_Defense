package fr.iut.paris8.towerdefense.modele;

public class GenerateurVague {

    private static int pourcentageDifficulte = 6;
    private int nbVague;
    private int nbEnnemisCreeeDansVague;
    private int nbToursDerniereVagueTerminee; // -1 signifie vague en cours


    public GenerateurVague() {
        this.nbVague = 0;
        this.nbEnnemisCreeeDansVague = 0;
        this.nbToursDerniereVagueTerminee = -1;
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

            if (nbEnnemisCreeeDansVague == nbVague+5) {
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

    public boolean finPartie(){
        return nbVague ==50;
    }

    public void finDUneVague(){
        pourcentageDifficulte +=6;
        nbVague++;
        nbEnnemisCreeeDansVague = 0;
        nbToursDerniereVagueTerminee = -1;
    }




}
