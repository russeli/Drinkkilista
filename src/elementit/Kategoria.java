package elementit;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "KATEGORIA", uniqueConstraints =
        @UniqueConstraint(columnNames = {"id", "nimi"}))
public class Kategoria implements Serializable {

    private int ID;
    private String nimi;

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
}
