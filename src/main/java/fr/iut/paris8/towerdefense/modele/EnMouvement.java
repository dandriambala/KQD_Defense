package fr.iut.paris8.towerdefense.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class EnMouvement {
    private DoubleProperty positionXProperty;
    private DoubleProperty positionYProperty;
    private double vitesse;
    private double direction;
    private Environnement env;
    private String id;

    public EnMouvement(int x, int y, int vitesse, Environnement env, double direction, String id) {
        this.env = env;
        this.positionXProperty = new SimpleDoubleProperty(x);
        this.positionYProperty = new SimpleDoubleProperty(y);
        this.vitesse = vitesse;
        this.direction = direction;
        this.id = id;
    }
    public EnMouvement(int x, int y, int vitesse, Environnement env) {
        this.env = env;
        this.positionXProperty = new SimpleDoubleProperty(x);
        this.positionYProperty = new SimpleDoubleProperty(y);
        this.vitesse = vitesse;
    }

    public double getPositionXProperty() {
        return positionXProperty.getValue();
    }

    public DoubleProperty positionXProperty() {
        return positionXProperty;
    }

    public double getPositionYProperty() {
        return positionYProperty.getValue();
    }

    public DoubleProperty positionYProperty() {
        return positionYProperty;
    }

    public double getVitesse() {
        return vitesse;
    }

    public double getDirection() {
        return direction;
    }

    public Environnement getEnv() {
        return env;
    }

    public void setPositionXProperty(double positionXProperty) {
        this.positionXProperty.set(positionXProperty);
    }

    public void setPositionYProperty(double positionYProperty) {
        this.positionYProperty.set(positionYProperty);
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void setVitesse(double vitesse) {
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