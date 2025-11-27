package entity;
import entity.osobe.Djelatnik;
import entity.osobe.Gost;
import entity.sobe.Soba;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Predstavlja rezervaciju sobe od strane gosta.
 * <p>
 * Sadrži informacije o gostu, sobi, cijeni, datumu dolaska i broju noćenja.
 * <p>
 * Automatski postavlja datum rezervacije na trenutni ako nije specificiran.
 *
 * @since 1.0
 */
public record Rezervacija(
        Gost gost,
        int id,
        LocalDate datumDolaska,
        int brojNocenja,
        BigDecimal cijenaNocenja,
        Soba soba,
        Djelatnik djelatnik,
        LocalDateTime datumRezervacije) {


    public Rezervacija(Gost gost, int id, LocalDate datumDolaska, int brojNocenja,
                       BigDecimal cijenaNocenja, Soba soba, Djelatnik djelatnik) {
        this(gost, id, datumDolaska, brojNocenja, cijenaNocenja, soba, djelatnik, LocalDateTime.now());
    }
    public static void dodajRezervaciju(
            List<? super Rezervacija> lista,
            Rezervacija r) {
        lista.add(r);
    }
/**
 * Izračunava ukupnu cijenu rezervacije.
 *
 * @return {@code BigDecimal} ukupne cijene rezervacije (cijena noćenja * broj noćenja)
 * @since 1.0
*/
public BigDecimal cijenaUkupna() {
        return cijenaNocenja.multiply(BigDecimal.valueOf(brojNocenja));
    }
}
