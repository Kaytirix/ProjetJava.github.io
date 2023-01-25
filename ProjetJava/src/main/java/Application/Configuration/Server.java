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

public class Server {
    private final int Port = 1234;
    private int NbMot;

    private ServerSocket MonServerSocket = null;
    private InputStream MonInputStream = null;
    private Socket MaSocket = null;
    private BufferedInputStream BufInStream = null;
    private DataInputStream DataInStream = null;

    private String[] TabMotLu;
    private ArrayList<String> ListMotLu = new ArrayList<>();
    private ArrayList<Livre> ListeLivreServeur = new ArrayList<>();
    private ArrayList<Lecteur> ListeLecteurServeur = new ArrayList<>();

    public Server() {
        try {
            MonServerSocket = new ServerSocket(Port);
            System.out.println("Creation du server socket : OK");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Creation du server socket impossible, le port doit être utiliser par une autre application");
        }
    }

    public void AttenteConnection(){
        //Création d'un temps de connection. Au delà de celui-ci le serveur n'accepte plus de connection
        try {
            MonServerSocket.setSoTimeout(20000);
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("Impossible de modifier le temps de connection possible pour le client");
        }
        try {
            //Fonction qui stop le programme tant qu'un client ne s'est pas connecté
            MaSocket = MonServerSocket.accept();
            System.out.println("Un client est connecte au serveur");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Le client a mis trop de temps pour se connecter");
        }
    }

    //Cette fonction récupére le flux entrant des données
    public void RecuperationFlux(){
        MonInputStream = null;

        try {
            MonInputStream = MaSocket.getInputStream();
            System.out.println("FLux entrant obtenu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Cette fonction lit les données passer à travers le flux récupérer
    public String[] LectureFlux(){
        String ChaineLu = "";
        NbMot = 0;

        //Se sont les caractères mettant fin à une lecture. Ils sont complexes car c'est pour éviter de potentielle chaîne écrite par l'utilisateur
        boolean ArretLectureFlux = false;


        if (MonInputStream != null) {
            BufInStream = new BufferedInputStream(MonInputStream);
        }

        if (BufInStream != null) {
            DataInStream = new DataInputStream(BufInStream);
        }

        if (DataInStream != null) {
            do {
                ArretLectureFlux = LectureChaine(ChaineLu, ArretLectureFlux);
            } while (!ArretLectureFlux);

            TabMotLu = new String[ListMotLu.size()];

            int compteur = 0;

            //DEBUG
            for (String LeMot : ListMotLu) {
                TabMotLu[compteur] = LeMot;
                System.out.println(LeMot);
                System.out.println(TabMotLu[compteur]);
                compteur++;
            }
        }

        return TabMotLu;
    }

    private boolean LectureChaine(String ChaineLu, boolean ArretLectureFlux){
        char CaractereLu;

        try {
            CaractereLu = DataInStream.readChar();

            if(CaractereLu == '-'){
                CaractereLu = DataInStream.readChar();

                if(CaractereLu == '&') {
                    CaractereLu = DataInStream.readChar();

                    if (CaractereLu == 'e') {
                        ListMotLu.add(ChaineLu);
                        ChaineLu = "";
                    } else {
                        if (CaractereLu == 'f') {
                            ListMotLu.add(ChaineLu);
                            ChaineLu = "";
                            ArretLectureFlux = true;
                        }
                    }
                }
            }else{
                ChaineLu += CaractereLu;
                System.out.println(ChaineLu);
            }
        } catch (IOException e) {
            e.printStackTrace();
            ArretLectureFlux = true;
        }
        return ArretLectureFlux;
    }

    public void ConstructionListObjet(String Type, String ParametreConstructeurUn, String ParametreConstructeurDeux ){
        if(Type == "Livre"){
            ListeLivreServeur.add(new Livre(ParametreConstructeurUn,ParametreConstructeurDeux));
        }

        if(Type == "Lecteurs"){
            ListeLecteurServeur.add(new Lecteur(ParametreConstructeurUn,ParametreConstructeurDeux));
        }
    }

    public ArrayList<Livre> getListeLivreServeur() {
        return ListeLivreServeur;
    }

    public ArrayList<Lecteur> getListeLecteurServeur() {
        return ListeLecteurServeur;
    }

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

    public Socket getMaSocket() {
        return MaSocket;
    }
}
