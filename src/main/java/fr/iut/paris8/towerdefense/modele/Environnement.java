package fr.iut.paris8.towerdefense.modele;

import fr.iut.paris8.towerdefense.BFS.BFS;
import fr.iut.paris8.towerdefense.BFS.Grille;
import fr.iut.paris8.towerdefense.BFS.Case;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class Environnement {
    private static int pourcentageDifficulte = 6;
    private GenerateurVague vague;
    private ObservableList<Defense> defenses;
    private ObservableList<EnMouvement> enMouvements;
    private IntegerProperty nbToursProperty;
    private TerrainModele t;
    private BFS bfs;
    private RessourceJeu ressourceJeu;

    public Environnement(TerrainModele t) {
        super();
        this.nbToursProperty = new SimpleIntegerProperty();
        this.nbToursProperty.setValue(0);

        this.enMouvements = FXCollections.observableArrayList();
        this.defenses = FXCollections.observableArrayList();
        this.t = t;
        this.bfs = new BFS(new Grille(t.getWidth()/16,t.getHeight()/16),new Case(59,10));
        vague = new GenerateurVague();
        ressourceJeu = new RessourceJeu();
    }

    public final IntegerProperty nbToursProperty() {
        return this.nbToursProperty;
    }

    public final int getNbTours() {
        return this.nbToursProperty.getValue();
    }

    public final void setNbToursProperty(int n) {
        this.nbToursProperty.setValue(n);
    }

    public ObservableList<Ennemi> getEnnemis(){
            ObservableList<Ennemi> listeEnnemis = FXCollections.observableArrayList();
        for (EnMouvement em: enMouvements) {
            if (em instanceof Ennemi){
                listeEnnemis.add((Ennemi) em);
            }
        }
        return listeEnnemis;
    }
    public ObservableList<Balle> getBalles(){
        ObservableList<Balle> listeBalles = FXCollections.observableArrayList();
        for (EnMouvement em: enMouvements) {
            if (em instanceof Balle){
                listeBalles.add((Balle) em);
            }
        }
        return listeBalles;
    }

    public ObservableList<Defense> getDefense() {
        return defenses;
    }


    public TerrainModele getTerrainModele(){
        return this.t;
    }

    public ObservableList<EnMouvement> getEnMouvements() {
        return enMouvements;
    }

    public void ajouterDefense(Defense d) {
        defenses.add(d);
    }

    public Ennemi getEnnemiID(String id) {
        for (Ennemi a : this.getEnnemis()) {
            if (a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }


    public void ajouterEnnemi(Ennemi a) {
        enMouvements.add(a);
    }

    public void unTour() {

        nbToursProperty.setValue(nbToursProperty.getValue() + 1);

        for (Defense d : getDefense()) {
            d.agir();
        }

        vague.vaguePourChaqueTour(this);

        this.ennemisPourChaqueTour();
    }


    public void ennemisPourChaqueTour() {

        for (int i = enMouvements.size() - 1; i >= 0; i--) {
            EnMouvement enMo = enMouvements.get(i);
            if ((enMo instanceof Ennemi && ((Ennemi) enMouvements.get(i)).estVivant()) && t.dansTerrain(enMo.getY() / 16, enMo.getX() / 16)) {
                enMo.agir();
            } else if (enMo instanceof Balle)
                enMo.agir();
            else if (enMo instanceof Ennemi && !((Ennemi) enMouvements.get(i)).estVivant()) {
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

    // Retourne le premier ennemi qui se trouve dans la portée de la défense (nombre de pixel qui sépare la tourelle de n'ennemi)

    public Ennemi chercherDansPortee(int colonne, int ligne, int portee){

        for (Ennemi ennemi : this.getEnnemis()) {
            if (((colonne-portee) <= ennemi.getX()) && (ennemi.getX()<=(colonne + portee))
                    && ((ligne- portee) <= ennemi.getY()) && (ennemi.getY() <= ligne + portee)) {
//                System.out.println("Dans portée");
                return ennemi;
            }
        }
        return null;

    }

    public GenerateurVague getVague() {
        return vague;
    }

    public RessourceJeu getRessourceJeu() {
        return ressourceJeu;
    }


    public void mortParTourelle(String id) {
        if (getEnnemiID(id) != null) {
            ressourceJeu.mortDUnEnnemi(getEnnemiID(id).getPrime());
        }
    }

    public void suppressionParPassageEnBase(String id) {
        if (getEnnemiID(id) != null) {
            ressourceJeu.ennemiEntrerDansLaBase(getEnnemiID(id).getPv()/25);
        }
    }

}

