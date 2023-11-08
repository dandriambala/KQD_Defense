package fr.iut.paris8.towerdefense.modele;

import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;
import fr.iut.paris8.towerdefense.modele.fabrique.FabriqueEnnemi;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireDeVague {

    private IntegerProperty nbVague;
    private int nbToursDerniereVagueTerminee; // -1 signifie que la vague précédente est terminée
    private List<String[]> vaguesEnnemiDansFichier = new ArrayList<>();
    private int ligneDeVague = 0;
    private int indiceEnnemiLigne = 0;
    private FabriqueEnnemi fab;

    public GestionnaireDeVague() {
        this.nbVague = new SimpleIntegerProperty(0);
        this.nbToursDerniereVagueTerminee = -1;
        lireFichier("src/main/resources/fr/iut/paris8/towerdefense/vague");
        fab = new FabriqueEnnemi();
    }

    private void lireFichier(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] ennemisDescriptions = ligne.trim().split(",");
                vaguesEnnemiDansFichier.add(ennemisDescriptions);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNbVague() {
        return nbVague.getValue();
    }

    public IntegerProperty nbVagueProperty() {
        return nbVague;
    }

    public void setNbVague(int nbVague) {
        this.nbVague.set(nbVague);
    }

    public Ennemi vaguePourChaqueTour(Environnement env){

        Ennemi e=null;
        if (nbToursDerniereVagueTerminee == -1 && !aFiniVague() && env.getEnnemis().isEmpty()){
        nbToursDerniereVagueTerminee = env.getNbTours();
        }

        if (nbToursDerniereVagueTerminee != -1) {
            if (env.getNbTours() % 20 == 0) {
                if (ligneDeVague < vaguesEnnemiDansFichier.size()) {
                    if (indiceEnnemiLigne < vaguesEnnemiDansFichier.get(ligneDeVague).length) {
                        String typeEnnemi = vaguesEnnemiDansFichier.get(ligneDeVague)[indiceEnnemiLigne];
                        e = fab.creationEnnemi(typeEnnemi.trim());
                        indiceEnnemiLigne++;
                    } else {
                        finDUneVague();
                    }
                }
            }
        }
        return e;
    }


    //retourne vrai si on est arrivé à la fin de toutes les vagues
    public boolean aFiniVague(){
        return ligneDeVague == vaguesEnnemiDansFichier.size();
    }

    //A la fin d'une vague on met à jour les attribut pour la prochaine vague
    public void finDUneVague(){
        setNbVague(getNbVague() + 1);
        nbToursDerniereVagueTerminee = -1;
        ligneDeVague++;
        indiceEnnemiLigne = 0;  // Réinitialisation de l'index des ennemis pour la nouvelle vague
    }
}
