package Application.Configuration;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
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
        String ChaineLu = null;

        //Se sont les caractères mettant fin à une lecture. Ils sont complexes car c'est pour éviter de potentielle chaîne écrite par l'utilisateur
        String ChaineTerminantLectureCaractere = "-/e";
        String ChaineTerminantLectureFlux = "-/f";
        String ChaineTemp = null;
        char CaractereLu;

        if (this.MonInputStream != null) {
            this.BufInStream = new BufferedInputStream(MonInputStream);
        }

        if (this.BufInStream != null) {
            this.DataInStream = new DataInputStream(BufInStream);

            try {
                do {
                    CaractereLu = DataInStream.readChar();

                    //Peut-être faire un if enchaînant par un boucle créant une autre chaîne qui construira une potentiel chaîne stoppant la lecture
                    //Si CaractereLu == "-" -> PEUT ETRE CHAINE METTANT FIN A LA LECTURE. FAUT REGARDER LE SUIVANT :
                        //1. AH non, ce n'est pas "/" de lu donc on les ajoutes à ChaineLu
                        //2. OOHOHO, c'est "/" de lu -> PEUT ETRE CHAINE METTANT FIN A LA LECTURE. FAUT REGARDER LE SUIVANT :
                            //1. C'EST "e" -> au total on a "-/e" -> on met fin à la lecture de caractère et on insert dans la BDD
                            //2.C'EST "f" -> au total on a "-/f" -> on met fin à la lecture du FLUX (MonInputStream.close())
                            //3. C'est autre chose baaaah je sais pas quoi faire

                    if(CaractereLu == '-'){
                        ChaineTemp += String.valueOf(CaractereLu);

                        CaractereLu = DataInStream.readChar();

                        if(CaractereLu == '/'){
                            ChaineTemp +=  String.valueOf(CaractereLu);

                            CaractereLu = DataInStream.readChar();
                            ChaineTemp+= String.valueOf(CaractereLu);

                            if(ChaineTemp ==  ChaineTerminantLectureCaractere ){
                                //Peut insertion dans BDD ou dans liste qu'on retourne
                                System.out.println("---------");
                                System.out.println(ChaineLu);
                                System.out.println("---------");
                            }else{
                                if(ChaineTemp == ChaineTerminantLectureFlux){
                                    //Peut insertion dans BDD ou dans liste qu'on retourne
                                    System.out.println("---------");
                                    System.out.println(ChaineLu);
                                    System.out.println("---------");
                                    MonInputStream.close();
                                }
                            }
                        }
                    }
                    ChaineLu += CaractereLu;

                }while( (ChaineTemp != ChaineTerminantLectureCaractere) || (ChaineTemp != ChaineTerminantLectureFlux));
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
