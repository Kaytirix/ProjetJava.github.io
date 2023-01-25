package Application;

import Application.Configuration.DataBase;
import Application.Configuration.Server;
import Application.Object.Lecteur;
import Application.Object.Livre;

import java.util.ArrayList;
import java.util.function.Consumer;

public class LancementServer {
    public static void main(String[] args) {
        String[] TabMotLu;
        int compteur;

        Server LeServeur = new Server();

        //Création d'un objet DataBase nommé mabd
        DataBase mabd = new DataBase();

        //Utilisation de la méthode connexion de l'objet DataBase
        mabd.Connexion();

        //Utilisation de la méthode CreationBase de l'objet DataBase
        mabd.CreationBase();

        //Utilisation de la méthode CreationTables de l'objet DataBase
        mabd.CreationTables();

        System.out.println("En attente d'une connection...");
        LeServeur.AttenteConnection();

        //Si c'était un serveur multi-client, la lecture serait dans un thread et c'est le thread qui se terminerai au lieu du MAIN
        
        LeServeur.RecuperationFlux();

        //Livre
        TabMotLu = LeServeur.LectureFlux();

        compteur = 0;
        for (String LeMot: TabMotLu) {
            compteur++;
            if(compteur % 2 == 0){
                LeServeur.ConstructionListObjet("Livre",TabMotLu[compteur-2],TabMotLu[compteur-1]);
            }
        }

        //AJOUTER CODE POUR INSERER LES LIVRES ET LECTEUR DANS BDD
        Consumer<Livre> InsertionBDDLivre = Lelivre -> Lelivre.InsertionBDD(mabd);
        LeServeur.getListeLivreServeur().forEach(InsertionBDDLivre);

        /*
        //Lecteur
        TabMotLu = LeServeur.LectureFlux();

        compteur = 0;
        for (String LeMot: TabMotLu) {
            compteur++;
            if(compteur % 2 == 0){
                LeServeur.ConstructionListObjet("Lecteur",TabMotLu[compteur-2],TabMotLu[compteur-1]);
            }
        }



        Consumer<Lecteur> InsertionBDDLecteur = Lelecteur -> Lelecteur.InsertionBDD(mabd);
        LeServeur.getListeLecteurServeur().forEach(InsertionBDDLecteur);


         */

        System.out.println("Fermeture de la BDD");
        mabd.close();

        System.out.println("Deconnection du serveur...");
        LeServeur.Close();
    }
}
