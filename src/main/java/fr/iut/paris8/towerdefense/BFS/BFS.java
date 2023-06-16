package fr.iut.paris8.towerdefense.BFS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BFS {
    private Grille g;
    private Case source;
    private ArrayList<Case> parcours;
    private Map<Case, Case> predecesseurs;

    public BFS(Grille g, Case source) {
        this.g = g;
        this.source = source;
        testBFS();
    }

    public void testBFS() {
        Case s;
        LinkedList<Case> fifo = new LinkedList<>();
        parcours = new ArrayList<>();
        predecesseurs = new HashMap<Case, Case>();

        predecesseurs.put(source,null);
        parcours.add(source);
        fifo.addFirst(source);

        while(!fifo.isEmpty()){
            s = fifo.pollLast();
            for( Case t: g.adjacents(s))
                if (!parcours.contains(t)){
                    parcours.add(t);
                    fifo.addFirst(t);
                    predecesseurs.put(t,s);
                }
        }

    }

    public ArrayList<Case> cheminVersSource( Case cible) {
        ArrayList<Case> chemin = new ArrayList<>();
        Case s = cible;

        while(s != source){
            chemin.add(s);
            s = predecesseurs.get(s);
        }

        return chemin;
    }

    public ArrayList<Case> getParcours () {
        return parcours;
    }

    public Case getSource () {
        return source;
    }

    public Grille getG () {
        return g;
    }

    //928,76 debut donc la sortie de l'ennemie
    //960,76
    //8,76 fin donc l'entrée de l'ennemie

}
