package fr.iut.paris8.towerdefense.control;

import fr.iut.paris8.towerdefense.modele.Ennemi;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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

            for (Ennemi nouveau : change.getAddedSubList()){

                creerSpriteEnnemi(nouveau);
                System.out.println("nv sprite");
            }

        }
    }

    private void creerSpriteEnnemi(Ennemi ennemi) {
        Circle c = new Circle(8);
        c.setFill(Color.BLACK);
        c.setTranslateX(ennemi.getX());
        c.setTranslateY(ennemi.getY());
        pane.getChildren().add(c);
        c.translateXProperty().bind(ennemi.getXProperty());
        c.translateYProperty().bind(ennemi.getYProperty());
        c.setId(ennemi.getId());

    }

}