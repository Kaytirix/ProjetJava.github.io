package Application.Configuration;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    public void Ecriture(){
        //OUVRIR UN FLUX
        //SPLIT la chaîne de caractère avec une fonction qui retourne un tableau de chaîne de caractère du mot (j'ai son nom donc je décris)
        //S'inspirer du cour :
            /*
            OutputStream MonOutputStream = null;

            try {
                if (MaSocket != null) {
                    MonOutputStream = MaSocket.getOutputStream();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            BufferedOutputStream bos = null;

            if (MonOutputStream != null) {
                bos = new BufferedOutputStream(MonOutputStream);
            }


            DataOutputStream dos = new DataOutputStream(bos);

            try {
                dos.writeInt(3);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                dos.writeDouble(3.14);
            } catch (IOException e) {
                e.printStackTrace();
            }
             */
        //En ajouter les différents caractère mettant à chaque lecture
        //Fermer le flux
        //Si nouvelle insertion de donnée, il suffit de rappeler cette fonction cliente

        //Lien utile :
        //https://docs.oracle.com/javase/10/docs/api/java/net/Socket.html#isConnected()
        //https://www.developpez.net/forums/d2027972/java/general-java/langage/separer-lettres-d-string/
        //https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/io/DataInputStream.html#readChar()
        //https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/io/StringBufferInputStream.html
        //https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/io/StringBufferOutputStream.html
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
