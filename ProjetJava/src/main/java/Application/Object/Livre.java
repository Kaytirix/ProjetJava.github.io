package Application.Object;

public class Livre {
    private String Nom;
    private String Editeur;

    public Livre(String nom, String editeur) {
        Nom = nom;
        Editeur = editeur;
    }

    public String getNom() {
        return Nom;
    }
    public String getEditeur() {
        return Editeur;
    }

    @Override
    public String toString() {
        return "\tApp.Object.Livre{" +
                "Nom='" + Nom + '\'' +
                ", Editeur='" + Editeur + '\'' +
                '}';
    }
}
