package Application.Object;

import Application.Configuration.DataBase;

import java.sql.SQLException;

public class Livre {
    private String Nom;
    private String Editeur;

    private String NomBDD = "nom_livre";
    private String EditeurBDD = "editeur_livre";
    private String NomTable = "livres";

    public Livre(String nom, String editeur) {
        Nom = nom;
        Editeur = editeur;
    }

    public void InsertionBDD(DataBase base) {
        String RequeteInsertionBDD =
                "INSERT INTO " + NomTable + " ("+ NomBDD +" , " + EditeurBDD +") VALUES " +
                        " (' " + Nom + " ', ' " + Editeur +" ');";
        try {
            base.getMonStatement().executeUpdate(RequeteInsertionBDD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Probleme d'insertion BDD !");
        }
    }

    public String getNom() {
        return Nom;
    }
    public String getEditeur() {
        return Editeur;
    }
    public String getNomTable() {
        return NomTable;
    }

    @Override
    public String toString() {
        return "\t{Nom='" + Nom + '\'' +
                ", Editeur='" + Editeur + '\'' +
                '}';
    }


}
