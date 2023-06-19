package fr.iut.paris8.towerdefense.control;
import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.TerrainModele;
import fr.iut.paris8.towerdefense.modele.defenses.*;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;
import fr.iut.paris8.towerdefense.modele.ennemis.EnnemiBase;
import fr.iut.paris8.towerdefense.modele.ennemis.Mastodonte;
import fr.iut.paris8.towerdefense.modele.ennemis.Tank;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;

public class EnvironnementTest {

    //chercher ennemis dans portee
    @Test
    public void testEnnemiDansPortee(){
        TerrainModele t = new TerrainModele();
         Environnement environnement = new Environnement(t);


        TourelleBase tb = new TourelleBase(environnement,32,32);
        environnement.ajouterDefense(tb);
        EnnemiBase e1 = new EnnemiBase(environnement, 0,0);
        EnnemiBase e2 = new EnnemiBase(environnement, 0,64);
        EnnemiBase e3 = new EnnemiBase(environnement, 64,0);
        EnnemiBase e4 = new EnnemiBase(environnement, 0,65);

        environnement.ajouterEnnemi(e1);
        environnement.ajouterEnnemi(e2);
        environnement.ajouterEnnemi(e3);
        environnement.ajouterEnnemi(e4);

        ArrayList<Ennemi> list = environnement.chercherEnnemisDansPortee(tb.getColonne(), tb.getLigne(), tb.getPortee(),4);

        Assert.assertTrue(list.contains(e1));
        Assert.assertTrue(list.contains(e2));
        Assert.assertTrue(list.contains(e3));
        Assert.assertFalse(list.contains(e4));
    }

    //chercher defense dans portee
    @Test
    public void testDefenseDansPortee(){
        TerrainModele t = new TerrainModele();
        Environnement environnement = new Environnement(t);


        TourelleBase tb = new TourelleBase(environnement,48,32);
        environnement.ajouterDefense(tb);
        Tank t1 = new Tank(environnement, 32,32);
        environnement.ajouterEnnemi(t1);

        Defense d = environnement.chercherDefenseDansPorteeEnnemi(t1.getX(), t1.getY(), 16);

        Assert.assertSame(d, tb);
    }


    //faire defensePourChaqueTour avec mine/tesla/tourelle/nuage/lancemissile
    @Test
    public void testDefensePourChaqueTour(){

        TerrainModele t1 = new TerrainModele();
        Environnement environnement = new Environnement(t1);
        environnement.getRessourceJeu().setArgent(100000000);

        Tesla t = new Tesla(environnement, 80, 112);
        environnement.ajouterDefense(t);

        Mastodonte mastodonte1 = new Mastodonte(environnement, 80, 90);
        environnement.ajouterEnnemi(mastodonte1);

        Assert.assertEquals( mastodonte1.getPv(), 300);
        environnement.defensesPourChaqueTour();

        //La tesla fait 3 de dégât au mastodonte
        Assert.assertEquals( mastodonte1.getPv(),297);

        environnement.enleverDefense(t);

        TourelleBase tB = new TourelleBase(environnement, 80, 80);
        mastodonte1.setPositionXProperty(80);
        mastodonte1.setPositionYProperty(81);
        environnement.ajouterDefense(tB);

        Assert.assertTrue(environnement.getDefense().contains(tB));

        //TourelleBase 4 degât
        environnement.defensesPourChaqueTour();
        Assert.assertEquals( mastodonte1.getPv(),293);

        environnement.enleverDefense(tB);


        //Mine 200 degat
        Mine m = new Mine(environnement, 80, 90);
        environnement.ajouterDefense(m);

        environnement.defensesPourChaqueTour();
        Assert.assertEquals( mastodonte1.getPv(),93);
        environnement.defensesPourChaqueTour();
        Assert.assertFalse(environnement.getDefense().contains(m));

    }


    @Test
    public void testFinDePartie(){
        TerrainModele t = new TerrainModele();
        Environnement environnement = new Environnement(t);

        environnement.getRessourceJeu().setPv(0);
        int a = environnement.finPartie();
        Assert.assertEquals(1, a);

        environnement.getRessourceJeu().setPv(1);
        environnement.getVague().setNbVague(50);
        int b = environnement.finPartie();
        Assert.assertEquals(0, b);


        environnement.getVague().setNbVague(30);
        int c = environnement.finPartie();
        Assert.assertEquals(-1, c);

    }

}
