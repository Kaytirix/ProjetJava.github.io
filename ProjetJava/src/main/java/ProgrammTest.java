import java.util.ArrayList;
import java.util.Scanner;

public class ProgrammTest {
    public static void main(String[] args) {

        String SaisieUtilisateur;

        Scanner saisie = new Scanner(System.in);

        //Livres
        ArrayList<String> ListLivres = new ArrayList();

        do {
            System.out.println("Voulez-vous ajouter un livre ? (Oui/Non)");

            SaisieUtilisateur = saisie.nextLine();

            if (SaisieUtilisateur.toUpperCase().equalsIgnoreCase("OUI")) {
                System.out.print("Saisir un Livre a ajouter : ");
                String Livre = saisie.nextLine();
                ListLivres.add(Livre);
            }else{
                if(!SaisieUtilisateur.toUpperCase().equalsIgnoreCase("NON")){
                    System.out.println("Saisie non conforme, Veillez reessayer");
                    SaisieUtilisateur="OUI";
                }
            }
        }
        while (SaisieUtilisateur.toUpperCase().equalsIgnoreCase("OUI"));

        if(ListLivres.size()!=0)System.out.println("Livre ajoute");
        for(String ParcourListAjoute : ListLivres)
        {
            System.out.println(ParcourListAjoute);
        }


        //Lecteurs
        ArrayList<String> ListLecteurs = new ArrayList();

        do {
            System.out.println("Voulez-vous ajouter un lecteur ? (Oui/Non)");

            SaisieUtilisateur = saisie.nextLine();

            if (SaisieUtilisateur.toUpperCase().equalsIgnoreCase("OUI")) {
                System.out.print("Saisir un Lecteur a ajouter : ");
                String Lecteur = saisie.nextLine();
                ListLecteurs.add(Lecteur);
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
        if(ListLecteurs.size()!=0)System.out.println("Lecteur ajoute");
        for(String ParcourLecteurAjoute : ListLecteurs)
        {
            System.out.println(ParcourLecteurAjoute);
        }
    }
}
