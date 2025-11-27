package entity.sobe;


/**
 * Predstavlja Deluxe sobu u hotelu.
 * <p>
 * Deluxe sobe imaju pristup bazenu (sučelje {@link Bazen}) i mogu imati napomenu.
 *
 * @since 2.0
 */
public final class Deluxe extends  Soba implements Bazen{
    private static final boolean bazenOtvoren=false; //bazeni u hotelu su uvijek zatvoreni :)


    public Deluxe(int sobaId, String sobaNapomena) {
        super(sobaId, sobaNapomena);
    }

    public Deluxe(int sobaId) {
        super(sobaId);
    }

    /**
     * Dohvaća vrstu sobe.
     *
     * @return uvijek {@code "Deluxe"}
     * @since 2.0
     */
    @Override
    public  String vrstaSobe(){
        return "Deluxe";
    }
}
