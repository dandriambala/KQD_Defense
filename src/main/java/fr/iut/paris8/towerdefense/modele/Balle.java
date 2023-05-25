package fr.iut.paris8.towerdefense.modele;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// Classe représentant les balles tirées par les tourelles
public class Balle {
    private double x, y; // Position de la balle
    private double vitesse; // Vitesse de la balle
    private double direction; // Direction de la balle en radians

    public Balle(double x, double y, double vitesse, double direction) {
        this.x = x;
        this.y = y;
        this.vitesse = vitesse;
        this.direction = direction;
    }

    // Méthode pour mettre à jour la position de la balle à chaque itération
    public void deplacer() {
        x += vitesse * Math.cos(direction);
        y += vitesse * Math.sin(direction);
    }

    // Méthode pour dessiner la balle sur le canevas JavaFX
    public void dessiner(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.fillOval(x, y, 10, 10);
    }
}
