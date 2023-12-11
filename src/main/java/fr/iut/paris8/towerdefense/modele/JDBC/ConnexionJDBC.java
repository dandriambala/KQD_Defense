package fr.iut.paris8.towerdefense.modele.JDBC;

import java.sql.*;

public class ConnexionJDBC {
        private static String url = "jdbc:mysql://database-etudiants.iut.univ-paris8.fr/dutinfopw20164";
        private static String usr = "dutinfopw20164";
        private static String mdp = "rytutyta";
        private static Connection connection;

        public static Connection getConnection() {
            try {
                if (connection==null) {
                    // Load the JDBC driver
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    connection = DriverManager.getConnection(url, usr, mdp);
                    System.out.println("Vous êtes connecté");
                }
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de connexion : " + e.getMessage());
                // Rethrow the exception or handle it according to your needs
                throw new RuntimeException("Erreur lors de la connexion à la base de données", e);
            }
            return connection;
        }
    public boolean connexionJoueur(String loginAVerifier, String mdpAVerifier) throws SQLException {
        String requete = "SELECT * FROM Joueur WHERE Nom = ? AND Mot_de_passe = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(requete)) {
            preparedStatement.setString(1, loginAVerifier);
            preparedStatement.setString(2, mdpAVerifier);

            // Exécuter la requête
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Les informations de connexion sont correctes
                    System.out.println("Connexion réussie !");
                    return true;
                } else {
                    // Aucune correspondance trouvée
                    System.out.println("Login ou mot de passe incorrect.");
                }
            }
        } catch (SQLException e) {
            // Gérer les exceptions liées à la connexion ou à la requête SQL
            e.printStackTrace();
        }
        return false;
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
                    // Ajustez le comportement en cas de joueur non trouvé (par exemple, renvoyer -1)
                    return -1;
                }
            }
        }
    }
}
