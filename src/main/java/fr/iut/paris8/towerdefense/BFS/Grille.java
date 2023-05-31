package fr.iut.paris8.towerdefense.BFS;

import java.util.*;

public class Grille {
    private int w;
    private int h;
    private Map<Sommet, Set<Sommet>> listeAdj;
    private ArrayList<Sommet> obstacles;

    public Grille ( int w, int h ) {
        this.w = w;
        this.h = h;
        this.listeAdj = new HashMap();
        this.obstacles = new ArrayList<>();
        this.construit();
    }

    private void construit () {
        int i;
        int j;
        for (i = 0; i < this.w; ++i) {
            for (j = 0; j < this.h; ++j) {
                this.listeAdj.put(new Sommet(i, j), new HashSet());
            }
        }

        for (i = 0; i < this.w; ++i) {
            for (j = 0; j < this.h; ++j) {
                Sommet s = new Sommet(i, j);
                if ( this.dansGrille(i - 1, j) ) {
                    ( (Set) this.listeAdj.get(s) ).add(new Sommet(i - 1, j));
                }

                if ( this.dansGrille(i + 1, j) ) {
                    ( (Set) this.listeAdj.get(s) ).add(new Sommet(i + 1, j));
                }

                if ( this.dansGrille(i, j + 1) ) {
                    ( (Set) this.listeAdj.get(s) ).add(new Sommet(i, j + 1));
                }

                if ( this.dansGrille(i, j - 1) ) {
                    ( (Set) this.listeAdj.get(s) ).add(new Sommet(i, j - 1));
                }
            }
        }

    }

    public boolean estDeconnecte ( Sommet s ) {
        return this.obstacles.contains(s);
    }

    private boolean dansGrille ( int x, int y ) {
        return x >= 0 && x < this.w && y >= 0 && y < this.h;
    }

    public Set<Sommet> adjacents ( Sommet s ) {
        return (Set) ( !this.estDeconnecte(s) ? (Set) this.listeAdj.get(s) : new HashSet() );
    }
    public void deconnecte(Sommet s) {
        this.obstacles.add(s);
    }
}