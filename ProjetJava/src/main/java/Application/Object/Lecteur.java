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

    public Lecteur(String nom, String prenom) {
        Nom = nom;
        Prenom = prenom;
    }

    public String getNom() {
        return Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public String getNomTable() {
        return NomTable;
    }

    @Override
    public String toString() {
        return "\t{Nom='" + Nom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                '}';
    }

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
}
