package euromoon;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.PrintWriter;

/**
 * Startpunt van de applicatie.
 * * @author Houcine Bouamar
 * @version 1.0.0
 */
public class Main {
    private static List<Passagier> lijstP = new ArrayList<>();
    private static List<Trip> lijstR = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static DateTimeFormatter dFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static DateTimeFormatter tFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== EUROMOON MENU ===");
            System.out.println("1. Registreer Passagier\n2. Maak Reis Aan\n3. Koppel Trein & Personeel\n4. Ticket verkopen\n5. Boardinglijst\n6. Stop");
            System.out.print("Maak uw keuze: ");
            String keuze = sc.nextLine();

            if (keuze.equals("6")) {
                System.out.println("Bedankt voor je bezoek! tot ziens!");
                break;
            }

            switch (keuze) {
                case "1": registreer(); break;
                case "2": maakReis(); break;
                case "3": koppel(); break;
                case "4": verkoop(); break;
                case "5": boarding(); break;
                default: System.out.println("Fout: Kies een getal tussen 1 en 6."); break;
            }
        }
    }

    /**
     * Methode om een passagier te registreren.
     */
    private static void registreer() {
        System.out.println("\n--- REGISTRATIE ---");
        String v = checkNaam("Voornaam: ");
        String a = checkNaam("Achternaam: ");

        LocalDate geboorte = null;
        while (geboorte == null) {
            System.out.print("Geboortedatum (DD.MM.JJJJ): ");
            try { geboorte = LocalDate.parse(sc.nextLine(), dFormat); }
            catch (Exception e) { System.out.println("Fout: Ongeldige datum."); }
        }

        String prefix = String.format("%02d.%02d.%02d", geboorte.getYear()%100, geboorte.getMonthValue(), geboorte.getDayOfMonth());
        String rr = "";
        while (true) {
            System.out.print("RR-nummer (Moet beginnen met " + prefix + "): ");
            rr = sc.nextLine();
            if (rr.startsWith(prefix) && rr.length() == 15) break;
            System.out.println("Fout: Het RR-nummer moet 11 cijfers bevatten en beginnen met " + prefix);
        }

        lijstP.add(new Passagier(v, a, rr, geboorte));
        System.out.println("\nGeregistreerd !");
        System.out.println(v + " " + a);
        System.out.println(geboorte.format(dFormat) + ", " + rr);
    }

    /**
     * Methode om een nieuwe reis op te stellen.
     */
    private static void maakReis() {
        System.out.println("\n--- NIEUWE REIS ---");
        List<String> steden = new ArrayList<>(Arrays.asList("Brussel", "Amsterdam", "Parijs"));
        String v = checkStad("Vertrekpunt", steden);
        List<String> aankomstOpties = new ArrayList<>();
        for(String s : steden) { if(!s.equalsIgnoreCase(v)) aankomstOpties.add(s); }
        String a = checkStad("Aankomst", aankomstOpties);

        LocalDateTime tijd = null;
        while (tijd == null) {
            System.out.print("Vertrekdatum en uur (DD.MM.YYYY HH:mm): ");
            try { tijd = LocalDateTime.parse(sc.nextLine(), tFormat); }
            catch (Exception e) { System.out.println("Fout: Gebruik het formaat DD.MM.YYYY HH:mm."); }
        }

        lijstR.add(new Trip(v, a, tijd));
        System.out.println("\nReis aangemaakt!");
        System.out.println("VAN " + v.toUpperCase() + " NAAR " + a.toUpperCase() + " " + tijd.toLocalTime());
    }

    /**
     * Valideert de stadsinvoer.
     * @param veld Het label voor de invoer.
     * @param opties De toegestane steden.
     * @return De gekozen stad als String.
     */
    private static String checkStad(String veld, List<String> opties) {
        String tekst = "";
        for(int i=0; i<opties.size(); i++) {
            tekst += opties.get(i) + (i < opties.size()-1 ? ", " : "");
        }
        while (true) {
            System.out.print(veld + " (" + tekst + "): ");
            String input = sc.nextLine();
            for (String s : opties) {
                if (s.equalsIgnoreCase(input)) return s;
            }
            System.out.println("Fout: Kies enkel uit " + tekst + ".");
        }
    }

    private static void koppel() {
        if (lijstR.isEmpty()) { System.out.println("Fout: Maak eerst een reis aan."); return; }
        Trip r = lijstR.get(lijstR.size()-1);
        String type = "";
        while(true) {
            System.out.print("Type locomotief (Class 373 of Class 374): ");
            type = sc.nextLine();
            if (type.equalsIgnoreCase("Class 373") || type.equalsIgnoreCase("Class 374")) break;
            System.out.println("Fout: Ongeldig type.");
        }
        r.setTrein(new Train(type));
        r.voegPersoneel(new Bestuurder("Jan", "Bestuurder", "RR1", LocalDate.now()));
        for(int i=0; i<3; i++) r.voegPersoneel(new Steward("Steward", ""+i, "RR", LocalDate.now()));
        System.out.println("Trein en personeel gekoppeld.");
    }

    private static void verkoop() {
        if (lijstR.isEmpty() || lijstP.isEmpty()) { System.out.println("Fout: Registreer eerst een passagier en reis."); return; }
        Trip r = lijstR.get(lijstR.size()-1);
        Passagier p = lijstP.get(lijstP.size()-1);

        System.out.print("Kies klasse voor " + p.getVoornaam() + " (1 = Eerste klasse, 2 = Tweede klasse.): ");
        int k = Integer.parseInt(sc.nextLine());

        if (r.voegTicketToe(new Ticket(p, k))) {
            System.out.println("Ticket in " + (k==1?"Eerste":"Tweede") + " klasse goed aangekocht!");
            System.out.println("Fijne Reis!");
            System.out.println("Ga verder naar 5 om je ticket te zien");
        }
    }

    /**
     * Genereert de boardinglijst als tekstbestand.
     */
    private static void boarding() {
        if (lijstR.isEmpty()) return;
        Trip r = lijstR.get(lijstR.size()-1);
        String f = r.getVertrek() + "_" + r.getBestemming() + "_" + r.getTijdstip().toString().replace(":", "-") + ".txt";

        try (PrintWriter pw = new PrintWriter(f)) {
            pw.println("==========================================");
            pw.println("      EUROMOON BOARDINGLIJST");
            pw.println("==========================================");
            pw.println("REIS: " + r.getVertrek().toUpperCase() + " -> " + r.getBestemming().toUpperCase());
            pw.println("DATUM: " + r.getTijdstip().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm")));
            if(r.getTrein() != null) pw.println("TREIN: " + r.getTrein().getType());
            pw.println("STATUS PERSONEEL: " + (r.checkPersoneel() ? "OK" : "NIET OK"));
            pw.println("------------------------------------------");
            for (Ticket t : r.getTickets()) {
                pw.println("PASSAGIER: " + t.getPassagier().getVoornaam() + " " + t.getPassagier().getAchternaam());
                pw.println("RR: " + t.getPassagier().getRR() + " | KLASSE: " + (t.getKlasse()==1?"1ste":"2de") + " Klasse");
                pw.println("------------------------------------------");
            }
            pw.println("Totaal aantal passagiers: " + r.getTickets().size());
            pw.println("==========================================");

            // ASCII Trein
            pw.println("                                          &&&&&&&&&");
            pw.println("                                        &&&");
            pw.println("                                       &&");
            pw.println("                                      &  _____ ___________");
            pw.println("                                     II__|[] | |   I I   |");
            pw.println("                                    |        |_|_  I I  _|");
            pw.println("                                   < OO----OOO   OO---OO");
            pw.println("**********************************************************");

            System.out.println("Boardinglijst aangemaakt: " + f);
        } catch (Exception e) { System.out.println("Fout bij schrijven."); }
    }

    /**
     * Valideert naam-invoer.
     * @param prompt De vraag voor de gebruiker.
     * @return De gevalideerde naam als String.
     */
    private static String checkNaam(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine();
            if (s.matches("[a-zA-Z]+")) return s;
            System.out.println("Fout: Naam mag enkel letters bevatten.");
        }
    }
}
