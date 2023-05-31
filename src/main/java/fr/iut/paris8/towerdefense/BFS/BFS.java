package fr.iut.paris8.towerdefense.BFS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BFS {
    private Grille g;
    private Sommet source;
    private ArrayList<Sommet> parcours;
    private Map<Sommet, Sommet> predecesseurs;

    public BFS(Grille g, Sommet source) {
        this.g = g;
        this.source = source;
        testBFS();
    }

    public void testBFS() {
        Sommet s;
        LinkedList<Sommet> fifo = new LinkedList<>();
        parcours = new ArrayList<>();
        predecesseurs = new HashMap<Sommet, Sommet>();

        predecesseurs.put(source,null);
        parcours.add(source);
        fifo.addFirst(source);

        while(!fifo.isEmpty()){
            s = fifo.pollLast();
            for( Sommet t: g.adjacents(s))
                if (!parcours.contains(t)){
                    parcours.add(t);
                    fifo.addFirst(t);
                    predecesseurs.put(t,s);
                }
        }

    }

    public ArrayList<Sommet> cheminVersSource(Sommet cible) {
        ArrayList<Sommet> chemin = new ArrayList<>();
        Sommet s = cible;

        while(s != source){
            chemin.add(s);
            s = predecesseurs.get(s);
        }

        return chemin;
    }

    public ArrayList<Sommet> getParcours () {
        return parcours;
    }

    public Sommet getSource () {
        return source;
    }

    public Grille getG () {
        return g;
    }

    //928,76 debut donc la sortie de l'ennemie
    //960,76
    //8,76 fin donc l'entrée de l'ennemie
}
