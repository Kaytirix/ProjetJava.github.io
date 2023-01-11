import java.io.Console;
import java.io.IOException;

public class ProgrammTest {
    public static void main(String[] args) {

        String rep= "Oui";

        while (rep=="Oui") {

            System.out.println("Voulez-vous ajouter un livre ? (Oui/Non)");

            try {
                rep = //Saisie Clavier
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(rep);
            /*

            if (rep == "Oui") {
                System.out.println("Saisir un Livre a ajouter : ");
                try {
                    System.in.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }*/
        }
    }
}
