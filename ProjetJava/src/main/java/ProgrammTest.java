import java.util.ArrayList;
import java.util.Scanner;

public class ProgrammTest {
    public static void main(String[] args) {

        String SaisieUtilisateur;

        Scanner saisie = new Scanner(System.in);

        //Livres
        ArrayList<Livre> ListLivres = new ArrayList();

        do {
            System.out.println("Voulez-vous ajouter un livre ? (Oui/Non)");

            SaisieUtilisateur = saisie.nextLine();

            if (SaisieUtilisateur.toUpperCase().equalsIgnoreCase("OUI")) {
                System.out.print("Saisir le nom du Livre a ajouter : ");
                String NomLivre = saisie.nextLine();
                System.out.print("Saisir le nom du l'editeur du Livre a ajouter : ");
                String EditeurLivre = saisie.nextLine();
                Livre NewLivre = new Livre(NomLivre,EditeurLivre);
                ListLivres.add(NewLivre);
            }else{
                if(!SaisieUtilisateur.toUpperCase().equalsIgnoreCase("NON")){
                    System.out.println("Saisie non conforme, Veillez reessayer");
                    SaisieUtilisateur="OUI";
                }
            }
        }
        while (SaisieUtilisateur.toUpperCase().equalsIgnoreCase("OUI"));

        if(ListLivres.size()!=0)System.out.println("-> Livre ajoute : ");
        for(Livre ParcourListAjoute : ListLivres)
        {
            System.out.println("\tLivre : " + ParcourListAjoute.getNom());
            System.out.println("\tEditeur : " + ParcourListAjoute.getEditeur() + "\n");
        }


        //Lecteurs
        ArrayList<Lecteur> ListLecteurs = new ArrayList();

        do {
            System.out.println("Voulez-vous ajouter un lecteur ? (Oui/Non)");

            SaisieUtilisateur = saisie.nextLine();

            if (SaisieUtilisateur.toUpperCase().equalsIgnoreCase("OUI")) {
                System.out.print("Saisir le prenom du lecteur a ajouter : ");
                String PrenomLecteur = saisie.nextLine();
                System.out.print("Saisir le nom du lecteur a ajouter : ");
                String NomLecteur = saisie.nextLine();
                Lecteur NewLecteur = new Lecteur(NomLecteur,PrenomLecteur);
                ListLecteurs.add(NewLecteur);
            }
            else{
                if(!SaisieUtilisateur.toUpperCase().equalsIgnoreCase("NON")){
                    System.out.println("Saisie non conforme, Veillez reessayer");
                    SaisieUtilisateur="OUI";
                }
            }
        }
        while (SaisieUtilisateur.toUpperCase().equalsIgnoreCase("OUI"));

        //Affichage de la liste ListLecteurs
        if(ListLecteurs.size()!=0)System.out.println("-> Lecteur ajoute :");
        for(Lecteur ParcourLecteurAjoute : ListLecteurs)
        {
            System.out.println("\tNom lecteur : " + ParcourLecteurAjoute.getNom());
            System.out.println("\tPrenom lecteur : " + ParcourLecteurAjoute.getPrenom() + "\n");
        }
    }
}
