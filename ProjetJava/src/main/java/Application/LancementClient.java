package Application;

import Application.Configuration.Client;
import Application.Object.Lecteur;
import Application.Object.Livre;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class LancementClient {
    public static void main(String[] args) {
        boolean AjoutLivres = true, AjoutLecteurs = true;

        Client LeClient = new Client("localhost", 1234);

        //Scanner permettant de récupérér la saisie utilisateur
        Scanner Saisie = new Scanner(System.in);

        //Création de la liste de Livres
        ArrayList<Livre> ListLivres = new ArrayList();

        //Création de la liste de Lecteurs
        ArrayList<Lecteur> ListLecteurs = new ArrayList();

        //Saisie des livres
        SaisieUtilisateur(Saisie, "Livres", ListLivres);

        //Saisie des lecteurs
        SaisieUtilisateur(Saisie, "Lecteurs", ListLecteurs);

        LeClient.OuvertureFlux();

        //Affichage de la liste ListLivres
        if (ListLivres.size() != 0){
            System.out.println("\n-> Livres ajoute :");
            ListLivres.forEach(System.out::println);
            ParcourListeLivrePourEcriture(ListLivres, LeClient);
        }

        //Affichage de la liste ListLecteurs
        if (ListLecteurs.size() != 0){
            System.out.println("\n-> Lecteurs ajoute :");
            ListLecteurs.forEach(System.out::println);
            ParcourListeLecteurPourEcriture(ListLecteurs, LeClient);
        }

        LeClient.Deconnection();
    }

    public static void SaisieUtilisateur(Scanner Saisie, String Type, ArrayList Liste) {

        //Variable contenant la reponse utilisateur
        String ReponseUtilisateur;

        do {
            System.out.println("\nVoulez-vous ajouter un " + Type + " ? (Oui/Non)");

            ReponseUtilisateur = Saisie.nextLine();

            if (ReponseUtilisateur.toUpperCase().equalsIgnoreCase("OUI")) {
                if (Type == "Lecteurs") {
                    System.out.print("Saisir le prenom du lecteur a ajouter : ");
                    String PrenomLecteur = Saisie.nextLine();
                    System.out.print("Saisir le nom du lecteur a ajouter : ");
                    String NomLecteur = Saisie.nextLine();
                    Lecteur NouveauLecteur = new Lecteur(NomLecteur, PrenomLecteur);
                    Liste.add(NouveauLecteur);
                }
                if (Type == "Livres") {
                    System.out.print("Saisir le nom du Livre a ajouter : ");
                    String NomLivre = Saisie.nextLine();
                    System.out.print("Saisir le nom du l'editeur du Livre a ajouter : ");
                    String EditeurLivre = Saisie.nextLine();
                    Livre NouveauLivre = new Livre(NomLivre, EditeurLivre);
                    Liste.add(NouveauLivre);
                }
            } else {
                if (!ReponseUtilisateur.toUpperCase().equalsIgnoreCase("NON")) {
                    System.out.println("Saisie non conforme, Veillez reessayer");
                    ReponseUtilisateur = "OUI";
                }
            }
        }
        while (ReponseUtilisateur.toUpperCase().equalsIgnoreCase("OUI"));
    }

    public static void ParcourListeLivrePourEcriture(ArrayList<Livre> LaListe, Client LeClient) {
        int compteur = 0;

        for (Livre LeLivre: LaListe) {
            compteur++;
            if(compteur == LaListe.size()){
                LeClient.Ecriture(LeLivre.getNom(), "-&e");
                LeClient.Ecriture(LeLivre.getEditeur(), "-&f");
            }else{
                LeClient.Ecriture(LeLivre.getNom(), "-&e");
                LeClient.Ecriture(LeLivre.getEditeur(), "-&e");
            }
        }
    }
    public static void ParcourListeLecteurPourEcriture(ArrayList<Lecteur> LaListe, Client LeClient) {
        int compteur = 0;

        for (Lecteur LeLecteur: LaListe) {
            compteur++;
            if(compteur == LaListe.size()){
                LeClient.Ecriture(LeLecteur.getNom(), "-&e");
                LeClient.Ecriture(LeLecteur.getPrenom(), "-&f");
            }else{
                LeClient.Ecriture(LeLecteur.getNom(), "-&e");
                LeClient.Ecriture(LeLecteur.getPrenom(), "-&e");
            }
        }
    }
}

