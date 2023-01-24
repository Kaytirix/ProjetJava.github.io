package Application;

import Application.Configuration.DataBase;
import Application.Configuration.Server;

public class LancementServer {
    public static void main(String[] args) {

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
        LeServeur.Lecture();

        //AJOUTER CODE POUR INSERER LES LIVRES ET LECTEUR DANS BDD
        // //Consumer<Livre> InsertionBDDLivre = Lelivre -> Lelivre.InsertionBDD(mabd);
        //ListLivres.forEach(InsertionBDDLivre);

        //Consumer<Lecteur> InsertionBDDLecteur = Lelecteur -> Lelecteur.InsertionBDD(mabd);
        //ListLecteurs.forEach(InsertionBDDLecteur);

        System.out.println("Fermeture de la BDD");
        mabd.close();

        System.out.println("Deconnection du serveur...");
        LeServeur.Close();
    }
}
