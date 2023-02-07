package Application.Object;

import Application.Configuration.DataBase;

import java.sql.SQLException;
import java.sql.Statement;

public class Lecteur {
    private String Nom;
    private String Prenom;
    private String NomBDD = "nom_lecteur";
    private String PrenomBDD = "prenom_lecteur";
    private String NomTable = "lecteurs";

    //Création d'un lecteur qui doit obligatoirement avoir un nom, prénom
    //Input :
    //nom -> nom du lecteur
    //prenom -> prenom du lecteur
    public Lecteur(String nom, String prenom) {
        Nom = nom;
        Prenom = prenom;
    }

    //Insertion du lecteur concerné dans la base de données grace a la requete
    //Input :
    //base -> base de donné
    public void InsertionBDD(DataBase base) {
        String RequeteInsertionBDD =
                "INSERT INTO " + NomTable + " ("+ NomBDD +", " + PrenomBDD +") VALUES " +
                        " (' " + Nom + " ', ' " + Prenom +" ');";
        try {
            base.getMonStatement().executeUpdate(RequeteInsertionBDD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Probleme d'insertion BDD !");
        }
    }

    //Méthode permettant la récupération du nom du lecteurs
    //Retourne le nom du lecteur
    public String getNom() {
        return Nom;
    }

    //Méthode permettant la récupération du prénom du lecteurs
    //Retourne le prénom du lecteur
    public String getPrenom() {
        return Prenom;
    }

    //Affichage par défaut d'un lecteur
    //Retourne la chaine de caractère contenant l'affichage par défaut du lecteur concerné
    @Override
    public String toString() {
        return "\t{Nom='" + Nom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                '}';
    }
}
