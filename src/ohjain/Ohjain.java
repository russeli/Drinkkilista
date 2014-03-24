package ohjain;

import elementit.DrinkinAinesosat;
import java.util.List;
import elementit.Drinkki;
import hibernate.Hibernate;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.SwingUtilities;
import kayttoliittyma.UI;
import saxparser.MySaxParser;

public class Ohjain {

    private List<Drinkki> drinkkilista;
    private Drinkki drinkki;
    private MySaxParser msp;
    private UI ui;
    private Hibernate hib;
    private boolean importedFromSQL = false;

    public Ohjain() {
        /*
         ui = new UI();
         msp = new MySaxParser();
         msp.parse("");
         ekokoelma = msp.getElokuvakokoelma();
         for(Elokuva ek : ekokoelma.getKokoelma()) {
         ui.lisaaElokuva(ek);
         }
         */
    }

    public Ohjain(Hibernate hib, UI ui) {
        this.hib = hib;
        this.ui = ui;
    }
    /*
     public void lisaaElokuva(String nimi, String tallennustyyppi, String julkaisuvuosi, String kesto, String genret) {
     Elokuva elokuva = new Elokuva();
     if(!nimi.equals(""))
     elokuva.nimi = nimi;
     if(!tallennustyyppi.equals(""))
     elokuva.tallennustyyppi = tallennustyyppi;
     if(!julkaisuvuosi.equals(""))
     elokuva.julkaisuvuosi = julkaisuvuosi;
     if(!kesto.equals(""))
     elokuva.kesto = kesto;
     if(!genret.equals(""))
     for (String genre : genret.split(",")) {
     elokuva.getGenret().add(genre.trim());
     }
     ekokoelma.lisaaElokuva(elokuva);
     ui.lisaaElokuva(elokuva);
     }

     public void poistaElokuva(Elokuva elokuva) {
     ekokoelma.poistaElokuva(elokuva);
     ui.poistaElokuva(elokuva);
     }

     public String kokoelmaToXML() {
     return ekokoelma.toXML();
     }
     */

    public static void main(String[] args) {
        Hibernate hib = new Hibernate();
        UI ui = new UI();
        Ohjain ohjain = new Ohjain(hib, ui);
        ui.rekisteroiOhjain(ohjain);
    }

    public void parseXML(String path) {
        importedFromSQL = false;
        MySaxParser msp = new MySaxParser();
        drinkkilista = msp.parse(path);
        for (final Drinkki d : drinkkilista) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ui.addDrinkki(d);
                }
            });
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
        drinkkilista = hib.loadDatabase();
        for (final Drinkki d : drinkkilista) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ui.addDrinkki(d);
                }
            });
        }
        importedFromSQL = true;
    }

    public void toSQL() {
        for (Drinkki drinkki : drinkkilista) {
            hib.addDrinkki(drinkki);
        }
    }

    public void poistaDrinkki(Drinkki drinkki) {
        if(importedFromSQL) {
            hib.poistaDrinkki(drinkki);
        }
    }
}
