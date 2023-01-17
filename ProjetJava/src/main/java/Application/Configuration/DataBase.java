package Application.Configuration;

import java.sql.*;

public class DataBase {
    private String NomBase;
    private String NomUtilisateur;
    private String MotDePasse;
    private final String DriverMySQL = "com.mysql.cj.jdbc.Driver";
    private final String UrlMySQL = "jdbc:mysql://localhost:8889/";
    private final String RequeteCreaBase = "CREATE DATABASE IF NOT EXISTS `mabd` " +
            "DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;";
    private final String RequeteUseBase = "USE mabd;";
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

    //Constructeur qui permet de charger le driver JDBC
    public DataBase(String Nom, String Utilisateur, String MDP){
        this.NomBase = Nom;
        this.NomUtilisateur = Utilisateur;
        this.MotDePasse = MDP;
        //Try-Catch permettant de charger le driver JDBC
        try {
            Class.forName(DriverMySQL);
            System.out.println("Chargement du Driver JDBC OK !");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    //Méthode permettant la connexion à la base et de préparer les futurs requêtes
    public void Connexion(){
        //Try-Catch permettant la connexion à PhpMyAdmin
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

        //Try-Catch permettant de préparer les futurs requêtes
        try {
            MonStatement=MaConnexion.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problème de préparation de requête !");
        }
    }

    //Méthode permettant de crée la base dans PhpMyAdmin
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

    //Méthode permettant de crée les tables dans la base
    public void CreationTables(){
        //Try-Catch permettant de crée la table lecteurs
        try {
            MonStatement.executeUpdate(RequeteCreaLecteur);
            System.out.println("Création de la table lecteurs OK !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problème de création table Lecteur");
        }
        //Try-Catch permettant de crée la table livres
        try {
            MonStatement.executeUpdate(RequeteCreaLivre);
            System.out.println("Création de la table livres OK !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problème de création table Livre");
        }
    }

    //Méthode permettant de fermer le statement et la connexion
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
