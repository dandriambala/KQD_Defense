package fr.iut.paris8.towerdefense.modele;

import fr.iut.paris8.towerdefense.BFS.BFS;
import fr.iut.paris8.towerdefense.BFS.Grille;
import fr.iut.paris8.towerdefense.BFS.Case;
import fr.iut.paris8.towerdefense.modele.defenses.Defense;
import fr.iut.paris8.towerdefense.modele.defenses.Piege;
import fr.iut.paris8.towerdefense.modele.defenses.Tourelle;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;
import fr.iut.paris8.towerdefense.modele.tirTourelle.Balle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Environnement {
    private GenerateurVague vague;
    private ObservableList<Defense> defenses;
    private ObservableList<EnMouvement> enMouvements;
    private IntegerProperty nbToursProperty;
    private TerrainModele t;
    private BFS bfs;
    private RessourceJeu ressourceJeu;
    private boolean partieTerminee = false;

    public Environnement ( TerrainModele t ) {
        this.nbToursProperty = new SimpleIntegerProperty();
        this.nbToursProperty.setValue(0);
        this.enMouvements = FXCollections.observableArrayList();
        this.defenses = FXCollections.observableArrayList();
        this.t = t;
        this.bfs = new BFS(new Grille(t.getWidth() / 16, t.getHeight() / 16), new Case(59, 10));
        vague = new GenerateurVague();
        ressourceJeu = new RessourceJeu();
    }
    public final IntegerProperty nbToursProperty () {
        return this.nbToursProperty;
    }

    public final int getNbTours () {
        return this.nbToursProperty.getValue();
    }

    public final void setNbToursProperty ( int n ) {
        this.nbToursProperty.setValue(n);
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


    public TerrainModele getTerrainModele () {
        return this.t;
    }

    public ObservableList<EnMouvement> getEnMouvements () {
        return enMouvements;
    }

    public void ajouterDefense ( Defense d ) {

        if ( getRessourceJeu().peutEncoreAcheter(d.getCout()) ) {
            getRessourceJeu().achatTourelle(d.getCout());
            if ( d instanceof Tourelle ) {
                int colonne = ( d.getColonne() - 8 ) / 16;
                int ligne = ( d.getLigne() - 8 ) / 16;
                if ( colonne <= 2 && colonne >= 1 && ligne <= 11 && ligne >= 9 && d instanceof Tourelle || colonne <= 59 && colonne >= 57 && ligne <= 11 && ligne >= 9 && d instanceof Tourelle ) {
                    d.setColonne(0);
                    d.setLigne(0);
                }
                else {
                    defenses.add(d);
                    bfs.getG().deconnecte(new Case(colonne+1, ligne+1));
                    bfs.testBFS();
                }
            }
            else
                defenses.add(d);
            getTerrainModele().ajouterDefenseDansModele(d.getColonne(), d.getLigne());
        }

    }
    public Ennemi getEnnemiID ( String id ) {
        for (Ennemi a : this.getEnnemis()) {
            if ( a.getId().equals(id) ) {
                return a;
            }
        }
        return null;
    }

    public void ajouterEnnemi ( Ennemi a ) {
        enMouvements.add(a);
    }

    public void unTour() {
            nbToursProperty.setValue(nbToursProperty.getValue() + 1);

            this.defensesPourChaqueTour();

            vague.vaguePourChaqueTour(this);

            this.enMouvementsPourChaqueTour();


            finPartie();
    }

    public int finPartie(){

        //si les pv sont supérieur à 0 et que les vagues on atteint 50 alors la partie est gagné et ca retourne 0
        if (!ressourceJeu.partiePerdu() && vague.finPartie()) {
            partieTerminee = true;
            return 0;
        }
        else if (ressourceJeu.partiePerdu()){
            partieTerminee = true;
            return 1;
        }
        return -1;

    }

    public boolean getPartieTerminee(){
        return partieTerminee;
    }

    public void enMouvementsPourChaqueTour () {

        for (int i = enMouvements.size() - 1; i >= 0; i--) {
            EnMouvement enMo = enMouvements.get(i);
            if ( ( enMo instanceof Ennemi && ( (Ennemi) enMouvements.get(i) ).estVivant() ) && t.dansTerrain(enMo.getY() / 16, enMo.getX() / 16) ) {
                enMo.agir();
            }
            else if ( enMo instanceof Balle && !( (Balle) enMouvements.get(i) ).ennemiAtteint() )
                enMo.agir();
            else if ( enMo instanceof Ennemi && !( (Ennemi) enMouvements.get(i) ).estVivant() ) {
                mortParTourelle(enMo.getId());
                enMouvements.remove(enMo);
            }
            else {
                suppressionParPassageEnBase(enMo.getId());
                enMouvements.remove(enMo);
            }
        }
    }

    public void defensesPourChaqueTour(){
        for (int i = defenses.size()-1; i>=0; i--) {
            defenses.get(i).agir();
            if(defenses.get(i) instanceof Piege && ((Piege) defenses.get(i)).finDeVie())
                enleverDefense(defenses.get(i));
        }
    }

    public BFS getBfs () {
        return bfs;
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

    //retourne une defense qui se situe près d'un tank
    public Defense chercherDefenseDansPorteeEnnemi(int x, int y, int portee) {

        for (Tourelle t : this.getTourelle()) {
            if ((( x + portee) >= t.getColonne() ) && (x<=t.getColonne()) && ( t.getLigne() == y ) ) {
                return t;
            }
        }
        return null;
    }

    public GenerateurVague getVague () {
        return vague;
    }

    public RessourceJeu getRessourceJeu () {
        return ressourceJeu;
    }

    public void mortParTourelle ( String id ) {
        if ( getEnnemiID(id) != null ) {
            ressourceJeu.mortDUnEnnemi(getEnnemiID(id).getPrime());
        }
    }

    public void suppressionParPassageEnBase ( String id ) {
        if ( getEnnemiID(id) != null ) {
            ressourceJeu.ennemiEntrerDansLaBase(getEnnemiID(id).getPv() / 25);
        }
    }
    public void enleverDefense (Defense d) {
        t.caseAZero(d.getColonne()/16,d.getLigne()/16);
        this.defenses.remove(d);
//        afficherTerrain(getTerrainModele());

        if(d instanceof Tourelle) {
            Case sommet = new Case(d.getColonne() / 16, d.getLigne() / 16);
            bfs.getG().reconnecte(sommet);
            this.getBfs().testBFS();
        }
    }

//    public boolean estDansEnvironnement(String s){
//        if(defenses.contains())
//    }
}
