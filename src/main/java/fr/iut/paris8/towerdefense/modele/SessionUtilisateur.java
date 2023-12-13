package fr.iut.paris8.towerdefense.modele;

public class SessionUtilisateur {
    private static int idJoueur;

    public static int getIdJoueur() {
        return idJoueur;
    }

    public static void setIdJoueur(int id) {
        idJoueur = id;
    }
}
