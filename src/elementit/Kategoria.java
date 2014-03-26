package elementit;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "KATEGORIA", uniqueConstraints =
        @UniqueConstraint(columnNames = {"id", "nimi"}))
public class Kategoria extends Elementti implements Serializable {

    private int ID;
    private Set<Ainesosa> ainesosat = new HashSet<>();

    public Kategoria(String nimi) {
        this(0, nimi);
    }

    public Kategoria(int ID, String nimi) {
        this.ID = ID;
        this.nimi = nimi;
    }

    public Kategoria() {
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

    @Column(name = "nimi", unique=true)
    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy="kategoria")
    public Set<Ainesosa> getAinesosat() {
        return ainesosat;
    }

    public void setAinesosat(Set<Ainesosa> ainesosat) {
        this.ainesosat = ainesosat;
    }
    
    public void addAinesosa(Ainesosa ainesosa) {
        ainesosat.add(ainesosa);
    }
}
