package Application;

import Application.Configuration.Server;

public class LancementServer {
    public static void main(String[] args) {

        Server LeServeur = new Server();
        System.out.println("En attente d'une connection...");
        LeServeur.AttenteConnection();
        LeServeur.Close();
    }
}
