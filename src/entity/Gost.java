package entity;

/**
 * Predstavlja gosta hotela.
 * <p>
 * Nasljeđuje {@link Osoba} i sadrži kontakt podatke.
 *
 * @since 1.0
 */
public class Gost extends Osoba{

    public  String kontakt;
    /**
     * Inicijalizira gosta
     *
     * @param ime ime gosta
     * @param prezime prezime gosta
     * @param kontakt kontakt gosta
     * @since 1.0
     */
    public Gost(String ime, String prezime, String kontakt) {
       super(ime,prezime);
       this.kontakt = kontakt;
    }
    /**
     * Dohvaća ime gosta.
     *
     * @return ime gosta
     * @since 2.0
     */
    public String ime() { return ime; }
    /**
     * Dohvaća prezime gosta.
     *
     * @return ime gosta
     * @since 2.0
     */
    public String prezime() { return prezime; }
}