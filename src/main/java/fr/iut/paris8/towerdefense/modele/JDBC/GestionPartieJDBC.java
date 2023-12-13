package fr.iut.paris8.towerdefense.modele.JDBC;

import java.sql.*;

public class GestionPartieJDBC {

    public boolean enregistrerPartie(int idPartie, String etatPartie, int vagueActuelle, int idJoueur, int idTerrain) {
        String requete = "INSERT INTO Partie (Id_Partie, Etat_Partie, VagueActuelle, idJoueur, idTerrain) VALUES (?, ?, ?, ?, ?)"
                + "ON DUPLICATE KEY UPDATE Etat_Partie = ?, VagueActuelle = ?, idJoueur = ?, idTerrain = ?";

        try (Connection connexion = ConnexionJDBC.getConnection();
             PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {

            preparedStatement.setInt(1, idPartie);
            preparedStatement.setString(2, etatPartie);
            preparedStatement.setInt(3, vagueActuelle);
            preparedStatement.setInt(4, idJoueur);
            preparedStatement.setInt(5, idTerrain);

            // Paramètres pour la mise à jour en cas de clé existante
            preparedStatement.setString(6, etatPartie);
            preparedStatement.setInt(7, vagueActuelle);
            preparedStatement.setInt(8, idJoueur);
            preparedStatement.setInt(9, idTerrain);

            int resultat = preparedStatement.executeUpdate();
            return resultat > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement de la partie : " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }
}
