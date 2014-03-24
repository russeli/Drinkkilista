package elementit;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "DRINKKI")
public class Drinkki implements Serializable {
    
    private int ID;
    private String nimi;
    private Set<DrinkinAinesosat> ainesosat = new HashSet<DrinkinAinesosat>();

    public Drinkki(int ID, String nimi) {
        this.ID = ID;
        this.nimi = nimi;
    }

    public Drinkki() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Column(name = "nimi")
    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.drinkki", cascade=CascadeType.ALL)
    public Set<DrinkinAinesosat> getAinesosat() {
        return ainesosat;
    }

    public void setAinesosat(Set<DrinkinAinesosat> ainesosat) {
        this.ainesosat = ainesosat;
    }

    public String toXML() {
        StringBuilder sb = new StringBuilder();
            sb.append("\t<drinkki>\n");
            sb.append("\t\t<nimi>").append(nimi).append("</nimi>\n");
            sb.append("\t\t<ainesosat>\n");
            Iterator<DrinkinAinesosat> ite = ainesosat.iterator();
            while (ite.hasNext()) {
                DrinkinAinesosat da = ite.next();
                sb.append(da.toXML());
            }
            sb.append("\t\t</ainesosat>\n");
            sb.append("\t</drinkki>\n");
            return sb.toString();
    }
    
    @Override
    public String toString() {
        return nimi;
    }
}