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

        //Création d'un client
        Client LeClient = new Client("localhost", 1234);

        //Scanner permettant de récupérér la saisie utilisateur
        Scanner Saisie = new Scanner(System.in);

        //Création de la liste de Livres
        ArrayList<Livre> ListLivres = new ArrayList();

        //Création de la liste de Lecteurs
        ArrayList<Lecteur> ListLecteurs = new ArrayList();

        //Saisie des livres
        SaisieUtilisateur(Saisie, "Livres", ListLivres);

        LeClient.OuvertureFlux();

        //Affichage de la liste ListLivres et envoi de cette liste au serveur
        if (ListLivres.size() != 0){
            System.out.println("\n-> Livres ajoute :");
            ListLivres.forEach(System.out::println);
            System.out.println("Envoi des donnees des livres au serveur");
            ParcourListeLivrePourEcriture(ListLivres, LeClient);
        }
        else {
            System.out.println("Aucun livre n'a etais saisi pour etre envoye au serveur");
            LeClient.Ecriture();
        }

        //Saisie des lecteurs
        SaisieUtilisateur(Saisie, "Lecteurs", ListLecteurs);

        //Affichage de la liste ListLecteurs et envoi de cette liste au serveur
        if (ListLecteurs.size() != 0){
            System.out.println("\n-> Lecteurs ajoute :");
            ListLecteurs.forEach(System.out::println);
            System.out.println("Envoi des donnees des lecteurs au serveur");
            ParcourListeLecteurPourEcriture(ListLecteurs, LeClient);
        }
        else {
            System.out.println("Aucun lecteur n'a etais saisi pour etre envoye au serveur");
            LeClient.Ecriture();
        }

        //Deconnection du lien entre le client et le serveur
        LeClient.Deconnection();
    }


    //Méthode permettant de la saisie Utilisateur d'un livre ou d'un lecteur
    //Input :
    //Saisie -> Saisie réaliser par l'utilisateur
    //Type -> Type d'objet si cela est un livre ou un lecteur
    //Liste -> Liste de livre ou de lecteur dans laquelle on va ajouter la saisie
    public static void SaisieUtilisateur(Scanner Saisie, String Type, ArrayList Liste) {

        //Variable contenant la reponse utilisateur
        String ReponseUtilisateur;

        do {
            System.out.println("\nVoulez-vous ajouter un " + Type + " ? (Oui/Non)");

            ReponseUtilisateur = Saisie.nextLine();

            //Vérification de la saisie et si elle est correcte ajout de cette liste dans la liste correspondant
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

    //Méthode permettant le parcour de la liste des livres pour ecrire dans ecrire dans la base de donnée
    //Input :
    //LaListe -> Liste concerné a ecrire dans la base de donné
    //LeClient -> Client connécté au serveur lui permettant d'envoyé les données au serveur
    public static void ParcourListeLivrePourEcriture(ArrayList<Livre> LaListe, Client LeClient) {
        int compteur = 0;

        for (Livre LeLivre: LaListe) {
            compteur++;

            if (compteur == LaListe.size()) {
                LeClient.Ecriture(LeLivre.getNom(), "-&e");
                LeClient.Ecriture(LeLivre.getEditeur(), "-&f");
            } else {
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

