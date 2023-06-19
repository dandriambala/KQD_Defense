module fr.iut.paris8.towerdefense {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens fr.iut.paris8.towerdefense.control to javafx.fxml;
    exports fr.iut.paris8.towerdefense;
    exports fr.iut.paris8.towerdefense.modele;
    exports fr.iut.paris8.towerdefense.modele.defenses;
    exports fr.iut.paris8.towerdefense.modele.ennemis;
    exports fr.iut.paris8.towerdefense.modele.tirTourelle;


}