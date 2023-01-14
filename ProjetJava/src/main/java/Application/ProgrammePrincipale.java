package Application;

import Application.Object.Lecteur;
import Application.Object.Livre;

import java.util.ArrayList;
import java.util.Scanner;

public class ProgrammePrincipale {
    public static void main(String[] args) {

        boolean AjoutLivres=true, AjoutLecteurs=true;

        //Scanner permettant de récupérér la saisie utilisateur
        Scanner Saisie = new Scanner(System.in);

        //Création de la liste de Livres
        ArrayList<Livre> ListLivres = new ArrayList();

        //Création de la liste de Lecteurs
        ArrayList<Lecteur> ListLecteurs = new ArrayList();

        //Saisie des livres
        ListLivres = SaisieUtilisateur(Saisie,"Livres",ListLivres);

        //Affichage de la liste ListLivres
        if(ListLivres.size()!=0)System.out.println("\n-> Livres ajoute :");
        ListLivres.forEach(System.out::println);

        //Saisie des lecteurs
        ListLecteurs = SaisieUtilisateur(Saisie,"Lecteurs",ListLecteurs);

        //Affichage de la liste ListLecteurs
        if(ListLecteurs.size()!=0)System.out.println("\n-> Lecteurs ajoute :");
        ListLecteurs.forEach(System.out::println);
    }


    public static ArrayList SaisieUtilisateur (Scanner Saisie, String Type, ArrayList Liste){

        //Variable contenant la reponse utilisateur
        String ReponseUtilisateur;

        do {
            System.out.println("\nVoulez-vous ajouter un "+ Type + " ? (Oui/Non)");

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
                else{
                    System.out.print("Saisir le nom du App.Object.Livre a ajouter : ");
                    String NomLivre = Saisie.nextLine();
                    System.out.print("Saisir le nom du l'editeur du App.Object.Livre a ajouter : ");
                    String EditeurLivre = Saisie.nextLine();
                    Livre NouveauLivre = new Livre(NomLivre,EditeurLivre);
                    Liste.add(NouveauLivre);
                }
            }
            else{
                if(!ReponseUtilisateur.toUpperCase().equalsIgnoreCase("NON")){
                    System.out.println("Saisie non conforme, Veillez reessayer");
                    ReponseUtilisateur="OUI";
                }
            }
        }
        while (ReponseUtilisateur.toUpperCase().equalsIgnoreCase("OUI"));

        return Liste;
    }
}
