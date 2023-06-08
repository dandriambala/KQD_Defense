package fr.iut.paris8.towerdefense.BFS;

import java.util.*;

public class Grille {
    private int w;
    private int h;
    private Map<Case, Set<Case>> listeAdj;
    private ArrayList<Case> obstacles;

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
                this.listeAdj.put(new Case(i, j), new HashSet());
            }
        }

        for (i = 0; i < this.w; ++i) {
            for (j = 0; j < this.h; ++j) {
                Case s = new Case(i, j);

                if ( this.dansGrille(i, j - 1) ) {
                    ( (Set) this.listeAdj.get(s) ).add(new Case(i, j - 1));
                }
                if ( this.dansGrille(i, j + 1) ) {
                    ( (Set) this.listeAdj.get(s) ).add(new Case(i, j + 1));
                }
                if ( this.dansGrille(i + 1, j) ) {
                    ( (Set) this.listeAdj.get(s) ).add(new Case(i + 1, j));
                }
                if ( this.dansGrille(i - 1, j) ) {
                    ( (Set) this.listeAdj.get(s) ).add(new Case(i - 1, j));
                }


            }
        }

    }

    public boolean estDeconnecte ( Case s ) {
        return this.obstacles.contains(s);
    }

    private boolean dansGrille ( int x, int y ) {
        return x >= 0 && x < this.w && y >= 0 && y < this.h;
    }

    public Set<Case> adjacents ( Case s ) {
        return (Set) ( !this.estDeconnecte(s) ? (Set) this.listeAdj.get(s) : new HashSet() );
    }
    public void deconnecte( Case s) {
        this.obstacles.add(s);
    }

    public void reconnecte( Case s) {
        this.obstacles.remove(s);
    }
}