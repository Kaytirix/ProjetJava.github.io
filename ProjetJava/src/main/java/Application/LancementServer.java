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

        //Création du serveur
        Server LeServeur = new Server();

        //Création d'un objet DataBase nommé mabd
        DataBase mabd = new DataBase();

        //Utilisation de la méthode connexion de l'objet DataBase
        mabd.Connexion();

        //Utilisation de la méthode CreationBase de l'objet DataBase
        mabd.CreationBase();

        //Utilisation de la méthode CreationTables de l'objet DataBase
        mabd.CreationTables();

        //Serveur pret a recevoir une connexion Client est qu'il attend après celle-ci
        System.out.println("En attente d'une connection...");
        LeServeur.AttenteConnection();

        //Recupération du flux permettant la trasmission de données entre le client et le serveur
        LeServeur.RecuperationFlux();

        //Lecture des livres a ajouter dans la base de donnée
        TabMotLu = LeServeur.LectureFlux();

        compteur = 0;
        for (String LeMot: TabMotLu) {
            compteur++;
            if(compteur % 2 == 0){
                LeServeur.ConstructionListObjet("Livre",TabMotLu[compteur-2],TabMotLu[compteur-1]);
            }
        }

        //Insertion des livres lu dans la base de données
        Consumer<Livre> InsertionBDDLivre = Lelivre -> Lelivre.InsertionBDD(mabd);
        LeServeur.getListeLivreServeur().forEach(InsertionBDDLivre);
        System.out.println("Tous les livres saisie (si il y en a) ont était ajoute a la base de donnée");

        //Lecture des lecteurs a ajouter dans la base de donnée
        TabMotLu = LeServeur.LectureFlux();

        compteur = 0;
        for (String LeMot: TabMotLu) {
            compteur++;
            if(compteur % 2 == 0){
                LeServeur.ConstructionListObjet("Lecteurs",TabMotLu[compteur-2],TabMotLu[compteur-1]);
            }
        }

        //Insertion des livres lu dans la base de données
        Consumer<Lecteur> InsertionBDDLecteur = Lelecteur -> Lelecteur.InsertionBDD(mabd);
        LeServeur.getListeLecteurServeur().forEach(InsertionBDDLecteur);
        System.out.println("Tous les lecteurs saisie (si il y en a) ont était ajoute a la base de donnée");

        //Fermetture de la base de donnée
        System.out.println("Fermeture de la BDD");
        mabd.close();

        //Fermetture du serveur
        System.out.println("Deconnection du serveur...");
        LeServeur.Close();
    }
}
