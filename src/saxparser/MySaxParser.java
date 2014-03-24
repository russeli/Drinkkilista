package saxparser;

import elementit.Ainesosa;
import elementit.DrinkinAinesosat;
import elementit.Drinkki;
import elementit.Kategoria;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class MySaxParser extends DefaultHandler {

    private final String TAG_DRINKKILISTA = "drinkkilista";
    private final String TAG_DRINKKI = "drinkki";
    private final String TAG_AINESOSA = "ainesosa";
    private final String TAG_AINESOSAT = "ainesosat";
    private final String TAG_NIMI = "nimi";
    private final String TAG_MAARA = "maara";
    public static OutFile outF = new OutFile();
    private static List<Drinkki> drinkkilista;
    private static List<Kategoria> kategorialista;
    private static String currentElement;
    private static String content;
    private static Drinkki drinkki;
    private static Ainesosa ainesosa;
    private Set<DrinkinAinesosat> drinkinAinesosat;
    private DrinkinAinesosat da;
    private String maara;
    private Stack<String> tagStack;

    public MySaxParser() {
        currentElement = "";
        tagStack = new Stack<String>();
    }

    public List parse(String uri) {

        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            parserFactory.setValidating(true);
            parserFactory.setNamespaceAware(false);
            //parserFactory.setFeature("http://apache.org/xml/features/validation/schema", true);
            //MySaxParser MySaxParserInstance = new MySaxParser();
            SAXParser parser = parserFactory.newSAXParser();
            parser.parse(uri, this);
        } catch (IOException | SAXException | ParserConfigurationException | FactoryConfigurationError exception) {
        }
        return drinkkilista;
    }

    @Override
    public void startDocument() throws SAXException {
        tagStack.push("");
        kategorialista = new ArrayList<Kategoria>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        tagStack.push(qName);
        switch (qName) {
            case TAG_DRINKKILISTA:
                drinkkilista = new ArrayList<Drinkki>();
                break;
            case TAG_DRINKKI:
                drinkki = new Drinkki();
                break;
            case TAG_AINESOSAT:
                drinkinAinesosat = new HashSet<DrinkinAinesosat>();
                break;
            case TAG_AINESOSA:
                da = new DrinkinAinesosat();
                ainesosa = new Ainesosa();
                String kategoriaNimi = attributes.getValue("kategoria");
                boolean loytyi = false;
                for (Kategoria kategoria : kategorialista) {
                    if (kategoria.getNimi().equalsIgnoreCase(kategoriaNimi)) {
                        ainesosa.setKategoria(kategoria);
                        loytyi = true;
                        break;
                    }
                }
                if (!loytyi) {
                    ainesosa.setKategoria(new Kategoria(attributes.getValue("kategoria")));
                }
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        content = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {

        String tag = tagStack.peek();
        if (!qName.equals(tag)) {
            throw new InternalError();
        }
        tagStack.pop();
        String parentTag = tagStack.peek();

        switch (qName) {
            case TAG_DRINKKI:
                drinkkilista.add(drinkki);
                break;
            case TAG_NIMI:
                if (TAG_AINESOSA.equalsIgnoreCase(parentTag)) {
                    ainesosa.setNimi(content.trim());
                } else if (TAG_DRINKKI.equalsIgnoreCase(parentTag)) {
                    drinkki.setNimi(content.trim());
                }
                break;
            case TAG_AINESOSA:
                da.setAinesosa(ainesosa);
                da.setDrinkki(drinkki);
                da.setMaara(maara);
                drinkinAinesosat.add(da);
                break;
            case TAG_MAARA:
                maara = content.trim();
                break;
            case TAG_AINESOSAT:
                drinkki.setAinesosat(drinkinAinesosat);
                break;
        }
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        throw new java.lang.UnsupportedOperationException("ASD:" + e.getMessage());
    }
}
