package Application.Configuration;

import javax.sound.midi.Soundbank;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLOutput;

public class Server {
    private int Port = 1234;

    private ServerSocket MonServerSocket = null;
    private InputStream MonInputStream = null;
    private Socket MaSocket = null;
    private BufferedInputStream BufInStream = null;
    private DataInputStream DataInStream = null;

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
    public void Lecture(){
        String ChaineLu = "";

        //Se sont les caractères mettant fin à une lecture. Ils sont complexes car c'est pour éviter de potentielle chaîne écrite par l'utilisateur
        boolean ArretLectureFlux = false;
        char CaractereLu;

        if (MonInputStream != null) {
            BufInStream = new BufferedInputStream(MonInputStream);
        }

        if (BufInStream != null) {
            DataInStream = new DataInputStream(BufInStream);

            try {

                do {
                    CaractereLu = DataInStream.readChar();

                    if(CaractereLu == '-'){
                        CaractereLu = DataInStream.readChar();

                        if(CaractereLu == '&'){

                            CaractereLu = DataInStream.readChar();
                            if(CaractereLu ==  'e' ){

                                //Peut insertion dans BDD ou dans liste qu'on retourne

                                ChaineLu = "";
                            }else{
                                if(CaractereLu ==  'f'){

                                    //Peut insertion dans BDD ou dans liste qu'on retourne

                                    ChaineLu = "";
                                    ArretLectureFlux = true;
                                }
                            }
                        }
                    }else{
                        ChaineLu += CaractereLu;
                    }
                }while(!ArretLectureFlux);
                MonInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void Close(){
        try {
            MonServerSocket.close();
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
