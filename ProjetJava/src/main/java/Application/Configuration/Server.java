package Application.Configuration;

import Application.Object.Lecteur;
import Application.Object.Livre;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    //region Attribut
    //Port d'écoute du serveur
    private final int Port = 1234;

    //Extrémité côté serveur du canal entre le client et serveur
    private ServerSocket MonServerSocket = null;

    //Flux entrant
    private InputStream MonInputStream = null;

    //La connection du client
    private Socket MaSocket = null;

    //Les données envoyés par le client est stocké
    // dans un buffer dans le d'utiliser le moins de ressource
    private BufferedInputStream BufInStream = null;

    //Type de donnée envoyé par le client
    private DataInputStream DataInStream = null;

    //Tableau contenant tous les mots lus
    private String[] TabMotLu;

    //Liste de tout les caractères du mot qui est entrain d'être lu
    private List<Character> ListCaracLu = new ArrayList<Character>();

    //Liste de tout les mots lus
    private ArrayList<String> ListMotLu = new ArrayList<>();

    //Liste de tout les livres envoyés par le client
    private ArrayList<Livre> ListeLivreServeur = new ArrayList<>();

    //Liste de tout les lecteurs envoyés par le client
    private ArrayList<Lecteur> ListeLecteurServeur = new ArrayList<>();
    //endregion


    //region Constructeur
    /*
    Constructeur de la classe
       -> Il permet au serveur de signaler sur quel port de la machine qui doit écouter
     */
    public Server() {
        try {
            MonServerSocket = new ServerSocket(Port);
            System.out.println("Creation du server socket : OK");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Creation du server socket impossible, le port doit être utiliser par une autre application");
        }
    }
    //endregion


    //region Méthode
    /*
    Initialise un timeout : C'est le temps qu'un client à pour se connecter au serveur
        S'il est dépassé, le serveur ne lis pas les données
    Puis attend la connection d'un client au serveur.
     */
    public void AttenteConnection(){
        //Création d'un temps de connection. Au delà de celui-ci le serveur n'accepte plus de connection
        try {
            MonServerSocket.setSoTimeout(20000);
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("Impossible de modifier le temps de connection possible pour le client");
        }
        try {
            MaSocket = MonServerSocket.accept();  //Fonction qui stop le programme tant qu'un client ne s'est pas connecté
            System.out.println("Un client est connecte au serveur");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Le client a mis trop de temps pour se connecter");
        }
    }


    /*
    Cette fonction récupére le flux entrant des données, c'est à dire, tout les octets transmits
     */
    public void RecuperationFlux(){
        try {
            MonInputStream = MaSocket.getInputStream();
            System.out.println("FLux entrant obtenu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    Cette fonction lit les données passer à travers le flux récupérer tant
     qu'il ne rencontre pas d'identifiant de fin de flux ou de chaîne

     RETOURNE : Un tableau de chaîne de caractère comportant tout les mots lu
     */
    public String[] LectureFlux(){
        char CaractereLu;
        int compteur = 0;
        boolean ArretLectureFlux = false; //Il permet de déterminer si l'identifiant de fin de flux est lu


        if (MonInputStream != null) {
            BufInStream = new BufferedInputStream(MonInputStream);
        }

        if (BufInStream != null) {
            DataInStream = new DataInputStream(BufInStream);
        }

        if (DataInStream != null) {
            try {
                do {
                    CaractereLu = DataInStream.readChar();
                    ArretLectureFlux = LectureChaine(CaractereLu, ArretLectureFlux);
                } while (!ArretLectureFlux);
            } catch (IOException e) {
                System.out.println("Probleme d'écriture");
            }

            //Si initialisation est faite à cette endroit, c'est pour créer le tableau SELON le nombre de mot
            //Si il est initalisé avant, sa taille ne sera pas la bonne et créa une exception "out of bound"
            TabMotLu = new String[ListMotLu.size()];

            //Transforme la liste de mot en tableau de mot dans le but de recréer plus tard l'objet qui a été envoyé
            for (String LeMot : ListMotLu) {
                if(LeMot != ""){
                    TabMotLu[compteur] = LeMot;
                }
                compteur++;
            }
            ListMotLu.clear();
        }

        return TabMotLu;
    }


    /*
    Lit le caractère envoyé par le client et reconstitu le mot

    PARAMETRE :
        CaractereLu : C'est le caractère lu par le serveur
        ArretLectureFlux : C'est le boolean d'arrêt de la lecture de flux qui est passé
        en paramètre pour le redéfinir car il n'est pas en attribut de la classe

    RETOURNE : Un boolean précisant si l'arrêt de la lecture du flux doit être réalisé
     */
    private boolean LectureChaine(char CaractereLu, boolean ArretLectureFlux){
        String MotLut = "";
        try {

            //Si le caractère est différent de "-", on l'ajoute
            if(CaractereLu != '-'){
                ListCaracLu.add(CaractereLu);

            }else{
                CaractereLu = DataInStream.readChar();

                //On vérifie si le caractère lu est "&" car il fait parti de l'identiant de fin de chaîne ou de flux
                if(CaractereLu == '&') {
                    CaractereLu = DataInStream.readChar();

                    //Si l'identifiant est "-&e", cela signifie que
                    // le mot doit être reconstruit et ajouté à la liste de mot
                    if (CaractereLu == 'e') {
                        for (char LeCaractere : ListCaracLu) {
                            MotLut += String.valueOf(LeCaractere);
                        }
                        ListMotLu.add(MotLut);
                        ListCaracLu.clear();
                    } else {
                        //Sinon l'identifiant est "-&f" et il signifie que la
                        // lecture du flux doit être arrêter tout en ajoutant le dernier mot à liste de mot
                        for (char LeCaractere : ListCaracLu) {
                            MotLut += String.valueOf(LeCaractere);
                        }
                        ListMotLu.add(MotLut);
                        ListCaracLu.clear();
                        ArretLectureFlux = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            ArretLectureFlux = true;
        }
        return ArretLectureFlux;
    }


    /*
    Cette méthode permet de reconstruire l'objet que le client envoi au
    serveur dans le but de simplifier son insertion dans la BDD

    PARAMETRE :
        Type : Le type d'objet à reconstituer
        ParametreConstructeurUn : Le premier paramètre du constructeur de l'objet
        ParametreConstructeurDeux : Le second paramètre du constructeur de l'objet
     */
    public void ConstructionListObjet(String Type, String ParametreConstructeurUn, String ParametreConstructeurDeux ){
        if(Type == "Livre"){
            ListeLivreServeur.add(new Livre(ParametreConstructeurUn,ParametreConstructeurDeux));
        }

        if(Type == "Lecteurs"){
            ListeLecteurServeur.add(new Lecteur(ParametreConstructeurUn,ParametreConstructeurDeux));
        }
    }


    /*
    Ferme toute les ressources utilisées par le serveur
     */
    public void Close(){
        try {
            MonServerSocket.close();
            MonInputStream.close();
            //Si cette ressource est null n'est pas utile de la fermer et cela éviter l'affichage d'une erreure
            if(MaSocket != null){
                MaSocket.close();
            }
            System.out.println("Deconnecter du serveur");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Impossible de se deconnecter du serveur");
        }
    }
    //endregion


    //region Getter
    public ArrayList<Livre> getListeLivreServeur() {
        return ListeLivreServeur;
    }

    public ArrayList<Lecteur> getListeLecteurServeur() {
        return ListeLecteurServeur;
    }
    //endregion
}
