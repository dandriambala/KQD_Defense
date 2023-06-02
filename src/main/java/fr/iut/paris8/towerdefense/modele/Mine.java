package fr.iut.paris8.towerdefense.modele;

public class Mine extends Piege{
    private static int compteurMine = 0;

    //La durée de vie à 1 n'a pas d'impact
    public Mine (Environnement env){
        super(25,env,1,200,1);
        setId("M" + compteurMine);
        compteurMine++;
    }
    public void agir(){
        exploser();
    }
    private void exploser(){
        Ennemi e = this.getEnv().chercherDansPortee(getColonne(),getLigne(),getPortee());
        if(e != null) {
            e.decrementerPv(this.getDegats());
            setDureeDeVie(0);
        }
    }

}
