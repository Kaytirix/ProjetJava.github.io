package Application.Object;

public class Lecteur {
    private String Nom;
    private String Prenom;

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

    @Override
    public String toString() {
        return "\tApp.Object.Lecteur{" +
                "Nom='" + Nom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                '}';
    }
}
