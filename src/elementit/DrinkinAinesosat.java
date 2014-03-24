package elementit;

import java.io.Serializable;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "DRINKKI_AINESOSA")
@AssociationOverrides({
    @AssociationOverride(name = "pk.drinkki",
            joinColumns =
            @JoinColumn(name = "DRINKKI_ID")),
    @AssociationOverride(name = "pk.ainesosa",
            joinColumns =
            @JoinColumn(name = "AINESOSA_ID"))})
public class DrinkinAinesosat implements Serializable {

    private DrinkkiAinesosaId pk = new DrinkkiAinesosaId();
    private String maara;

    public DrinkinAinesosat() {
    }

    public DrinkinAinesosat(String maara) {
        this.maara = maara;
    }

    @EmbeddedId
    public DrinkkiAinesosaId getPk() {
        return pk;
    }

    public void setPk(DrinkkiAinesosaId pk) {
        this.pk = pk;
    }

    @Transient
    public Drinkki getDrinkki() {
        return pk.getDrinkki();
    }

    public void setDrinkki(Drinkki drinkki) {
        pk.setDrinkki(drinkki);
    }

    @Transient
    public Ainesosa getAinesosa() {
        return pk.getAinesosa();
    }

    public void setAinesosa(Ainesosa ainesosa) {
        pk.setAinesosa(ainesosa);
    }

    @Column(name = "maara")
    public String getMaara() {
        return maara;
    }

    public void setMaara(String maara) {
        this.maara = maara;
    }

    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t\t<ainesosa kategoria=\"").append(getAinesosa().getKategoria().getNimi()).append("\">\n");
        sb.append("\t\t\t\t<nimi>").append(getAinesosa().getNimi()).append("</nimi>\n");
        sb.append("\t\t\t\t<maara>").append(maara).append("</maara>\n");
        sb.append("\t\t\t</ainesosa>\n");
        return sb.toString();
    }
}
