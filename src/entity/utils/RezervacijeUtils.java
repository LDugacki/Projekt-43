package entity.utils;

import entity.Gost;

import java.util.Scanner;

/**
 * Unosi rezervacije.
 * <p>
 * Sadrži metode za unos podataka o gostima iz konzole.
 *
 * @since 2.0
 */
public class RezervacijeUtils {
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

}
