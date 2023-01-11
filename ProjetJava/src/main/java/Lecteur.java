import java.util.Date;

public class Lecteur {
    private String Name;
    private String LastName;
    private int Age;
    private Date DateNaissance;
    private Date DateIntegration;

    public Lecteur(String name, String lastName, int age, Date dateNaissance, Date dateIntegration) {
        Name = name;
        LastName = lastName;
        Age = age;
        DateNaissance = dateNaissance;
        DateIntegration = dateIntegration;
    }
}
