package elementit;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "AINESOSA")
public class Ainesosa implements Serializable {
    
    private int ID;
    private String nimi;
    private Kategoria kategoria;

    public Ainesosa(int ID, String nimi, Kategoria kategoria) {
        this.ID = ID;
        this.nimi = nimi;
        this.kategoria = kategoria;
    }

    public Ainesosa() {
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

    @JoinColumn(name = "kategoria")
    @ManyToOne
    public Kategoria getKategoria() {
        return kategoria;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }
}
