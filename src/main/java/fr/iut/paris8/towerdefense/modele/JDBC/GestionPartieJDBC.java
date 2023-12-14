package fr.iut.paris8.towerdefense.modele.JDBC;

import java.sql.*;

public class GestionPartieJDBC {

    public boolean enregistrerPartie( String etatPartie, int vagueActuelle, int idJoueur, int idTerrain) {
        String requete = "INSERT INTO Partie ( Etat_Partie, VagueActuelle, idJoueur, idTerrain) VALUES ( ?, ?, ?, ?)"
                + "ON DUPLICATE KEY UPDATE Etat_Partie = ?, VagueActuelle = ?, idJoueur = ?, idTerrain = ?";

        try (Connection connexion = ConnexionJDBC.getConnection();
             PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {

            preparedStatement.setString(1, etatPartie);
            preparedStatement.setInt(2, vagueActuelle);
            preparedStatement.setInt(3, idJoueur);
            preparedStatement.setInt(4, idTerrain);

            // Paramètres pour la mise à jour en cas de clé existante
            preparedStatement.setString(5, etatPartie);
            preparedStatement.setInt(6, vagueActuelle);
            preparedStatement.setInt(7, idJoueur);
            preparedStatement.setInt(8, idTerrain);

            int resultat = preparedStatement.executeUpdate();
            return resultat > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement de la partie : " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }
}
