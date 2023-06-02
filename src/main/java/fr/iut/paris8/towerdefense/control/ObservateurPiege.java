package fr.iut.paris8.towerdefense.control;

import fr.iut.paris8.towerdefense.modele.*;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class ObservateurPiege implements ListChangeListener<Piege>{
    private Pane pane;

    public ObservateurPiege(Pane pane) {
        this.pane = pane;
    }

    @Override
    public void onChanged(ListChangeListener.Change<? extends Piege> change) {

        while (change.next()) {

            for (Piege p : change.getRemoved()) {
                System.out.println("AAA");
                    Node node = pane.lookup("#" + p.getId());
                    pane.getChildren().remove(node);
            }
        }
    }
}
