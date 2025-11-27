package entity.utils;
import entity.Rezervacija;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class StatUtils {
    public static Optional<Rezervacija> najjeftinija(List<Rezervacija> rezervacije) {
        return rezervacije.stream()
                .min(Comparator.comparing(Rezervacija::cijenaUkupna));
    }
    public static Optional<Rezervacija> najskuplja(List<Rezervacija> rezervacije) {
        return rezervacije.stream()
                .max(Comparator.comparing(Rezervacija::cijenaUkupna));
    }
    public static OptionalDouble prosjek(List<Rezervacija> rezervacije) {
        return rezervacije.stream()
                .mapToDouble(r -> r.cijenaUkupna().doubleValue())
                .average();
    }
    public static BigDecimal ukupniPrihod(List<? extends Rezervacija> rezervacije) {
        return rezervacije.stream()
                .map(Rezervacija::cijenaUkupna)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}