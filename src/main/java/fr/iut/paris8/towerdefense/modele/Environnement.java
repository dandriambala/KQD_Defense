package fr.iut.paris8.towerdefense.modele;

import fr.iut.paris8.towerdefense.BFS.BFS;
import fr.iut.paris8.towerdefense.BFS.Grille;
import fr.iut.paris8.towerdefense.BFS.Case;
import fr.iut.paris8.towerdefense.modele.defenses.Defense;
import fr.iut.paris8.towerdefense.modele.defenses.Tourelle;
import fr.iut.paris8.towerdefense.modele.ennemis.BarreDeVie;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;
import fr.iut.paris8.towerdefense.modele.fabrique.FabDefense;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Environnement {
    private static Environnement uniqueInstance = null;
    private GestionnaireDeVague gestionnaireDeVague;

    private ObservableList<Defense> defenses;
    private ObservableList<EnMouvement> enMouvements;
    private ObservableList<BarreDeVie> barreDeVies;
    private IntegerProperty nbToursProperty;
    private TerrainModele t;
    private BFS bfs;
    private RessourceJeu ressourceJeu;
    private int partieTerminee;
    private FabDefense fabDefense;

    private Environnement(TerrainModele t) {
        this.nbToursProperty = new SimpleIntegerProperty();
        this.nbToursProperty.setValue(0);
        this.enMouvements = FXCollections.observableArrayList();
        this.barreDeVies = FXCollections.observableArrayList();
        this.defenses = FXCollections.observableArrayList();
        this.t = t;
        this.bfs = new BFS(new Grille(t.getWidth() / 16, t.getHeight() / 16), new Case(59, 10));
        ressourceJeu = new RessourceJeu();
        this.partieTerminee = -1;
        gestionnaireDeVague= new GestionnaireDeVague();
        fabDefense = new FabDefense();
    }

    public static synchronized Environnement getInstance(TerrainModele terrainModele) {
        if (uniqueInstance == null) {
            uniqueInstance = new Environnement(terrainModele);
        }
        return uniqueInstance;
    }

    public final int getNbTours () {
        return this.nbToursProperty.getValue();
    }

    public ObservableList<BarreDeVie> getBarreDeVies() {
        return barreDeVies;
    }

    public ObservableList<Ennemi> getEnnemis () {
        ObservableList<Ennemi> listeEnnemis = FXCollections.observableArrayList();
        for (EnMouvement em : enMouvements) {
            if ( em instanceof Ennemi ) {
                listeEnnemis.add((Ennemi) em);
            }
        }
        return listeEnnemis;
    }

    public ObservableList<Defense> getDefense () {
        return defenses;
    }


    public ObservableList<Tourelle> getTourelle () {
        ObservableList<Tourelle> listeTourelles = FXCollections.observableArrayList();
        for (Defense d : defenses) {
            if ( d instanceof Tourelle ) {
                listeTourelles.add((Tourelle) d);
            }
        }
        return listeTourelles;
    }

    public GestionnaireDeVague getVague () {
        return gestionnaireDeVague;
    }

    public RessourceJeu getRessourceJeu () {
        return ressourceJeu;
    }

    public TerrainModele getTerrainModele () {
        return this.t;
    }

    public ObservableList<EnMouvement> getEnMouvements () {
        return enMouvements;
    }

    public BFS getBfs () {
        return bfs;
    }

    public int getPartieTerminee () {
        return partieTerminee;
    }

    /**
     * La méthode "ajouterDefense" permet d'ajouter une défense (tourelle ou autre) dans le jeu.
     * Elle vérifie d'abord si les ressources de jeu permettent l'achat de la défense en fonction de son coût.
     * Si l'achat est possible, la méthode effectue les actions suivantes :
     *   - Déduit le coût de la défense des ressources de jeu.
     *   - elle vérifie si la position de la tourelle est dans une zone restreinte spécifique.
     *       - Si la position est dans la zone restreinte, elle réinitialise les coordonnées de la défense à (0, 0).
     *       - Sinon, elle ajoute la défense à la liste des défenses et effectue une déconnexion dans le graphe BFS.
     *   - Si la défense n'est pas une tourelle, elle l'ajoute simplement à la liste des défenses.
     *   - Ajoute la défense dans le modèle du terrain.
     */
    public Defense ajouterDefense (int type, int x, int y) {

        Defense d = fabDefense.ajouterDefense(type, x, y);

        if (getRessourceJeu().peutEncoreAcheter(d.getCout()) ) {

            if ( d instanceof Tourelle ) {

                int colonne = ( d.getColonne()) / 16;
                int ligne = ( d.getLigne()) / 16;
                if ( colonne <= 2 && colonne >= 1 && ligne <= 11 && ligne >= 9 || colonne <= 59 && colonne >= 57 && ligne <= 11 && ligne >= 9  ) {

                    d.setColonne(0);
                    d.setLigne(0);
                }
                else {
                    getRessourceJeu().achatTourelle(d.getCout());
                    defenses.add(d);
                    bfs.getG().deconnecte(new Case(colonne, ligne));

                    bfs.grilleBFS();

                }
            }
            else {
                getRessourceJeu().achatTourelle(d.getCout());
                defenses.add(d);


            }
            getTerrainModele().ajouterDefenseDansModele(x,y);
        }
        return d;

    }

    public void ajouterEnnemi ( Ennemi a ) {
        enMouvements.add(a);
        ajouterBarreDeVie(a.getBarreDeVie());
    }

    public void ajouterBarreDeVie(BarreDeVie b) {
        barreDeVies.add(b);
    }


    public void unTour() {
            nbToursProperty.setValue(nbToursProperty.getValue() + 1);

            this.defensesPourChaqueTour();


            Ennemi e = gestionnaireDeVague.vaguePourChaqueTour(this);
            if (e!=null){
                ajouterEnnemi(e);
            }

            this.enMouvementsPourChaqueTour();


            partieTerminee = finPartie();

    }

    public int finPartie(){
        //si les pv sont supérieur à 0 et que les vagues on atteint 50 alors la partie est gagné et ca retourne 0
        if (!ressourceJeu.partiePerdu() && gestionnaireDeVague.aFiniVague() && getEnnemis().isEmpty()) {
            return 0;
        }
        else if (ressourceJeu.partiePerdu()){
            return 1;
        }
        return -1;
    }



    private void enMouvementsPourChaqueTour () {

        for (int i = 0; i < getEnnemis().size(); i++) {
            Ennemi a = getEnnemis().get(i);
            a.getBarreDeVie().setX(a.getX());
            a.getBarreDeVie().setY(a.getY());
            a.getBarreDeVie().setVie(a.getPv());
            a.getBarreDeVie().miseAJourVieTotale();
        }

        for (int i = enMouvements.size() - 1; i >= 0; i--) {
            EnMouvement enMo = enMouvements.get(i);

            enMo.agir();
            if (enMo.estTerminé())
                enMouvements.remove(i);
        }

    }

    private void defensesPourChaqueTour(){
        for (int i = defenses.size()-1; i>=0; i--) {
            Defense d = defenses.get(i);
            d.agir();

            if (d.getEtat() == false)
                this.enleverDefense(d);
        }

    }

    /**
     * La méthode "enleverDefense" permet de supprimer une défense du jeu.
     * Elle effectue les actions suivantes :
     *   - Met à zéro la valeur de la case correspondant à la position de la défense dans le tableau du terrain.
     *   - Supprime la défense de la liste des défenses du jeu.
     *   - Crée un objet "Case" à partir des coordonnées de la défense pour reconnecter cette case dans le graphe BFS.
     *   - Effectue une nouvelle recherche BFS pour mettre à jour les informations du graphe après la suppression de la défense.
     */
    public void enleverDefense(Defense d) {
        t.caseAZero(d.getColonne() / 16, d.getLigne() / 16);
        this.defenses.remove(d);

        Case sommet = new Case(d.getColonne() / 16, d.getLigne() / 16);
        bfs.getG().reconnecte(sommet);
        this.getBfs().grilleBFS();

    }


    //retourne une liste d'ennemis selon une limite que la tourelle aura pour toucher un ennemi en même temps
    public ArrayList<Ennemi> chercherEnnemisDansPortee(int colonne, int ligne, int portee, int limiteur ) {


    //retourne une liste d'ennemis selon une limite que la tourelle aura pour toucher plusieurs ennemis en même temps

        ArrayList<Ennemi> ennemisDansPortee = new ArrayList<>();

        for (Ennemi ennemi : this.getEnnemis()) {
            if ( ennemisDansPortee.size() < limiteur ) {
                if ( ( ( colonne - portee ) <= ennemi.getX() ) && ( ennemi.getX() <= ( colonne + portee ) )
                        && ( ( ligne - portee ) <= ennemi.getY() ) && ( ennemi.getY() <= ligne + portee ) ) {
                    ennemisDansPortee.add(ennemi);
                }
            }
        }
        return ennemisDansPortee;
    }

    //retourne les défenses qui se situe sur la route des ennemis
    public ArrayList<Tourelle> chercherTourelleDansPorteeEnnemi(int x, int y, int portee, int limiteur, Direction directionDeRecherche) {

        ArrayList<Tourelle> tourelleDansPortee = new ArrayList<>();


        for (Tourelle t : this.getTourelle()) {
            if ( tourelleDansPortee.size() < limiteur ) {
                if (defensesTrouveDansDirection(x,y,portee,directionDeRecherche, t.getLigne(), t.getColonne())) {
                    tourelleDansPortee.add(t);
                }
            }
        }
        return tourelleDansPortee;
    }


    public boolean defensesTrouveDansDirection(int xEnnemi, int yEnnemi, int porteeEnnemi, Direction direction, int ligneDef, int colonneDef) {
        switch(direction){
            case horizontal :
                return ((( xEnnemi - porteeEnnemi ) <= colonneDef) && ( colonneDef <= ( xEnnemi + porteeEnnemi )  && (ligneDef == yEnnemi)));
            case vertical :
                // TO-DO coder la recherche en verticale
                break;
            case toute :
                return (( xEnnemi - porteeEnnemi ) <= colonneDef) && ( colonneDef <= ( xEnnemi + porteeEnnemi ) )
                    && ( ( yEnnemi - porteeEnnemi ) <= ligneDef ) && ( ligneDef <= yEnnemi + porteeEnnemi );
        }
        return false;
    }

}
