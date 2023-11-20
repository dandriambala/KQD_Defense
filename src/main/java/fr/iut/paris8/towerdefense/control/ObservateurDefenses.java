package fr.iut.paris8.towerdefense.control;

import fr.iut.paris8.towerdefense.modele.defenses.Defense;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class ObservateurDefenses implements ListChangeListener<Defense>{
    private Pane pane;

    public ObservateurDefenses(Pane pane) {
        this.pane = pane;
    }

    @Override
    public void onChanged(ListChangeListener.Change<? extends Defense> change) {

        while (change.next()) {
            for (Defense d : change.getRemoved()) {
                    Node node = pane.lookup("#" + d.getId());
                    pane.getChildren().remove(node);
                this.pane.getChildren().remove(this.pane.lookup("#" + d.getId()));
            }
        }
    }
}
