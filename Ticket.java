package euromoon;

/**
 * Beheert de ticketinformatie voor een passagier.
 * * @author Houcine Bouamar
 * @version 1.0.0
 */
public class Ticket {
    private Passagier passagier;
    private int klasse;

    public Ticket(Passagier passagier, int klasse) {
        this.passagier = passagier;
        this.klasse = klasse;
    }

    public Passagier getPassagier() { return passagier; }
    public int getKlasse() { return klasse; }
}