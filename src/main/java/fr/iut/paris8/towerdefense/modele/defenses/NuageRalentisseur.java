package fr.iut.paris8.towerdefense.modele.defenses;

import fr.iut.paris8.towerdefense.modele.Environnement;
import fr.iut.paris8.towerdefense.modele.ennemis.Ennemi;

import java.util.ArrayList;

public class NuageRalentisseur extends PiegeTemporaire {
    private static int compteurRalentisseur = 0;
    private ArrayList <Ennemi> ennemisDansZone;
    private static double ralentissement = 0.4;

    public NuageRalentisseur() {
        super(40, 3, 0, 7000);
        setId("NR" + compteurRalentisseur);

        ennemisDansZone = new ArrayList<>();

        compteurRalentisseur++;
    }

    public void faireEffet() {
        super.faireEffet();
        ralentir();
        accélèreEnnemiHorsZone();
        /* Si la différence entre le temps actuel le temps de création du nuage est supérieure à la durée de vie du nuage
            Alors on annule son effet et on le fait disparaître
         */
    }

    /**
     * La méthode "ralentir" est responsable de l'effet de ralentissement appliqué par le nuage.
     * Elle recherche les ennemis à portée du nuage, s'ils ne sont pas déjà ralentis, elle diminue leur vitesse en fonction du facteur de ralentissement.
     * Les ennemis ralentis sont ajoutés à la liste "ennemisDansZone" pour un suivi ultérieur.
     * Ensuite, la méthode vérifie si des ennemis dans la liste "ennemisDansZone" sont morts ou ont quitté la portée de la tourelle.
     * Dans ces cas, leur vitesse est rétablie à la normale et ils sont retirés de la liste.
     * Cette méthode permet au nuage de ralentir les ennemis dans sa portée et de maintenir cet effet jusqu'à ce que les ennemis meurent ou sortent de la portée.
     */
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
    }

    /**
     * La méthode "accélérer" est responsable de l'effet d'accélération appliqué par le nuage.
     * Elle parcourt la liste des ennemis dans la zone d'effet "ennemisDansZone" et augmente leur vitesse en inversant le facteur de ralentissement.
     * Cela permet d'accélérer les ennemis qui ont été ralentis précédemment par le nuage.
     * Cette méthode est appelée lorsque le nuage qui applique le ralentissement est supprimée, afin de rétablir la vitesse normale des ennemis ralentis.
     */

    private void accélèreEnnemiHorsZone(){
        if (!ennemisDansZone.isEmpty()) {
            for (int i = ennemisDansZone.size() - 1; i >= 0; i--) {
                if (!ennemisDansZone.get(i).estVivant())
                    ennemisDansZone.remove(ennemisDansZone.get(i));
                    // Si l'ennemi sort de la zone alors on le réaccélère
                else if (this.getColonne() + getPortee() < ennemisDansZone.get(i).getX() || this.getLigne() + getPortee() < ennemisDansZone.get(i).getY()) {
                    ennemisDansZone.get(i).setVitesse(ennemisDansZone.get(i).getVitesse() * (1/ralentissement));
                    ennemisDansZone.remove(ennemisDansZone.get(i));
                }
            }
        }
    }

    public void finEffet(){

        if (!ennemisDansZone.isEmpty()) {
            for (int i = 0; i < ennemisDansZone.size(); i++) {
                ennemisDansZone.get(i).setVitesse(ennemisDansZone.get(i).getVitesse() * (1/ralentissement));
            }
        }
    }
}