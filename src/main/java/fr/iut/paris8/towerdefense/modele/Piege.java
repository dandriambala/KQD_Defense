package fr.iut.paris8.towerdefense.modele;

public abstract class Piege extends Defense{
    private String id;
    private int dureeDeVie;

    public Piege (int cout, Environnement env, int portee, int degats, int dureeDeVie){
        super(cout,env,portee,degats);
        this.dureeDeVie = dureeDeVie;
    }
    public abstract void agir();

    public boolean finDeVie(){
        return this.dureeDeVie == 0;
    }

    public String getId() {
        return id;
    }

    public int getDureeDeVie() {
        return dureeDeVie;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDureeDeVie(int dureeDeVie) {
        this.dureeDeVie = dureeDeVie;
    }
}
