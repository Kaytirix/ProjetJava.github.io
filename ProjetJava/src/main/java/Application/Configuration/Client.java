package Application.Configuration;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket MaSocket = null;

    public Client(String Adresse, int Port) {
        try {
            MaSocket = new Socket(Adresse, Port);
            System.out.println("Connection au serveur effectue");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Impossible de se connecter au serveur");
        }
    }

    public void Deconnection(){
        try {
            MaSocket.close();
            System.out.println("Deconnection du serveur");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Impossible de se deconnecter du serveur");
        }
    }
}
