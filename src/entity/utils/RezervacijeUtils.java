package entity.utils;
import entity.*;
import entity.osobe.Djelatnik;
import entity.osobe.Gost;
import entity.sobe.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Unosi i ispisuje rezervacije.
 * <p>
 * Sadrži metode za unos i ispis podataka o gostima iz konzole.
 *
 * @since 2.0
 */
public class RezervacijeUtils {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final Logger log = LoggerFactory.getLogger(MenuUtils.class);
    public static Gost setGost(int i, Scanner sc) {
        System.out.println("Unos podataka za rezervaciju "+(i +1));
        System.out.println("Unesite ime gosta:");
        String Ime= sc.nextLine();
        System.out.println("Unesite prezime gosta:");
        String PIme= sc.nextLine();
        System.out.println("Unesite kontakt gosta(e-mail/TEL):");
        String Kontakt= sc.nextLine();
        return new Gost(Ime,PIme,Kontakt);
    }
    /**
     * Ispisuje detalje pojedine rezervacije na konzolu.
     * <p>
     * Podaci uključuju ime, prezime, kontakt, datume, cijene, tip i broj sobe te ime djelatnika koji je obradio rezervaciju.
     *
     * @param rezervacije {@code Rezervacija} objekt koji se ispisuje
     * @since 2.0
     */
    protected static void rezervacijaDetails(Rezervacija rezervacije) {
        System.out.println("Ime: "+ rezervacije.gost().ime);
        System.out.println("Prezime: "+ rezervacije.gost().prezime);
        System.out.println("Kontakt: "+ rezervacije.gost().kontakt);
        System.out.println("Datum Rezervacije: "+ rezervacije.datumRezervacije());
        System.out.println("Datum Dolaska: "+ rezervacije.datumDolaska());
        System.out.println("Broj Nocenja: "+ rezervacije.brojNocenja());
        System.out.println("Cijena Nocenja: "+ rezervacije.cijenaNocenja());
        System.out.println("Cijena Rezervacije: "+ rezervacije.cijenaUkupna());
        System.out.println("Vrsta Sobe: "+ rezervacije.soba().vrstaSobe());
        System.out.println("Broj Sobe: "+ rezervacije.soba().sobaId);
        System.out.println("Napomena: "+ rezervacije.soba().sobaNapomena);
        System.out.println("Rezervaciju obradio/la: "+ rezervacije.djelatnik().ime+" "+rezervacije.djelatnik().prezime);
        log.info("Ispis Rezervacije");
    }
    protected static void debug_rezervacijeOutputAll(int maxBrojSoba, List<Rezervacija> rezervacije) {
        log.info("Pokrenut debug_rezervacijeOutputAll");
        for (int i = 0; i < maxBrojSoba; i++) {
            System.out.println("Broj Rezervacije: "+ (i+1));
            rezervacijaDetails(rezervacije.get(i));
            System.out.println();
        }
        //rezervacije.forEach(RezervacijeUtils::rezervacijaDetails); //dodat prvo broj rezervacije u rezervacijaDetails
    }
    /**
     * Unosi rezervacije za sve sobe.
     * <p>
     * Metoda traži ime, prezime, kontakt gosta, datum dolaska, broj noćenja, cijenu i broj sobe.
     * Validira unos datuma i provjerava postoji li soba.
     * <p>
     * Koristi {@link RezervacijeUtils#setGost} za unos podataka gosta.
     *
     * @param maxBrojSoba maksimalni broj soba
     * @param scInput Scanner za unos s konzole
     * @param formatter DateTimeFormatter za parsiranje datuma
     * @param sobe polje {@code Soba} objekata
     * @param rezervacije polje {@code Rezervacija} objekata koje se popunjava
     * @param djelatnik {@code Djelatnik} koji obrađuje rezervacije
     * @throws InputMismatchException ako korisnik unese neispravan broj
     * @throws DateTimeParseException ako korisnik unese datum u pogrešnom formatu
     * @since 1.0
     */
    public static void inputRezervacije(int maxBrojSoba, Scanner scInput, DateTimeFormatter formatter,
                                        Map<Integer,Soba> sobe, List<Rezervacija> rezervacije, Djelatnik djelatnik) {
        log.info("Pokrenut inputRezervacije");

        for (int i = 0; i < maxBrojSoba; i++) {
            log.info("Unos rezervacije broj {}", (i + 1));

            Gost tGost = RezervacijeUtils.setGost(i, scInput);

            LocalDate datum = null;
            int brojNocenja = 0;
            BigDecimal cijenaNocenja = null;
            int brojSobe = 0;


            boolean validInput = false;
            do {
                try {
                    System.out.println("Unesite datum dolaska (dd-MM-yyyy):");
                    String datumString = scInput.nextLine();
                    datum = LocalDate.parse(datumString, formatter);
                    validInput = true;
                } catch (DateTimeParseException e) {
                    log.warn("Pogrešan format datuma pri unosu rezervacije {}", (i + 1));
                    System.out.println("Pogrešan format! Koristite format: dd-MM-yyyy");
                }
            } while (!validInput);


            validInput = false;
            do {
                try {
                    System.out.println("Unesite broj noćenja:");
                    brojNocenja = scInput.nextInt();
                    removeNewLine(scInput);
                    validInput = true;
                } catch (InputMismatchException e) {
                    log.warn("Pogrešan unos broja noćenja (InputMismatchException) kod rezervacije {}", (i + 1));
                    System.out.println("Pogrešan unos! Unesite cijeli broj.");
                    removeNewLine(scInput);
                }
            } while (!validInput);


            validInput = false;
            do {
                try {
                    System.out.println("Unesite cijenu noćenja:");
                    cijenaNocenja = scInput.nextBigDecimal();
                    removeNewLine(scInput);
                    validInput = true;
                } catch (InputMismatchException e) {
                    log.warn("Pogrešan unos cijene noćenja kod rezervacije {}", (i + 1));
                    System.out.println("Pogrešan unos! Unesite decimalni broj, npr. 120.50");
                    removeNewLine(scInput);
                }
            } while (!validInput);


            Soba sobaReferenca = null;
            do {
                validInput = false;
                try {
                    System.out.println("Unesite broj sobe gosta:");
                    brojSobe = scInput.nextInt();
                    removeNewLine(scInput);
                    validInput = true;

                    sobaReferenca = sobe.get(brojSobe);

                    if (sobaReferenca == null) {
                        System.out.println("Soba ne postoji! Unesite ispravan broj sobe.");
                        log.warn("Pogrešan broj sobe: {}", brojSobe);
                        validInput = false;
                    }

                } catch (InputMismatchException e) {
                    log.warn("Pogrešan unos broja sobe (InputMismatchException) kod rezervacije {}", (i + 1));
                    System.out.println("Pogrešan unos! Unesite broj sobe kao cijeli broj.");
                    removeNewLine(scInput);
                }
            } while (!validInput);


            rezervacije.add(new Rezervacija(tGost, i, datum, brojNocenja, cijenaNocenja, sobaReferenca, djelatnik));
            log.info("Rezervacija broj {} uspješno unesena", (i + 1));
        }
    }

