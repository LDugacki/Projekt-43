package app;
import entity.*;
import entity.osobe.Djelatnik;
import entity.sobe.Soba;
import entity.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Glavna klasa aplikacije za upravljanje hotelom.
 * <p>
 * Omogućava unos djelatnika, generiranje i unos soba te rezervacija.
 * Također pruža glavni izbornik za pretraživanje, statistiku i debug ispis rezervacija.
 * <p>
 * Logiranje se vrši putem SLF4J logera.
 *
 * @author Lovro Dugački
 * @version 3.0
 * @since Java 25
 */
public class MainJava {
    static Logger log= LoggerFactory.getLogger(MainJava.class);
    /**
     * Pokreće aplikaciju.
     * <p>
     * Metoda inicijalizira Scanner i DateTimeFormatter, unosi djelatnika,
     * generira sobe, unosi rezervacije i pokreće glavni izbornik.
     * <p>
     * Rezultat je pokrenuta konzolna aplikacija s logiranjem događaja.
     *
     * @param args argumenti komandne linije (nisu korišteni)
     * @since 1.0
     */
   static void main(String[] args) {
       log.info("Pokrenut program");
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
       Scanner scInput =new Scanner(System.in);
       final int MAX_BROJ_SOBA =5;
       Djelatnik djelatnik= RezervacijeUtils.inputDjelatnik(scInput);

        Map<Integer,Soba> sobe = new TreeMap<>();

       RezervacijeUtils.generateSobe(sobe);
       //inputSobe(MAX_BROJ_SOBA, scInput, sobe);
       List<Rezervacija> rezervacije = new ArrayList<>();
       RezervacijeUtils.inputRezervacije(MAX_BROJ_SOBA, scInput, formatter, sobe, rezervacije,djelatnik);
       MenuUtils.menuMain(scInput, rezervacije);
       scInput.close();
    }
}
