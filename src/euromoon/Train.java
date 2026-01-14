package euromoon;

import java.util.ArrayList;
import java.util.List;

/**
 * Beheert de treinconfiguratie en de wagon-generatie.
 * * @author Houcine Bouamar
 * @version 1.0.0
 */
public class Train {
    private String type;
    private List<String> wagons = new ArrayList<>();

    /**
     * Maakt een trein aan en genereert wagons op basis van het type.
     * @param type De locomotief (Class 373 of Class 374).
     */
    public Train(String type) {
        this.type = type;
        int aantal = type.equalsIgnoreCase("Class 373") ? 12 : 14;
        for (int i = 1; i <= aantal; i++) {
            wagons.add("Wagon " + i);
        }
    }

    /**
     * Geeft het treintype terug.
     * @return Het type van de locomotief als String.
     */
    public String getType() { return type; }
}
