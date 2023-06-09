package fr.iut.paris8.towerdefense.modele;

import fr.iut.paris8.towerdefense.BFS.BFS;
import fr.iut.paris8.towerdefense.BFS.Grille;
import fr.iut.paris8.towerdefense.BFS.Case;
import fr.iut.paris8.towerdefense.modele.defenses.Defense;
import fr.iut.paris8.towerdefense.modele.defenses.Piege;
import fr.iut.paris8.towerdefense.modele.defenses.Tourelle;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;
import fr.iut.paris8.towerdefense.modele.tirTourelle.BalleTourelleBase;
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

    public Environnement ( TerrainModele t ) {
        super();
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

    public ObservableList<BalleTourelleBase> getBalles () {
        ObservableList<BalleTourelleBase> listeBalleTourelleBases = FXCollections.observableArrayList();
        for (EnMouvement em : enMouvements) {
            if ( em instanceof BalleTourelleBase) {
                listeBalleTourelleBases.add((BalleTourelleBase) em);
            }
        }
        return listeBalleTourelleBases;
    }

    public ObservableList<Piege> getPieges () {
        ObservableList<Piege> listePiege = FXCollections.observableArrayList();
        for (Defense d : defenses) {
            if ( d instanceof Piege ) {
                listePiege.add((Piege) d);
            }
        }
        return listePiege;
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

        if (!vague.finPartie() && !ressourceJeu.partiePerdu()) {
            nbToursProperty.setValue(nbToursProperty.getValue() + 1);


            for (Defense d : defenses) {
                d.agir();
            }

            vague.vaguePourChaqueTour(this);

            this.enMouvementsPourChaqueTour();

            piegesPourChaqueTour();
        }
    }

    public void enMouvementsPourChaqueTour () {

        for (int i = enMouvements.size() - 1; i >= 0; i--) {
            EnMouvement enMo = enMouvements.get(i);
            if ( ( enMo instanceof Ennemi && ( (Ennemi) enMouvements.get(i) ).estVivant() ) && t.dansTerrain(enMo.getY() / 16, enMo.getX() / 16) ) {
                enMo.agir();
            }
            else if ( enMo instanceof BalleTourelleBase && !( (BalleTourelleBase) enMouvements.get(i) ).ennemiAtteint() )
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
            if ((( x + portee) >= t.getColonne() ) &&  ( t.getLigne() == y ) ) {
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
        this.defenses.remove(d);
    }
    /* Vérifie si les pièges sont encores actifs sinon les retire*/
    public void piegesPourChaqueTour () {
        for (int i = getPieges().size() - 1; i >= 0; i--) {
            if ( getPieges().get(i).finDeVie() ) {
                defenses.remove(getPieges().get(i));
            }
        }
    }
}
