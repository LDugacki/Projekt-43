package entity.osobe;

/**
 * Predstavlja djelatnika hotela.
 * <p>
 * Nasljeđuje {@link Osoba} i omogućava dohvat imena i prezimena.
 *
 * @since 2.0
 */
public class Djelatnik extends Osoba{

    /**
     * Inicijalizira djelatnika s imenom i prezimenom.
     *
     * @param ime ime djelatnika
     * @param prezime prezime djelatnika
     * @since 2.0
     */
    public Djelatnik(String ime, String prezime) {
        super(ime, prezime);
    }
    /**
     * Dohvaća ime djelatnika.
     *
     * @return ime djelatnika
     * @since 2.0
     */
    public String ime() { return ime; }
    /**
     * Dohvaća prezime djelatnika.
     *
     * @return prezime djelatnika
     * @since 2.0
     */
    public String prezime() { return prezime; }
}
