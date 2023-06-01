package fr.iut.paris8.towerdefense.modele;

public class Ennemi extends EnMouvement{

    private static int compteurEnnemi = 0;


    private int prime; //L'argent que donnera l'ennemi à sa mort
    private int pv;

    public Ennemi(int x, int y, int vitesse, Environnement env, int prime, int pv) {
      super(x,y,vitesse,env);
        this.prime = prime;
        this.pv = pv;
        setId("E"+ compteurEnnemi);
        compteurEnnemi++;
    }
    public int getPv(){return this.pv;}

    public boolean estVivant () {
        return this.pv >= 0;
    }

    public int getPrime () {
        return prime;
    }
    public void decrementerPv(int nb){
        this.pv-=nb;
    }

    //TODO à enlever lorsque le BFS fonctionnera
    public void agir () {
        if (this.getPositionXProperty() < getEnv().getTerrainModele().getWidth()) {
            this.setPositionXProperty(getPositionXProperty() + this.getVitesse());
            //System.out.println("Ennemi :" +this.getX()+ " " +this.getY());
        }
    }

}