package fr.iut.paris8.towerdefense.control;

import fr.iut.paris8.towerdefense.modele.EnMouvement;
import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.TerrainModele;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;
import fr.iut.paris8.towerdefense.modele.ennemis.EnnemiBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnnemiTest {
    private Ennemi ennemi;


    @Test
    public void testGetPv() {
        Environnement environnement = new Environnement(new TerrainModele()); // Créer un objet Environnement approprié
        ennemi = new EnnemiBase(environnement);
        int pv = ennemi.getPv();
        assertEquals(100, pv);
    }

    @Test
    public void testEstVivant() {
        Environnement environnement = new Environnement(new TerrainModele()); // Créer un objet Environnement approprié
        ennemi = new EnnemiBase(environnement);
        boolean estVivant = ennemi.estVivant();
        assertTrue(estVivant);
    }

    @Test
    public void testDecrementerPv() {
        Environnement environnement = new Environnement(new TerrainModele()); // Créer un objet Environnement approprié
        ennemi = new EnnemiBase(environnement);
        ennemi.decrementerPv(20);
        int pv = ennemi.getPv();
        assertEquals(80, pv);
    }

}
