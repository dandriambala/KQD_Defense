package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

import java.util.ArrayList;

public class NuageRalentisseur extends Piege {
    private static int compteurRalentisseur = 0;
    private ArrayList <Ennemi> ennemisDansZone;
    private static double ralentissement = 0.4;
    private long tempsDebut;

    public NuageRalentisseur(Environnement env) {
        super(40, env, 3, 0, 20000);
        setId("NR" + compteurRalentisseur);

        ennemisDansZone = new ArrayList<>();
        tempsDebut = System.currentTimeMillis();
        compteurRalentisseur++;
    }

    public void agir() {
        ralentir();

        long tempsActuel = System.currentTimeMillis();
        long dureeEcoulee = tempsActuel - tempsDebut;
        if (dureeEcoulee > getDureeDeVie()) {
            accélerer();
            setDureeDeVie(0);
        }
    }

    private void ralentir() {

        ArrayList<Ennemi> ennemisaRalentir = this.getEnv().chercherEnnemisDansPortee(getColonne(), getLigne(), getPortee(), 10);

        if (!ennemisaRalentir.isEmpty()) {
            for (int i = 0; i < ennemisaRalentir.size(); i++) {

                if (!ennemisDansZone.contains(ennemisaRalentir.get(i))) {
                    ennemisaRalentir.get(i).setVitesse(ennemisaRalentir.get(i).getVitesse() * ralentissement);
                    ennemisDansZone.add(ennemisaRalentir.get(i));
                }
            }
        }
        if (!ennemisDansZone.isEmpty()) {
            for (int i = ennemisDansZone.size() - 1; i >= 0; i--) {
                if (!ennemisDansZone.get(i).estVivant())
                    ennemisDansZone.remove(ennemisDansZone.get(i));
                else if (this.getColonne() + getPortee() < ennemisDansZone.get(i).getX() || this.getLigne() + getPortee() < ennemisDansZone.get(i).getY()) {
                    ennemisDansZone.get(i).setVitesse(ennemisDansZone.get(i).getVitesse() * (1/ralentissement));
                    ennemisDansZone.remove(ennemisDansZone.get(i));
                }
            }
        }
    }
    private void accélerer(){
        if (!ennemisDansZone.isEmpty()) {
            for (int i = 0; i < ennemisDansZone.size(); i++) {
                ennemisDansZone.get(i).setVitesse(ennemisDansZone.get(i).getVitesse() * (1/ralentissement));
            }
        }
    }
}