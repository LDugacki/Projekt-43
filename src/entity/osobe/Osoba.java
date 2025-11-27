package entity.osobe;
/**
 * Sadrži ime i prezime osobe, koje nasljeđuju Djelatnik i Gost.
 *
 * @since 1.0
 */
public abstract class Osoba {

    public   String ime;
    public   String prezime;

    public Osoba(String prezime, String ime) {
        this.prezime = prezime;
        this.ime = ime;
    }
}