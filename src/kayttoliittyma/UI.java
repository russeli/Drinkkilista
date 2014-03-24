package kayttoliittyma;

import elementit.DrinkinAinesosat;
import elementit.Drinkki;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import ohjain.Ohjain;

public class UI extends JFrame implements ActionListener, ListSelectionListener {

    private JPanel sisältöpaneeli;
    private JPanel radiopaneeli, listapaneeli, detailpaneeli, nappulapaneeli;
    private JPanel dialogiPaneeli, lisaaPaneeli;
    private JPanel lisaaKategoriaKortti, lisaaAinesosaKortti, lisaaDrinkkiKortti; //Dialogin kortit
    private JPanel lisaaKategoriaKorttiWrapper; //Ja niitten wrapperit T_T
    private JMenuBar menuBar;
    private JMenu menu, submenu;
    private JMenuItem menuItem;
    private JScrollPane listScrollPane, detailScrollPane;
    private JButton lisaaDrinkkiPainike, poistaDrinkkiPainike, muokkaaTietojaPainike;
    private JLabel kategorianimilabel, ainesosanimilabel, drinkkinimilabel;
    private JLabel kategorialabel, ainesosakategorialabel, julkaisuvuosilabel, tallennustyyppilabel, kestolabel, genrelabel;
    private JTextField kategorianimikentta, ainesosanimikentta, drinkkinimikentta;
    private JTextField julkaisuvuosikentta, tallennustyyppikentta, kestokentta, genrekentta;
    private JTextArea detailkentta;
    private JRadioButton drinkkiButton, ainesosaButton, kategoriaButton;
    private JList drinkkilista;
    private DefaultListModel listmodel;
    private JComboBox lisaysValinta, kategoriaValinta;
    private List<JComboBox> ainesosaValinnat;
    private Ohjain ohjain;
    private final String drinkkiString = "Drinkki", ainesosaString = "Ainesosa", kategoriaString = "Kategoria";
    private final String[] dialogiValinnat = {drinkkiString, ainesosaString, kategoriaString};

    public UI() {
        alustaKomponentit();
    }

    private void alustaKomponentit() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Drinkkikokoelma");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setPreferredSize(new Dimension(320,105));

