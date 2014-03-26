package ohjain;

import elementit.Ainesosa;
import elementit.DrinkinAinesosat;
import java.util.List;
import elementit.Drinkki;
import elementit.Kategoria;
import hibernate.Hibernate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.swing.SwingUtilities;
import kayttoliittyma.UI;
import saxparser.MySaxParser;
import utils.MyComparator;

public class Ohjain {

    private List<Drinkki> drinkkilista = new ArrayList<>();
    private List<Ainesosa> ainesosalista = new ArrayList<>();
    private List<Kategoria> kategorialista = new ArrayList<>();
    private Drinkki drinkki;
    private MySaxParser msp;
    private UI ui;
    private Hibernate hib;
    private boolean importedFromSQL = false;

    public Ohjain() {
    }

    public Ohjain(Hibernate hib, UI ui) {
        this.hib = hib;
        this.ui = ui;
    }

    public static void main(String[] args) {
        Hibernate hib = new Hibernate();
        UI ui = new UI();
        Ohjain ohjain = new Ohjain(hib, ui);
        ui.rekisteroiOhjain(ohjain);
    }

    public void parseXML(String path) {
        importedFromSQL = false;
        MySaxParser msp = new MySaxParser();
        if (msp.parse(path)) {
            drinkkilista = msp.getDrinkkilista();
            kategorialista = msp.getKategorialista();
            ainesosalista = msp.getAinesosalista();
            addElementsToUI();
        }
    }

    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<drinkkilista>\n");
        for (Drinkki drinkki : drinkkilista) {
            sb.append(drinkki.toXML());
        }
        sb.append("</drinkkilista>");
        return sb.toString();
    }

    public void loadFromSQL() {
        if (hib.loadDatabase()) {
            drinkkilista = hib.getDrinkkiLista();
            ainesosalista = hib.getAinesosaLista();
            kategorialista = hib.getKategoriaLista();
            addElementsToUI();
            importedFromSQL = true;
        }
    }

    public void toSQL() {
        for (Drinkki drinkki : drinkkilista) {
            hib.addDrinkki(drinkki);
        }
    }

    public void poistaDrinkki(Drinkki drinkki) {
        if (importedFromSQL) {
            hib.poistaDrinkki(drinkki);
        }
    }

    public void lisaaKategoria(Kategoria kategoria) {
        kategorialista.add(kategoria);
        if (importedFromSQL) {
            hib.addKategoria(kategoria);
        }
    }

    public void poistaKategoria(Kategoria kategoria) {
        if (importedFromSQL) {
            hib.poistaKategoria(kategoria);
        }
    }

    private void addElementsToUI() {
        Comparator comp = new MyComparator();
        Collections.sort(drinkkilista, comp);
        Collections.sort(ainesosalista, comp);
        Collections.sort(kategorialista, comp);
        for (final Drinkki d : drinkkilista) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ui.addDrinkki(d);
                }
            });
        }
        for (final Kategoria k : kategorialista) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ui.addKategoria(k);
                }
            });
        }
        for (final Ainesosa a : ainesosalista) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ui.addAinesosa(a);
                }
            });
        }
    }

    public void suljeIstunto() {
        hib.suljeIstunto();
    }
}
