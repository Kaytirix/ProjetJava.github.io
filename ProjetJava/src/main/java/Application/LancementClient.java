package Application;

import Application.Configuration.Client;

public class LancementClient {
    public static void main(String[] args) {

        Client LeClient = new Client("localhost",1234);
        LeClient.Deconnection();
    }
}

