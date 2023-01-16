package Application.Configuration;

import java.sql.*;

public class DataBase {
    private String NomBase;
    private String NomUtilisateur;
    private String MotDePasse;
    private String DriverMySQL = "com.mysql.cj.jdbc.Driver";
    private String UrlMySQL = "jdbc:mysql://localhost:8889/";
    private String RequeteCreaBase = "CREATE DATABASE IF NOT EXISTS `mabd` " +
            "DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;";
    private String RequeteUseBase = "USE mabd;";
    private String RequeteCreaLecteur = "CREATE TABLE IF NOT EXISTS `lecteurs` " +
            "(`id_lecteur` int(11) NOT NULL AUTO_INCREMENT," +
            "`nom_lecteur` VARCHAR(255) NOT NULL," +
            "`prenom_lecteur` VARCHAR(255) NOT NULL," +
            "PRIMARY KEY (`id_lecteur`)) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;";
    private String RequeteCreaLivre = "CREATE TABLE IF NOT EXISTS `livres` (" +
            "`id_livre` int(11) NOT NULL AUTO_INCREMENT," +
            "`nom_livre` VARCHAR(255) NOT NULL," +
            "`editeur_livre` VARCHAR(255) NOT NULL," +
            "PRIMARY KEY (`id_livre`)" +
            ") ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;";

    private Statement MonStatement = null;
    private ResultSet MonResultatSet = null;
    private Connection MaConnexion = null;

    public Statement getMonStatement() {
        return MonStatement;
    }

    public DataBase(String Nom, String Utilisateur, String MDP){
        this.NomBase = Nom;
        this.NomUtilisateur = Utilisateur;
        this.MotDePasse = MDP;
        try {
            Class.forName(DriverMySQL);
            System.out.println("Chargement du Driver JDBC OK !");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void Connexion(){
        try {
            MaConnexion = DriverManager.getConnection(
                    UrlMySQL,
                    "root",
                    "root");
            System.out.println("Connexion OK !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problème de connexion !");
        }

        try {
            MonStatement=MaConnexion.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problème de préparation de requête !");
        }
    }

    public void CreationBase(){
        try {
            MonStatement.executeUpdate(RequeteCreaBase);
            MonStatement.executeUpdate(RequeteUseBase);
            System.out.println("Vérification ou création de la Base mabd OK !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problème de création de la base !");
        }
    }

    public void CreationTables(){
        try {
            MonStatement.executeUpdate(RequeteCreaLecteur);
            System.out.println("Création de la table lecteurs OK !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problème de création table Lecteur");
        }
        try {
            MonStatement.executeUpdate(RequeteCreaLivre);
            System.out.println("Création de la table livres OK !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problème de création table Livre");
        }
    }

    public void close(){
        try {
            //MonResultatSet.close();
            MonStatement.close();
            MaConnexion.close();
            System.out.println("Ressources Libérées");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
