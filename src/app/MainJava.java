package app;
import entity.*;
import entity.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

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

       Djelatnik djelatnik= inputDjelatnik(scInput);
       Soba[] sobe = new Soba[MAX_BROJ_SOBA];
       generateSobe(sobe);
       //inputSobe(MAX_BROJ_SOBA, scInput, sobe);

       Rezervacija[] rezervacije = new Rezervacija[MAX_BROJ_SOBA];

       inputRezervacije(MAX_BROJ_SOBA, scInput, formatter, sobe, rezervacije,djelatnik);

       menuMain(scInput, rezervacije);

       scInput.close();
    }

    /**
     * Generira fiksne sobe za testne rezervacije.
     * <p>
     * Sobe se kreiraju s tipom, ID-om i eventualnom napomenom.
     *
     * @param sobe polje {@code Soba} objekata koje se inicijalizira
     */
    private static void generateSobe(Soba[] sobe) {
        sobe[0]=new SobaBuilder()
                .tipSobe("Apartman")
                .sobaID(1)
                .build();
        sobe[1]=new SobaBuilder()
                .tipSobe("Suite")
                .sobaID(11)
                .sobaNapomena("Ne radi klima")
                .build();
        sobe[2]=new SobaBuilder()
                .tipSobe("Suite")
                .sobaID(12)
                .build();
        sobe[3]=new SobaBuilder()
                .tipSobe("Suite")
                .sobaID(13)
                .sobaNapomena("Treba popravit kotlić")
                .build();
        sobe[4]=new SobaBuilder()
                .tipSobe("Deluxe")
                .sobaID(21)
                .sobaNapomena("Sanitarna nam je zatvorila bazen do 15.01.2027, ako pitaju reci da je pokvaren i dolazi majstor slj tjedan")
                .build();
    }

    /**
     * Unosi podatke djelatnika s konzole.
     * <p>
     * Metoda traži od korisnika da unese ime i prezime djelatnika
     * i vraća kreirani {@code Djelatnik} objekt.
     * <p>
     * Podaci se logiraju putem SLF4J logera.
     *
     * @param scInput Scanner koji se koristi za unos s konzole
     * @return novokreirani {@code Djelatnik} objekt s unesenim imenom i prezimenom
     * @since 2.0
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
     * Pokreće glavni izbornik aplikacije.
     * <p>
     * Metoda prikazuje opcije za pretraživanje rezervacija,
     * statistiku rezervacija ili izlaz iz programa.
     * <p>
     * Opcije uključuju:
     * <ul>
     *   <li>Pretraživanje po imenu ili prezimenu gosta</li>
     *   <li>Pretraživanje po broju sobe</li>
     *   <li>Prikaz statistike (min/max ukupne cijene)</li>
     *   <li>Debug ispis svih rezervacija</li>
     * </ul>
     *
     * @param scInput Scanner koji se koristi za unos s konzole
     * @param rezervacije polje {@code Rezervacija} objekata koje se pretražuje i prikazuje
     * @since 1.0
     */
    public static void menuMain(Scanner scInput, Rezervacija[] rezervacije) {
        int menuMainChoice = 9;
        int correctInput;
        log.info("Pokrenut Menu");

        do {
            correctInput = 1;
            try {
                System.out.println("1] pretrazivanje");
                System.out.println("2] statistika(min/MAX)");
                System.out.println("3]_Debug_List_All");
                System.out.println("0] EXIT");
                System.out.print("Odaberi opciju: ");
                menuMainChoice = scInput.nextInt();

                log.info("Odabrana opcija: {}", menuMainChoice);

                if (menuMainChoice == 3) {
                    debug_rezervacijeOutputAll(5, rezervacije);
                } else if (menuMainChoice == 1) {
                    int menuSearchMode;
                    System.out.println("[] pretrazivanje po:");
                    System.out.println("1] imenu ili prezimenu");
                    System.out.println("2] sobi");
                    menuSearchMode = scInput.nextInt();

                    switch (menuSearchMode) {
                        case 1 -> {
                            System.out.println("Unesite ime ili prezime gosta:");
                            removeNewLine(scInput);
                            String tIme = scInput.nextLine();
                            int p = 0;
                            for (Rezervacija r : rezervacije) {
                                if (tIme.equalsIgnoreCase(r.gost().ime)
                                        || tIme.equalsIgnoreCase(r.gost().prezime)) {
                                    rezervacijaDetails(r);
                                    p++;
                                }
                            }
                            if (p == 0) {
                                System.out.println("Nema gostiju zvanih: " + tIme);
                                log.info("Nema rezultata za pretragu po imenu/prezimenu: {}", tIme);
                            }
                        }
                        case 2 -> {
                            System.out.println("Unesite broj sobe:");
                            int tBroj = scInput.nextInt();
                            boolean found = false;
                            for (Rezervacija r : rezervacije) {
                                if (tBroj == r.soba().sobaId) {
                                    rezervacijaDetails(r);
                                    found = true;
                                }
                            }
                            if (!found) {
                                System.out.println("Nema gostiju rezerviranih za sobu: " + tBroj);
                                log.info("Nema rezervacija za sobu {}", tBroj);
                            }
                        }
                        default -> log.warn("Nepostojeći izbor u pretrazi");
                    }
                } else if (menuMainChoice == 2) {
                    int g;
                    System.out.println("[] Statistika (min/MAX):");
                    System.out.println("1] min");
                    System.out.println("2] MAX");
                    g = scInput.nextInt();
                    int h;
                    System.out.println("[] Statistika po:");
                    System.out.println("[1] Ukupnoj Cijeni :");
                    h = scInput.nextInt();
                    switch (h) {
                        case 1 -> {
                            BigDecimal cijena = null;
                            int iRezervacije = 0;
                            for (int i = 0; i < rezervacije.length; i++) {
                                if (cijena == null) {
                                    cijena = rezervacije[i].cijenaUkupna();
                                    iRezervacije = i;
                                }
                                if ((cijena.compareTo(rezervacije[i].cijenaUkupna()) > 0) && (g == 1)) {
                                    iRezervacije = i;
                                    cijena = rezervacije[i].cijenaUkupna();
                                }
                                if ((cijena.compareTo(rezervacije[i].cijenaUkupna()) < 0) && (g == 2)) {
                                    iRezervacije = i;
                                    cijena = rezervacije[i].cijenaUkupna();
                                }
                            }
                            if (g == 1)
                                System.out.println("Najmanja Cijena Rezervacije je: " + cijena);
                            else
                                System.out.println("Najveca Cijena Rezervacije je: " + cijena);

                            rezervacijaDetails(rezervacije[iRezervacije]);
                            log.info("Statistika prikazana za opciju: {}", g);
                        }
                        default -> log.warn("Nepostojeća opcija statistike");
                    }
                } else if (menuMainChoice == 0) {
                    log.info("Program završava rad");
                    scInput.close();
                    System.exit(0);
                } else {
                    log.warn("Nepostojeći izbor u glavnom meniju");
                }

            } catch (java.util.InputMismatchException e) {
                log.warn("Pogrešan unos tipa podataka (InputMismatchException)");
                System.out.println("Pogrešan unos! Molimo unesite broj.");
                correctInput = 0;
                scInput.nextLine();
            }
        } while (correctInput != 1);
    }

    /**
     * Ispisuje detalje pojedine rezervacije na konzolu.
     * <p>
     * Podaci uključuju ime, prezime, kontakt, datume, cijene, tip i broj sobe te ime djelatnika koji je obradio rezervaciju.
     *
     * @param rezervacije {@code Rezervacija} objekt koji se ispisuje
     * @since 2.0
     */
    private static void rezervacijaDetails(Rezervacija rezervacije) {
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

    private static void debug_rezervacijeOutputAll(int maxBrojSoba, Rezervacija[] rezervacije) {
        log.info("Pokrenut debug_rezervacijeOutputAll");
        for (int i = 0; i < maxBrojSoba; i++) {
            System.out.println("Broj Rezervacije: "+ (i+1));
            rezervacijaDetails(rezervacije[i]);
            System.out.println();
        }

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
    private static void inputRezervacije(int maxBrojSoba, Scanner scInput, DateTimeFormatter formatter,
                                         Soba[] sobe, Rezervacija[] rezervacije, Djelatnik djelatnik) {
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


                    sobaReferenca = null;
                    for (Soba s : sobe) {
                        if (brojSobe == s.sobaId) {
                            sobaReferenca = s;
                            break;
                        }
                    }
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


            rezervacije[i] = new Rezervacija(tGost, i, datum, brojNocenja, cijenaNocenja, sobaReferenca, djelatnik);
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
