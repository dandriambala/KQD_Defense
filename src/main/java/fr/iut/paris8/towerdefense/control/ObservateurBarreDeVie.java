package fr.iut.paris8.towerdefense.control;

import fr.iut.paris8.towerdefense.modele.ennemis.BarreDeVie;
import fr.iut.paris8.towerdefense.vue.BarreDeVieVue;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;

public class ObservateurBarreDeVie implements ListChangeListener<BarreDeVie> {

    private Pane panneauDeJeu;
    private BarreDeVieVue barreDeVieVue;

    public ObservateurBarreDeVie(Pane PanneauJeu) {
        this.panneauDeJeu = PanneauJeu;
        barreDeVieVue = new BarreDeVieVue(PanneauJeu);
    }

    @Override
    public void onChanged(Change<? extends BarreDeVie> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for (BarreDeVie b : change.getAddedSubList()) {
                    System.out.println("add");
                    barreDeVieVue.afficherBarreVie(b);
                }
            }
        }
    }
}