        //MENU
        menuBar = new JMenuBar();

        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);

        submenu = new JMenu("Import");
        submenu.setMnemonic(KeyEvent.VK_I);
        menuItem = new JMenuItem("from SQL Database", KeyEvent.VK_S);
        menuItem.setActionCommand("FROM_SQL");
        menuItem.addActionListener(this);
        submenu.add(menuItem);
        menuItem = new JMenuItem("from XML file", KeyEvent.VK_M);
        menuItem.setActionCommand("FROM_XML");
        menuItem.addActionListener(this);
        submenu.add(menuItem);
        menu.add(submenu);

        submenu = new JMenu("Export");
        submenu.setMnemonic(KeyEvent.VK_E);
        menuItem = new JMenuItem("to SQL Database", KeyEvent.VK_S);
        menuItem.setActionCommand("TO_SQL");
        menuItem.addActionListener(this);
        submenu.add(menuItem);
        menuItem = new JMenuItem("to XML file", KeyEvent.VK_M);
        menuItem.setActionCommand("TO_XML");
        menuItem.addActionListener(this);
        submenu.add(menuItem);
        menu.add(submenu);

        menu.addSeparator();

        menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        menuItem.setActionCommand("EXIT");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //PANEELIT
        sisältöpaneeli = new JPanel();
        listapaneeli = new JPanel();
        listapaneeli.setBorder(new EmptyBorder(5, 0, 0, 0));
        detailpaneeli = new JPanel();
        detailpaneeli.setLayout(new BorderLayout());
        detailpaneeli.setBorder(new EmptyBorder(10, 10, 10, 10));
        nappulapaneeli = new JPanel();
        radiopaneeli = new JPanel();
        radiopaneeli.setLayout(new BoxLayout(radiopaneeli, BoxLayout.PAGE_AXIS));

        //SCROLLPANET
        listScrollPane = new JScrollPane();
        detailScrollPane = new JScrollPane();

        //LISAA DIALOGIN PANEELI
        dialogiPaneeli = new JPanel();
        dialogiPaneeli.setLayout(new BoxLayout(dialogiPaneeli, BoxLayout.PAGE_AXIS));
        //lisättävän elementin valintapaneeli ja combobox
        lisaysValinta = new JComboBox(dialogiValinnat);
        lisaysValinta.setSelectedIndex(0);
        lisaaPaneeli = new JPanel(new CardLayout());
        lisaaKategoriaKortti = new JPanel();
        lisaaKategoriaKortti.setLayout(new SpringLayout());
        lisaaKategoriaKorttiWrapper = new JPanel();
        lisaaKategoriaKorttiWrapper.setLayout(new BoxLayout(lisaaKategoriaKorttiWrapper, BoxLayout.PAGE_AXIS));
        lisaaAinesosaKortti = new JPanel();
        lisaaAinesosaKortti.setLayout(new SpringLayout());
        lisaaDrinkkiKortti = new JPanel();
        lisaaDrinkkiKortti.setLayout(new SpringLayout());

        kategorianimilabel = new JLabel("Nimi:");
        kategorianimikentta = new JTextField(5);
        ainesosanimilabel = new JLabel("Nimi:");
        ainesosanimikentta = new JTextField(5);
        kategoriaValinta = new JComboBox();
        kategorialabel = new JLabel("Kategoria:");
        tallennustyyppikentta = new JTextField(5);
        julkaisuvuosilabel = new JLabel("Julkaisuvuosi:");
        julkaisuvuosikentta = new JTextField(5);
        kestolabel = new JLabel("Kesto:");
        kestokentta = new JTextField(5);
        genrelabel = new JLabel("Genre:");
        genrekentta = new JTextField(5);

        lisaaKategoriaKortti.add(kategorianimilabel);
        lisaaKategoriaKortti.add(kategorianimikentta);
        lisaaKategoriaKorttiWrapper.add(lisaaKategoriaKortti);
        lisaaKategoriaKorttiWrapper.add(Box.createVerticalGlue());
        lisaaAinesosaKortti.add(ainesosanimilabel);
        lisaaAinesosaKortti.add(ainesosanimikentta);
        lisaaAinesosaKortti.add(kategorialabel);
        lisaaAinesosaKortti.add(kategoriaValinta);
        /*
         lisaaKategoriaKortti.add(tallennustyyppilabel);
         lisaaKategoriaKortti.add(tallennustyyppikentta);
         lisaaKategoriaKortti.add(julkaisuvuosilabel);
         lisaaKategoriaKortti.add(julkaisuvuosikentta);
         lisaaKategoriaKortti.add(kestolabel);
         lisaaKategoriaKortti.add(kestokentta);
         lisaaKategoriaKortti.add(genrelabel);
         lisaaKategoriaKortti.add(genrekentta);*/
        SpringUtilities.makeCompactGrid(lisaaKategoriaKortti, 1, 2, 6, 6, 6, 6);
        SpringUtilities.makeCompactGrid(lisaaAinesosaKortti, 2, 2, 6, 6, 6, 6);
        //lisaaPaneeli.add(lisaaDrinkkiKortti, "Drinkki");
        //lisaaPaneeli.add(lisaaAinesosaKortti, "Ainesosa");
        //lisaaPaneeli.add(lisaaKategoriaKorttiWrapper, "Kategoria");
        dialogiPaneeli.add(lisaysValinta);
        dialogiPaneeli.add(lisaaPaneeli);

        //PAINIKKEET
        lisaaDrinkkiPainike = new JButton("Lisää drinkki");
        poistaDrinkkiPainike = new JButton("Poista drinkki");
        poistaDrinkkiPainike.setEnabled(false);
        muokkaaTietojaPainike = new JButton("Muokkaa tietoja");
        muokkaaTietojaPainike.setEnabled(false);

        //RADIONAPPULAT
        drinkkiButton = new JRadioButton(drinkkiString);
        drinkkiButton.setActionCommand(drinkkiString);
        drinkkiButton.setSelected(true);
        ainesosaButton = new JRadioButton(ainesosaString);
        ainesosaButton.setActionCommand(ainesosaString);
        kategoriaButton = new JRadioButton(kategoriaString);
        kategoriaButton.setActionCommand(kategoriaString);
        ButtonGroup group = new ButtonGroup();
        group.add(drinkkiButton);
        group.add(ainesosaButton);
        group.add(kategoriaButton);
        RadioButtonListener rbl = new RadioButtonListener(this);
        drinkkiButton.addActionListener(rbl);
        ainesosaButton.addActionListener(rbl);
        kategoriaButton.addActionListener(rbl);
        radiopaneeli.add(drinkkiButton);
        radiopaneeli.add(ainesosaButton);
        radiopaneeli.add(kategoriaButton);

        //DRINKKILISTA
        listmodel = new DefaultListModel();
        drinkkilista = new JList(listmodel);
        drinkkilista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        drinkkilista.setSelectedIndex(0);
        drinkkilista.addListSelectionListener(this);
        drinkkilista.setVisibleRowCount(8);
        drinkkilista.setPrototypeCellValue("Drinkintosipitkänimi");
        //elokuvalista.setPreferredSize(new Dimension(elokuvalista.getFixedCellWidth(), elokuvalista.getFixedCellHeight() * 8));
        listScrollPane.setPreferredSize(new Dimension(drinkkilista.getFixedCellWidth(), drinkkilista.getFixedCellHeight() * 8));
        listScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        listScrollPane.setViewportView(drinkkilista);

        //LISÄTIEDOT
        detailkentta = new JTextArea();
        detailkentta.setFont(new Font("Courier New", Font.PLAIN, 12));
        detailkentta.setPreferredSize(new Dimension(220, 120));
        detailkentta.setMinimumSize(new Dimension(220, 120));
        detailkentta.setRows(5);
        detailkentta.setColumns(20);
        detailkentta.setLineWrap(false);
        detailkentta.setEditable(false);
        detailScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        detailScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        detailScrollPane.setViewportView(detailkentta);
        //detailpaneeli.add(detailkentta, BorderLayout.CENTER);
        detailpaneeli.add(detailScrollPane, BorderLayout.CENTER);
        detailpaneeli.add(muokkaaTietojaPainike, BorderLayout.PAGE_END);

        //NAPPULAT
        nappulapaneeli.add(lisaaDrinkkiPainike);
        nappulapaneeli.add(poistaDrinkkiPainike);

        //listapaneeli.add(elokuvalista);
        listapaneeli.add(radiopaneeli);
        listapaneeli.add(listScrollPane);
        sisältöpaneeli.add(listapaneeli, BorderLayout.LINE_START);
        //sisältöpaneeli.add(detailpaneeli, BorderLayout.LINE_END);
        sisältöpaneeli.add(detailpaneeli, BorderLayout.CENTER);
        sisältöpaneeli.add(nappulapaneeli, BorderLayout.PAGE_END);
        setContentPane(sisältöpaneeli);
        setJMenuBar(menuBar);

        lisaaDrinkkiPainike.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane jop = new JOptionPane();
                jop.add(dialogiPaneeli);
                jop.createDialog("asd");
                jop.setVisible(true);

                int result = JOptionPane.showConfirmDialog(
                        null,
                        dialogiPaneeli,
                        "Lisää elokuva",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String nimi = kategorianimikentta.getText();
                    String kategoria = tallennustyyppikentta.getText();
                    String julkaisuvuosi = julkaisuvuosikentta.getText();
                    String kesto = kestokentta.getText();
                    String genre = genrekentta.getText();
                    //ohjain.lisaaElokuva(nimi, tallennustyyppi, julkaisuvuosi, kesto, genre);
                }
                kategorianimikentta.setText("");
                julkaisuvuosikentta.setText("");
                tallennustyyppikentta.setText("");
                kestokentta.setText("");
                genrekentta.setText("");
                //If you're here, the return value was null/empty.
                //setLabel("Come on, finish the sentence!");
            }
        });

        poistaDrinkkiPainike.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (drinkkilista.getSelectedIndex() != -1) {
                    Drinkki drinkki = (Drinkki) listmodel.getElementAt(drinkkilista.getSelectedIndex());
                    ohjain.poistaDrinkki(drinkki);
                    listmodel.removeElement(drinkki);
                    detailkentta.setText("");
                    drinkkilista.clearSelection();
                    /*elokuvalista.revalidate();
                     paivitaNakyma();*/
                }
            }
        });

        /*
         muokkaaTietojaPainike.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
         Elokuva elokuva = (Elokuva) listmodel.getElementAt(elokuvalista.getSelectedIndex());
         nimikentta.setText(elokuva.nimi);
         tallennustyyppikentta.setText(elokuva.tallennustyyppi);
         julkaisuvuosikentta.setText(elokuva.julkaisuvuosi);
         kestokentta.setText(elokuva.kesto);
         StringBuilder genret = new StringBuilder();
         for (String genre : elokuva.getGenret()) {
         genret.append(genre);
         genret.append(", ");
         }
         genret.delete(genret.length() - 2, genret.length());
         genrekentta.setText(genret.toString());
         int result = JOptionPane.showConfirmDialog(
         null,
         lisaapaneeli,
         "Päivitä tiedot",
         JOptionPane.OK_CANCEL_OPTION);
         if (result == JOptionPane.OK_OPTION) {
         elokuva.nimi = nimikentta.getText();
         elokuva.tallennustyyppi = tallennustyyppikentta.getText();
         elokuva.julkaisuvuosi = julkaisuvuosikentta.getText();
         elokuva.kesto = kestokentta.getText();
         //elokuva.genre = genrekentta.getText();
         elokuva.getGenret().clear();
         for (String genre : genrekentta.getText().split(",")) {
         elokuva.getGenret().add(genre.trim());
         }

         StringBuilder teksti = new StringBuilder();
         teksti.append("Nimi: ").append(elokuva.nimi).append("\n")
         .append("Tallennustyyppi: ").append(elokuva.tallennustyyppi).append("\n")
         .append("Julkaisuvuosi: ").append(elokuva.julkaisuvuosi).append("\n")
         .append("Kesto: ").append(elokuva.kesto).append("\n")
         .append("Genret: ");
         for (String genre : elokuva.getGenret()) {
         teksti.append(genre);
         teksti.append(", ");
         }
         teksti.delete(teksti.length() - 2, teksti.length());

         detailkentta.setText(teksti.toString());
         paivitaNakyma();
         }
         //If you're here, the return value was null/empty.
         //setLabel("Come on, finish the sentence!");
         nimikentta.setText("");
         julkaisuvuosikentta.setText("");
         tallennustyyppikentta.setText("");
         kestokentta.setText("");
         genrekentta.setText("");
         }
         });
         */
        lisaysValinta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lisaysValinta.getSelectedItem();
                ((CardLayout) lisaaPaneeli.getLayout()).show(lisaaPaneeli, lisaysValinta.getSelectedItem().toString());
                System.out.println(lisaysValinta.getSelectedItem());
                lisaaPaneeli.removeAll();
                if (lisaysValinta.getSelectedItem().equals("Kategoria")) {
                    lisaaPaneeli.add(lisaaKategoriaKortti);
                }
                dialogiPaneeli.revalidate();
                dialogiPaneeli.repaint();
            }
        });

        //setPreferredSize(new Dimension(520, 250));
        setPreferredSize(new Dimension(498, 268));
        setLocation((dim.width - this.getSize().width) / 2, (dim.height - this.getSize().height) / 2);
        setResizable(false);
        pack();

        setVisible(true);
    }

    /*
     public void lisaaElokuva(Elokuva elokuva) {
     listmodel.addElement(elokuva);
     paivitaNakyma();
     }

     public void poistaElokuva(Elokuva elokuva) {
     listmodel.removeElement(elokuva);
     detailkentta.setText("");
     elokuvalista.clearSelection();
     elokuvalista.revalidate();
     paivitaNakyma();
     }
     */
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (!lse.getValueIsAdjusting() && drinkkilista.getSelectedIndex() != -1) {
            poistaDrinkkiPainike.setEnabled(true);
            muokkaaTietojaPainike.setEnabled(true);

            Drinkki drinkki = (Drinkki) listmodel.getElementAt(drinkkilista.getSelectedIndex());

            StringBuilder teksti = new StringBuilder();
            teksti.append(drinkki.getNimi()).append("\n");
            for (DrinkinAinesosat da : drinkki.getAinesosat()) {
                teksti.append("    ").append(da.getAinesosa().getNimi());
                teksti.append("\t").append(da.getMaara()).append("\n");
            }

            detailkentta.setText(teksti.toString());
            paivitaNakyma();
        } else if (drinkkilista.getSelectedIndex() == -1) {
            poistaDrinkkiPainike.setEnabled(false);
            muokkaaTietojaPainike.setEnabled(false);
        }
    }

    public void addDrinkki(Drinkki drinkki) {
        listmodel.addElement(drinkki);
    }

    public void rekisteroiOhjain(Ohjain ohjain) {
        this.ohjain = ohjain;
    }

    public void paivitaNakyma() {
        sisältöpaneeli.updateUI();
        revalidate();
        repaint();
    }

    //Menun action listener
    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());
        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new ExtensionFilter("XML file", ".xml");
        int retrival;
        switch (source.getActionCommand()) {
            case "FROM_XML":
                listmodel.clear();
                chooser = new JFileChooser();
                filter = new ExtensionFilter("XML file", ".xml");
                chooser.addChoosableFileFilter(filter);
                chooser.setFileFilter(filter);
                chooser.setCurrentDirectory(new File("./"));
                retrival = chooser.showOpenDialog(null);
                if (retrival == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    ohjain.parseXML(file.getAbsolutePath());
                }
                break;
            case "TO_XML":
                chooser.addChoosableFileFilter(filter);
                chooser.setFileFilter(filter);
                chooser.setCurrentDirectory(new File("./"));
                retrival = chooser.showSaveDialog(null);
                if (retrival == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    if (!file.getName().endsWith(".xml")) {
                        file = new File(file.toString() + ".xml");
                    }
                    try (FileWriter fw = new FileWriter(file)) {
                        fw.write(ohjain.toXML());
                    } catch (IOException ex) {
                    }
                }
                break;
            case "FROM_SQL":
                listmodel.clear();
                ohjain.loadFromSQL();
                break;
            case "TO_SQL":
                ohjain.toSQL();
                break;
            case "EXIT":
                System.exit(0);
                break;
        }
    }

    public void vaihdaLista(String actionCommand) {
        System.out.println(actionCommand);
    }
}
