package Application.Object;

import Application.Configuration.DataBase;

import java.sql.SQLException;

public class Livre {
    private String Nom;
    private String Editeur;

    private String NomBDD = "nom_livre";
    private String EditeurBDD = "editeur_livre";
    private String NomTable = "livres";

    //Création d'un livre ayant un nom et un editeur
    //Input :
    //nom -> nom du livre
    //editeur -> editeur du livre
    public Livre(String nom, String editeur) {
        Nom = nom;
        Editeur = editeur;
    }

    //Insertion du livre concerné dans la base de données grace a la requete
    //Input :
    //Base -> Base de donnée
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

    //Méthode permettant la récupération du nom du lecteurs
    //Retourne le nom du livre
    public String getNom() {
        return Nom;
    }

    //Méthode permettant la récupération du nom du lecteurs
    //Retourne l'éditeur du livre
    public String getEditeur() {
        return Editeur;
    }


    //Affichage par défaut d'un Livre
    //Retourne la chaine de caractère contenant l'affichage par défaut du livre concerné
    @Override
    public String toString() {
        return "\t{Nom='" + Nom + '\'' +
                ", Editeur='" + Editeur + '\'' +
                '}';
    }


}
