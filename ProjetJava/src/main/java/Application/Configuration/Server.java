package Application.Configuration;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    private int Port = 1234;

    private ServerSocket MonServerSocket = null;
    private InputStream MonInputStream = null;
    private Socket MaSocket = null;
    private BufferedInputStream bis = null;

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

    public void Close(){
        try {
            MonServerSocket.close();
            if(MaSocket != null){
                MaSocket.close();
            }
            System.out.println("Deconnecter du serveur");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Impossible de se deconnecter du serveur");
        }
    }
}
