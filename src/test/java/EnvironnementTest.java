import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.TerrainModele;
import fr.iut.paris8.towerdefense.modele.defenses.*;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;
import fr.iut.paris8.towerdefense.modele.ennemis.Mastodonte;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

public class EnvironnementTest {

    private Environnement environnement;


    @BeforeClass
    public void setUp() {
        TerrainModele t = new TerrainModele();
        environnement = new Environnement(t);
    }


    //chercher ennemis dans portee
    @Test
    public void testEnnemiDansPortee(){



    }




    //chercher defense dans portee
    @Test
    public void testDefenseDansPortee(){

    }




    //faire defensePourChaqueTour avec mine/tesla/tourelle/nuage/lancemissile
    @Test
//    public void testDefensePourChaqueTour(){
//
//
//        Tesla t = new Tesla(environnement, 80, 112);
//        environnement.ajouterDefense(t);
//
//        Mastodonte mastodonte1 = new Mastodonte(environnement, 80, 90);
//        environnement.ajouterEnnemi(mastodonte1);
//
//        Assert.assertEquals(350, mastodonte1.getPv());
//        environnement.defensesPourChaqueTour();
//        //10 de degat
//        Assert.assertEquals(340, mastodonte1.getPv());
//
//        environnement.enleverDefense(t);
//
//
//        TourelleBase tB = new TourelleBase(environnement, 160, 160);
//        mastodonte1.setPositionXProperty(160);
//        mastodonte1.setPositionYProperty(170);
//        environnement.ajouterDefense(tB);
//        //2 degat
//        environnement.defensesPourChaqueTour();
//        Assert.assertEquals(338, mastodonte1.getPv());
//
//        environnement.enleverDefense(tB);
//
//
//        //200 degat
//        Mine m = new Mine(environnement, 165, 170);
//        environnement.ajouterDefense(m);
//
//        environnement.defensesPourChaqueTour();
//        Assert.assertEquals(138, mastodonte1.getPv());
//
//
//        NuageRalentisseur nuageRalentisseur = new NuageRalentisseur(environnement, 160, 144);
//        mastodonte1.setPositionXProperty(160);
//        mastodonte1.setPositionYProperty(144);
//        environnement.ajouterDefense(nuageRalentisseur);
//        Assert.assertEquals(2, mastodonte1.getVitesse());
//
//        environnement.defensesPourChaqueTour();
//
//        Assert.assertEquals(0.8, mastodonte1.getVitesse());
//
//
//        Mastodonte mastodonte2 = new Mastodonte(environnement, 170, 144);
//        environnement.ajouterEnnemi(mastodonte2);
//
//        LanceMissile lanceMissile = new LanceMissile(environnement, 160, 144);
//        environnement.ajouterDefense(lanceMissile);
//        environnement.defensesPourChaqueTour();
//
//        //10 de degat
//        Assert.assertEquals(128, mastodonte1.getPv());
//        Assert.assertEquals(340, mastodonte2.getPv());
//
//
//        environnement.enleverDefense(lanceMissile);
//
//    }


//    @Test
    public void testEnleverDefense(){



    }


//    @Test
//    public void testGetEnnemiID(){
//
//        Ennemi e = new Mastodonte(environnement);
//        environnement.ajouterEnnemi(e);
//
//        Ennemi test = environnement.getEnnemiID(e.getId());
//
//        Assert.assertEquals("E"+1,test,e);
//    }


    @Test
    public void testFinDePartie(){

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



    @Test
    public void testEnMouvementPourChaqueTour(){

    }



    @Test
    public void testMortParTourelle(){

    }


    @Test
    public void testSuppresionParPassageEnBase(){

    }


}
