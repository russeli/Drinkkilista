package elementit;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class DrinkkiAinesosaId implements Serializable {
    
    private Drinkki drinkki;
    private Ainesosa ainesosa;

    @ManyToOne
    public Drinkki getDrinkki() {
        return drinkki;
    }

    public void setDrinkki(Drinkki drinkki) {
        this.drinkki = drinkki;
    }

    @ManyToOne
    public Ainesosa getAinesosa() {
        return ainesosa;
    }

    public void setAinesosa(Ainesosa ainesosa) {
        this.ainesosa = ainesosa;
    }
}
