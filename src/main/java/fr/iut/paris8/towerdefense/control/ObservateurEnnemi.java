package fr.iut.paris8.towerdefense.control;

import fr.iut.paris8.towerdefense.modele.Ennemi;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class ObservateurEnnemi implements ListChangeListener<Ennemi> {

    private Pane pane;

    public ObservateurEnnemi(Pane pane) {
        this.pane = pane;
    }

    @Override
    public void onChanged(Change<? extends Ennemi> change) {

        while (change.next()){

            for (Ennemi e : change.getRemoved()){
                Node node = pane.lookup("#" + e.getId());
                pane.getChildren().remove(node);

            }
        }
    }
}