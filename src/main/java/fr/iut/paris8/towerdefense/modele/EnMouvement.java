package fr.iut.paris8.towerdefense.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class EnMouvement {
    private IntegerProperty positionXProperty;
    private IntegerProperty positionYProperty;
    private int vitesse;
    private Environnement env;
    private String id;

    public EnMouvement(int x, int y, int vitesse, Environnement env, String id) {
        this.env = env;
        this.positionXProperty = new SimpleIntegerProperty(x);
        this.positionYProperty = new SimpleIntegerProperty(y);
        this.vitesse = vitesse;
        this.id = id;
    }
    public EnMouvement(int x, int y, int vitesse, Environnement env) {
        this.env = env;
        this.positionXProperty = new SimpleIntegerProperty(x);
        this.positionYProperty = new SimpleIntegerProperty(y);
        this.vitesse = vitesse;
    }

    public int getX() {
        return positionXProperty.getValue();
    }

    public IntegerProperty getXProperty() {
        return positionXProperty;
    }

    public int getY() {
        return positionYProperty.getValue();
    }

    public IntegerProperty getYProperty() {
        return positionYProperty;
    }

    public int getVitesse() {
        return vitesse;
    }

    public Environnement getEnv() {
        return env;
    }

    public void setPositionXProperty(int positionXProperty) {
        this.positionXProperty.set(positionXProperty);
    }

    public void setPositionYProperty(int positionYProperty) {
        this.positionYProperty.set(positionYProperty);
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public abstract void agir();

}