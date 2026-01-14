package euromoon;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Superklasse voor alle personen in het Euromoon systeem.
 * Bevat de gedeelde attributen zoals naam en rijksregisternummer.
 * * @author Houcine Bouamar
 * @version 1.0.0
 */
public abstract class Persoon {
    protected String voornaam;
    protected String achternaam;
    protected String rijksregisternummer;
    protected LocalDate geboortedatum;

    public Persoon(String voornaam, String achternaam, String rr, LocalDate geboorte) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.rijksregisternummer = rr;
        this.geboortedatum = geboorte;
    }

    public String getVoornaam() { return voornaam; }
    public String getAchternaam() { return achternaam; }
    public String getRR() { return rijksregisternummer; }
    public LocalDate getGeboorte() { return geboortedatum; }
}

/**
 * Klasse voor passagiers.
 * @author Houcine Bouamar
 * @version 1.0.0
 */
class Passagier extends Persoon {
    public Passagier(String v, String a, String rr, LocalDate d) { super(v, a, rr, d); }
}

/**
 * Basisklasse voor personeel met certificaatbeheer.
 * @author Houcine Bouamar
 * @version 1.0.0
 */
class Personeel extends Persoon {
    protected List<String> certificaties = new ArrayList<>();
    public Personeel(String v, String a, String rr, LocalDate d) { super(v, a, rr, d); }

    /**
     * Voegt een certificaat toe aan de lijst van de medewerker.
     * @param c De naam van het certificaat (bv. Veiligheid).
     */
    public void voegCertificaatToe(String c) { certificaties.add(c); }
}

class Bestuurder extends Personeel {
    public Bestuurder(String v, String a, String rr, LocalDate d) { super(v, a, rr, d); }
}
class Steward extends Personeel {
    public Steward(String v, String a, String rr, LocalDate d) { super(v, a, rr, d); }
}
class Conducteur extends Personeel {
    public Conducteur(String v, String a, String rr, LocalDate d) { super(v, a, rr, d); }
}
class BagagePersoneel extends Personeel {
    public BagagePersoneel(String v, String a, String rr, LocalDate d) { super(v, a, rr, d); }
}
