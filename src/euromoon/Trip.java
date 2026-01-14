package euromoon;

import java.util.*;
import java.time.LocalDateTime;

/**
 * Centrale klasse voor het beheer van een reis en de bijbehorende tickets.
 * * @author Houcine Bouamar
 * @version 1.0.0
 */
public class Trip {
    private String vertrek;
    private String bestemming;
    private LocalDateTime tijdstip;
    private Train trein;
    private List<Ticket> tickets = new ArrayList<>();
    private List<Personeel> personeelLijst = new ArrayList<>();

    public Trip(String vertrek, String bestemming, LocalDateTime tijdstip) {
        this.vertrek = vertrek;
        this.bestemming = bestemming;
        this.tijdstip = tijdstip;
    }

    /**
     * Voegt een ticket toe aan de reis als er plaats is.
     * @param t Het ticket object.
     * @return true als het ticket is toegevoegd, false indien volzet (max 80).
     */
    public boolean voegTicketToe(Ticket t) {
        if (tickets.size() < 80) {
            tickets.add(t);
            return true;
        }
        return false;
    }

    public void voegPersoneel(Personeel p) { personeelLijst.add(p); }

    /**
     * Controleert of de reis voldoet aan de personeelseisen.
     * @return true indien er minstens 1 bestuurder en 3 stewards zijn.
     */
    public boolean checkPersoneel() {
        int b = 0;
        int s = 0;
        for (Personeel p : personeelLijst) {
            if (p instanceof Bestuurder) b++;
            if (p instanceof Steward) s++;
        }
        return b >= 1 && s >= 3;
    }

    public String getVertrek() { return vertrek; }
    public String getBestemming() { return bestemming; }
    public LocalDateTime getTijdstip() { return tijdstip; }
    public Train getTrein() { return trein; }
    public List<Ticket> getTickets() { return tickets; }
    public void setTrein(Train t) { this.trein = t; }
}
