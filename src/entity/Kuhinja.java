package entity;

/**
 * Označava da soba ima kuhinju.
 * <p>
 * Samo {@link Apartman} implementira ovo sučelje.
 * Sadrži statičku metodu za provjeru dostupnosti kuhinje.
 *
 * @since 2.0
 */
public sealed interface Kuhinja permits Apartman {


    /**
     * Provjerava ima li soba kuhinju.
     *
     * @return uvijek {@code true} jer svaka soba koja implementira {@code Kuhinja} ima kuhinju
     * @since 2.0
     */
    public static boolean imaKuhinju(){return true;}
}
