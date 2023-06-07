//package fr.iut.paris8.towerdefense.modele;
//
////tire une balle puis att que lautre touche pour en faire une autre
////degat de zone, si ennemis dans une zone alors degat pour tous
//
//import java.util.ArrayList;
//
//public class LanceMissile extends Defense {
//
//    private static int TEMPS = 25;
//    private static int cooldown = 0;
//    private boolean balleEnCours;
//
//    public LanceMissile(Environnement env) {
//        super(1500, env, 3, 10);
//        this.balleEnCours = false;
//    }
//
//    public LanceMissile(Environnement env, int colonne, int ligne) {
//        super(1500, env, 3, 20, colonne, ligne);
//        this.balleEnCours = false;
//    }
//
//    //cherche un ennemi est dans la portée du missile et prendre la position de l'ennemi pour voir ceux autour et decrementer les pv
//    public void sniper(){
//
//
//        ArrayList<Ennemi> ennemis = getEnv().chercherDansPortee(this.getColonne(), this.getLigne(),this.getPortee(), 1);
//
//        if (!ennemis.isEmpty() && !balleEnCours) {
//
//            ArrayList<Ennemi> degatEnChaine = getEnv().chercherDansPortee(ennemis.get(0).getX(), ennemis.get(0).getY(), 5, 5);
//
//            if (!degatEnChaine.isEmpty()){
//                creerBallesDansTourelle(degatEnChaine.get(0).getX(), degatEnChaine.get(0).getY());
//                balleEnCours = true;
//
//                for (int j = 0; j < degatEnChaine.size(); j++){
//                    degatEnChaine.get(j).decrementerPv(this.getDegats());
//                }
//
//            }
//        }
//        balleEnCours = false;
//    }
//

package fr.iut.paris8.towerdefense.modele;

import java.util.ArrayList;

public class LanceMissile extends Tourelle {

    private static int TEMPS = 10;
    private static int cooldown = 0;
    private Balle balleActuelle;

    public LanceMissile(Environnement env) {
        super(1500, env, 3, 10, 3, 1);
        this.balleActuelle = null;
    }

    public void sniper() {
        ArrayList<Ennemi> ennemis = getEnv().chercherDansPortee(this.getColonne(), this.getLigne(), this.getPortee(), getNbCible());

        if (!ennemis.isEmpty() && balleActuelle == null) {
            ArrayList<Ennemi> degatEnChaine = getEnv().chercherDansPortee(ennemis.get(0).getX(), ennemis.get(0).getY(), 32, 5);

            if (!degatEnChaine.isEmpty()) {
                balleActuelle = creerBallesDansTourelle(ennemis.get(0).getX(), ennemis.get(0).getY());

                for (int j = 0; j < degatEnChaine.size(); j++) {
                    degatEnChaine.get(j).decrementerPv(this.getDegats());
                }
            }
        }
    }

    public void attaquer() {
        if (cooldown == 0) {
            cooldown = TEMPS;
            sniper();
        }

        if (cooldown > 0) {
            cooldown--;
            if (cooldown == TEMPS - 1 && balleActuelle != null && balleActuelle.ennemiAtteint()) {
                balleActuelle = null;
            }
        }
    }
}
