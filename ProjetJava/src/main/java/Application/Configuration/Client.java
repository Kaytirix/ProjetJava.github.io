package Application.Configuration;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    private Socket MaSocket = null;
    private OutputStream MonOutputStream = null;
    private BufferedOutputStream MonBufOutStream = null;
    private DataOutputStream DataOutStream = null;

    public Client(String Adresse, int Port) {
        try {
            MaSocket = new Socket(Adresse, Port);
            System.out.println("Connection au serveur effectue");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Impossible de se connecter au serveur");
        }
    }

    public void OuvertureFlux(){
        try {
            if (MaSocket != null) {
                MonOutputStream = MaSocket.getOutputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Ecriture(String LaChaine, String ChaineDeFin){

        if (MonOutputStream != null) {
            MonBufOutStream = new BufferedOutputStream(MonOutputStream);
        }

        DataOutStream = new DataOutputStream(MonBufOutStream);

        try {
            LaChaine += ChaineDeFin;
            DataOutStream.writeChars(LaChaine);
            DataOutStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Deconnection(){
        try {
            //Si cette ressource est null n'est pas utile de la fermer et cela éviter l'affichage d'une erreur
            if(MaSocket != null){
                MaSocket.close();
            }
            System.out.println("Deconnection du serveur");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Impossible de se deconnecter du serveur");
        }
    }
}
