package entity.sobe;
/**
 * Predstavlja apartman u hotelu.
 * <p>
 * Apartman nasljeđuje {@link Soba} i implementira sučelje {@link Kuhinja}.
 * <p>
 * Omogućava dohvat tipa sobe i kreiranje objekta apartmana s ili bez napomene.
 *
 * @author Vaše Ime
 * @since 2.0
 */
public final class Apartman extends Soba implements Kuhinja{

    public Apartman(int sobaId, String sobaNapomena) {
        super(sobaId, sobaNapomena);
    }

    public Apartman(int sobaId) {
        super(sobaId);
    }

    @Override
    public String vrstaSobe(){
        return "Apartman";
    }

}
