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
    private ObservableList<Ennemi> ennemis;
    private IntegerProperty nbToursProperty;
    private TerrainModele t;
    private BFS bfs;

    public Environnement(TerrainModele t) {
        super();
        this.nbToursProperty = new SimpleIntegerProperty();

        this.nbToursProperty.setValue(0);
        this.ennemis = FXCollections.observableArrayList();
        this.defenses = FXCollections.observableArrayList();
        this.t = t;

        this.bfs = new BFS(new Grille(t.getWidth()/16,t.getHeight()/16),new Case(59,10));
        vague = new GenerateurVague();
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


    public void unTour() {

        nbToursProperty.setValue(nbToursProperty.getValue() + 1);

        for (Defense d : getDefense()) {
            d.attaquer();
        }

        vague.vaguePourChaqueTour(this);

        this.ennemisPourChaqueTour();
    }


    public void ennemisPourChaqueTour(){
        for (int i = ennemis.size() - 1; i >= 0; i--) {
            if (ennemis.get(i).estVivant() && t.dansTerrain(ennemis.get(i).getY() / 16, ennemis.get(i).getX() / 16)) {
                ennemis.get(i).agir();
            } else {
                ennemis.remove(ennemis.get(i));
            }
        }
    }

    public BFS getBfs () {
        return bfs;
    }

    public TerrainModele getT () {
        return t;
    }

}

