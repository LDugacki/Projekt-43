package entity.sobe;

/**
 * Kreiranje soba pomoću builder patterna.
 * <p>
 * Omogućava postavljanje ID-a, napomene i tipa sobe te kreiranje odgovarajućeg objekta.
 *
 * @since 1.0
 */
public class SobaBuilder {
    private int sobaId;
    private String sobaNapomena=" ";
    private String tipSobe;


    public SobaBuilder sobaID(int sobaId){
        this.sobaId=sobaId;
        return this;
    }
    public SobaBuilder sobaNapomena(String sobaNapomena){
        this.sobaNapomena=sobaNapomena;
        return this;
    }
    public SobaBuilder tipSobe(String tipSobe){
        this.tipSobe=tipSobe;
        return this;
    }
    /**
     * Kreira objekt sobe prema postavljenim parametrima.
     *
     * @return {@code Soba} objekt ili specifična soba (Apartman, Deluxe, Suite)
     * @since 1.0
     */
    public Soba build(){
        switch (tipSobe){
          case "Apartman" -> { return new Apartman(sobaId, sobaNapomena); }
          case "Deluxe"   -> { return new Deluxe(sobaId, sobaNapomena);   }
          case "Suite"    -> { return new Suite(sobaId, sobaNapomena);    }
          default         -> { return new Soba(sobaId, sobaNapomena);     }
        }
    }
}