    /**
     * Unosi podatke o sobama.
     * <p>
     * Metoda traži unos broja sobe i nasumično dodjeljuje tip sobe.
     *
     * @param n broj soba za unos
     * @param sc Scanner za unos s konzole
     * @param sobe polje {@code Soba} objekata koje se popunjava
     * @since 2.0 metoda se trenutačno ne koristi jer se sobe automatski generiraju
     */
    public static void inputSobe(int n, Scanner sc, Soba[] sobe) {
        log.info("inputSobe");
        int brojSobe;
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            System.out.println("Unesite broj sobe "+(i+1)+" :");
            brojSobe = sc.nextInt();
            removeNewLine(sc);//\n ostatak od nextInt();
            switch (random.nextInt(3)){
                case 0->sobe[i] = new Apartman(brojSobe);
                case 1->sobe[i] = new Deluxe(brojSobe);
                case 2->sobe[i] = new Suite(brojSobe);
            }

        }
    }
    /**
     * Generira fiksne sobe za testne rezervacije.
     * <p>
     * Sobe se kreiraju s tipom, ID-om i eventualnom napomenom.
     *
     * @param sobe polje {@code Soba} objekata koje se inicijalizira
     */
    public static void generateSobe(Map<Integer,Soba> sobe) {
        sobe.put(1, new SobaBuilder()
                .tipSobe("Apartman")
                .sobaID(1)
                .build());
        sobe.put(11,new SobaBuilder()
                .tipSobe("Suite")
                .sobaID(11)
                .sobaNapomena("Ne radi klima")
                .build());
        sobe.put(12,new SobaBuilder()
                .tipSobe("Suite")
                .sobaID(12)
                .build());
        sobe.put(13,new SobaBuilder()
                .tipSobe("Suite")
                .sobaID(13)
                .sobaNapomena("Treba popravit kotlić")
                .build());
        sobe.put(21,new SobaBuilder()
                .tipSobe("Deluxe")
                .sobaID(21)
                .sobaNapomena("Sanitarna nam je zatvorila bazen do 15.01.2027, ako pitaju reci da je pokvaren i dolazi majstor slj tjedan")
                .build());
    }
    /**
     * Unos podataka o djelatniku.
     *
     * @param scInput Scanner za unos s konzole
     * @return kreirani objekt Djelatnik
     */
    public static Djelatnik inputDjelatnik(Scanner scInput) {
        System.out.println("Upišite vaše ime:");
        String imeDjelatnika = scInput.nextLine();
        System.out.println("Upišite vaše prezime:");
        String prezimeDjelatnika = scInput.nextLine();
        log.info("Upisan djelatnik");
        return new Djelatnik(imeDjelatnika, prezimeDjelatnika);
    }
    /**
     * Uklanja ostatak linije iz Scanner-a nakon {@code nextInt()} ili {@code nextBigDecimal()}.
     * <p>
     * Metoda se koristi kako bi se spriječilo preskakanje unosa stringa.
     *
     * @param sc Scanner koji se koristi za čišćenje buffer-a
     * @since 1.0
     */
    public static void removeNewLine(Scanner sc) {
        sc.nextLine();
        log.info("Buffer očiščen");
    }
}
