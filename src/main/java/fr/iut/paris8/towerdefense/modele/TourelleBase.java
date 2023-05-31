package fr.iut.paris8.towerdefense.modele;

public class TourelleBase extends Defense {
    private int vitesseAttaque = 1;

    public TourelleBase(Environnement env) {
        super(60, env, 2, 10);
    }

    public void attaquer() {
        Ennemi e = this.chercherDansPortee();
        if (e != null) {
            System.out.println("j'attaque");
            System.out.println(e.getPv());
            e.decrementerPv(this.getDegats());
        }
    }
}

