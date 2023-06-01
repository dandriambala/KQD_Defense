package fr.iut.paris8.towerdefense.control;

import fr.iut.paris8.towerdefense.modele.Balle;
import fr.iut.paris8.towerdefense.modele.EnMouvement;
import fr.iut.paris8.towerdefense.modele.Ennemi;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ObservateurEnMouvement implements ListChangeListener<EnMouvement> {

    private Pane pane;

    public ObservateurEnMouvement(Pane pane) {
        this.pane = pane;
    }

    @Override
    public void onChanged(Change<? extends EnMouvement> change) {

        while (change.next()) {

            for (EnMouvement em : change.getAddedSubList()){
                if(em instanceof Balle)
                    creerSpriteBalle((Balle) em);
            }

            for (EnMouvement em: change.getRemoved()) {
                Node node = pane.lookup("#" + em.getId());
                pane.getChildren().remove(node);
            }
        }
    }
    public void creerSpriteBalle(Balle b) {
        Circle c = new Circle(1);
        c.setFill(Color.YELLOW);
        c.setTranslateX(b.getPositionXProperty());
        c.setTranslateY(b.getPositionYProperty());
        pane.getChildren().add(c);
        c.translateXProperty().bind(b.positionXProperty());
        c.translateYProperty().bind(b.positionYProperty());
        c.setId(b.getId());

    }
}