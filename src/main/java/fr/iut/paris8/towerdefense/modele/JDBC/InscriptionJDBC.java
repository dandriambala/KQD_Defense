package fr.iut.paris8.towerdefense.modele.JDBC;

import java.sql.*;

import static fr.iut.paris8.towerdefense.modele.JDBC.ConnexionJDBC.getConnection;

public class InscriptionJDBC {

    public int inscrireJoueur(String nom, String motDePasse) {
        if (utilisateurExiste(nom)) {
            System.out.println("Un utilisateur avec ce nom existe déjà.");
            return -1;
        }

        // Valeurs par défaut pour les autres attributs
        int argentInitial = 0;
        String logoInitial = "logo.png";

        String requete = "INSERT INTO Joueur (Nom, Argent, Mot_de_passe, Logo) VALUES (?, ?, ?, ?)";
        try (Connection connexion = getConnection();
             PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {

            preparedStatement.setString(1, nom);
            preparedStatement.setInt(2, argentInitial);
            preparedStatement.setString(3, motDePasse);
            preparedStatement.setString(4, logoInitial);

            int resultat = preparedStatement.executeUpdate();
            if (resultat > 0) {
                System.out.println("Inscription réussie !");
                return trouverIDJoueur(nom);
            } else {
                System.out.println("Échec de l'inscription.");
                return -1;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'inscription : " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public int trouverIDJoueur(String login) throws SQLException {
        Connection connexion = getConnection();

        String query = "SELECT idJoueur FROM Joueur WHERE Nom = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("idJoueur");
                } else {
                    return -1;
                }
            }
        }
    }

    public boolean utilisateurExiste(String nom) {
        String requete = "SELECT COUNT(*) FROM Joueur WHERE Nom = ?";
        try (Connection connexion = getConnection();
             PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {

            preparedStatement.setString(1, nom);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de l'existence de l'utilisateur : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }}
