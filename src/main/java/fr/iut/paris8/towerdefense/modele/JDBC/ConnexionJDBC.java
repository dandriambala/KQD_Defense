package fr.iut.paris8.towerdefense.modele.JDBC;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class ConnexionJDBC {
        private static String url = "jdbc:mysql://database-etudiants.iut.univ-paris8.fr/dutinfopw20164";
        private static String usr = "dutinfopw20164";
        private static String mdp = "rytutyta";
        private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, usr, mdp);
                System.out.println("Vous êtes connecté");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
            throw new RuntimeException("Erreur lors de la connexion à la base de données", e);
        }
        return connection;
    }



    public int connexionJoueur(String loginAVerifier, String mdpAVerifier) throws SQLException {
        String requete = "SELECT Mot_de_passe FROM Joueur WHERE Nom = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(requete)) {
            preparedStatement.setString(1, loginAVerifier);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String mdpStocke = resultSet.getString("Mot_de_passe");
                    if (BCrypt.checkpw(mdpAVerifier, mdpStocke)) {
                        System.out.println("Connexion réussie !");
                        return trouverIDJoueur(loginAVerifier);
                    }
                }
                System.out.println("Login ou mot de passe incorrect.");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }



    public int trouverIDJoueur(String login) throws SQLException {
        Connection connexion = getConnection(); // Assurez-vous d'avoir une méthode obtenirConnexion()

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
}
