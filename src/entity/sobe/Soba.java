package entity.sobe;
/**
 * Predstavlja sobu u hotelu.
 * <p>
 * Sadrži identifikacijski broj sobe i opcionalnu napomenu.
 * Može biti nadjačana od strane specifičnih soba (Apartman, Deluxe, Suite).
 *
 * @since 1.0
 */
public class Soba {
    public int sobaId;
    public String sobaNapomena;
     //boolean isZauzeta(){
     // return 0;} //tbi

    /**
     * Inicijalizira sobe s napomenom.
     *
     * @param sobaId ID sobe
     * @param sobaNapomena napomena o sobi
     * @since 2.0
     */
    public Soba(int sobaId, String sobaNapomena) {
        this.sobaId = sobaId;
        this.sobaNapomena = sobaNapomena;
    }
    /**
     * Inicijalizira sobe bez napomene.
     *
     * @param sobaId ID sobe
     * @since 1.0
     */
    public Soba(int sobaId) {
        this.sobaId = sobaId;
    }
    /**
     * Dohvaća vrstu sobe.
     *
     * @return tip sobe kao string
     * @since 2.0
     */
    public String vrstaSobe(){
        return "Soba";
    }
}


