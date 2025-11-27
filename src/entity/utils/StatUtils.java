package entity.utils;
import entity.Rezervacija;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Pruža statičke metode za statističku obradu rezervacija.
 *
 * <p>Omogućuje izračun najjeftinije i najskuplje rezervacije,
 * prosječne cijene te ukupnog prihoda na temelju lista rezervacija.</p>
 *
 * @since 5.0
 */
public class StatUtils {
    /**
     * Pronazi rezervaciju s najmanjom ukupnom cijenom.
     *
     * @param rezervacije lista rezervacija koje se analiziraju
     * @return {@code Optional} koji sadrži najjeftiniju rezervaciju,
     *         ili prazan {@code Optional} ako lista nema elemenata
     * @since 5.0
     */
    public static Optional<Rezervacija> najjeftinija(List<Rezervacija> rezervacije) {
        return rezervacije.stream()
                .min(Comparator.comparing(Rezervacija::cijenaUkupna));
    }
    /**
     * Pronazi rezervaciju s najvećom ukupnom cijenom.
     *
     * @param rezervacije lista rezervacija koje se analiziraju
     * @return {@code Optional} koji sadrži najskuplju rezervaciju,
     *         ili prazan {@code Optional} ako lista nema elemenata
     * @since 5.0
     */
    public static Optional<Rezervacija> najskuplja(List<Rezervacija> rezervacije) {
        return rezervacije.stream()
                .max(Comparator.comparing(Rezervacija::cijenaUkupna));
    }
    /**
     * Izračunava prosječnu ukupnu cijenu rezervacije.
     *
     * @param rezervacije lista rezervacija iz kojih se računa prosjek
     * @return {@code OptionalDouble} s prosječnom vrijednošću ako lista nije prazna,
     *         inače prazan {@code OptionalDouble}
     * @since 5.0
     */
    public static OptionalDouble prosjek(List<Rezervacija> rezervacije) {
        return rezervacije.stream()
                .mapToDouble(r -> r.cijenaUkupna().doubleValue())
                .average();
    }
    /**
     * Izračunava ukupan prihod zbrojem svih ukupnih cijena rezervacija.
     *
     * <p>Metoda koristi gornje ograničenje ("upper bounded wildcard") kako bi
     * prihvatila listu bilo kojeg tipa koji nasljeđuje {@link Rezervacija}.</p>
     *
     * @param rezervacije lista rezervacija ili podtipova rezervacija
     * @return zbroj svih ukupnih cijena rezervacija, ili {@code BigDecimal.ZERO}
     *         ako je lista prazna
     * @since 5.0
     */
    public static BigDecimal ukupniPrihod(List<? extends Rezervacija> rezervacije) {
        return rezervacije.stream()
                .map(Rezervacija::cijenaUkupna)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}