package entity.sobe;

/**
 * Predstavlja Suite sobu u hotelu.
 * <p>
 * Suite nasljeđuje {@link Soba}.
 *
 * @since 1.0
 */
public final class Suite extends Soba{
    public Suite(int sobaId, String sobaNapomena) {
        super(sobaId, sobaNapomena);
    }

    public Suite(int sobaId) {
        super(sobaId);
    }

    @Override
    public  String vrstaSobe(){
        return "Suite";
    }
}
