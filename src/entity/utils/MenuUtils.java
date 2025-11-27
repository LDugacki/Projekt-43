package entity.utils;

import entity.Rezervacija;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;
/**
 * Glavni izbornik aplikacije
 * @since 3.0
 */
public class MenuUtils {
    private static final Logger log = LoggerFactory.getLogger(MenuUtils.class);
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
     *   <li>Prikaz statistike </li>
     * </ul>
     *
     * @param scInput Scanner koji se koristi za unos s konzole
     * @param rezervacije polje {@code Rezervacija} objekata koje se pretražuje i prikazuje
     * @since 3.0
     */
    public static void menuMain(Scanner scInput, List<Rezervacija> rezervacije) {
        int menuMainChoice = 9;
        int correctInput;
        log.info("Pokrenut Menu");

        do {
            correctInput = 1;
            try {
                System.out.println("1] pretrazivanje");
                System.out.println("2] statistika");
                System.out.println("3]_Debug_List_All");
                System.out.println("0] EXIT");
                System.out.print("Odaberi opciju: ");
                menuMainChoice = scInput.nextInt();

                log.info("Odabrana opcija: {}", menuMainChoice);

                if (menuMainChoice == 3) {
                    RezervacijeUtils.debug_rezervacijeOutputAll(5, rezervacije);
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
                                    RezervacijeUtils.rezervacijaDetails(r);
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
                                    RezervacijeUtils.rezervacijaDetails(r);
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
                    System.out.println("[] Statistika :");
                    System.out.println("1] min");
                    System.out.println("2] MAX");
                    System.out.println("3] Detalji");
                    System.out.println("4] Ukupan Prihod");
                    g = scInput.nextInt();
                    int h;
                    System.out.println("[] Statistika po:");
                    System.out.println("[1] Ukupnoj Cijeni :");
                    h = scInput.nextInt();
                    switch (h) {
                        case 1 -> {
                            Comparator<Rezervacija> compCijena =
                                    Comparator.comparing(Rezervacija::cijenaUkupna);

                            List<Rezervacija> sortirano = rezervacije.stream()
                                    .sorted(compCijena)
                                    .toList();
                            //BigDecimal cijena = null;
                            //int iRezervacije = 0;
                            //for (int i = 0; i < rezervacije.size(); i++) {
                            //if (cijena == null) {
                            //    cijena = rezervacije.get(i).cijenaUkupna();
                            //}
                            switch (g) {

                                case 1 -> {
                                    StatUtils.najjeftinija(rezervacije)
                                            .ifPresent(r -> System.out.println("Najmanja Cijena Rezervacije je: " + r.cijenaUkupna()));
                                    //iRezervacije = i;
                                    //cijena = sortirano.getFirst().cijenaUkupna();
                                }
                                case 2 -> {
                                    StatUtils.najskuplja(rezervacije)
                                            .ifPresent(r -> System.out.println("Najveca Cijena Rezervacije je:" + r.cijenaUkupna()));
                                    //iRezervacije = i;
                                    //cijena = sortirano.getLast().cijenaUkupna();
                                }
                                case 3 -> {
                                    DoubleSummaryStatistics stats = sortirano.stream()
                                            .mapToDouble(r -> r.cijenaUkupna().doubleValue())
                                            .summaryStatistics();

                                    System.out.println("Broj rezervacija: " + stats.getCount());
                                    System.out.println("Minimalna cijena: " + stats.getMin());
                                    System.out.println("Maksimalna cijena: " + stats.getMax());
                                    System.out.println("Prosječna cijena: " + stats.getAverage());
                                    System.out.println("Suma: " + stats.getSum());
                                    //else
                                    //System.out.println("Najveca Cijena Rezervacije je: " + cijena);
                                }
                                case 4 -> System.out.println("Ukupan prihod je" + StatUtils.ukupniPrihod(rezervacije));
                            }
                            //if (g == 1 || g == 2) RezervacijeUtils.rezervacijaDetails(rezervacije.get(iRezervacije));
                            //log.info("Statistika prikazana za opciju: {}", g);
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
