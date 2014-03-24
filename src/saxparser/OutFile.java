package saxparser;

import java.io.*;

public class OutFile {
  private File outFile;
  private PrintWriter outF;

    public void openFile() throws IOException {
      if (outFile == null)
        System.out.println("Tiedostonimi ei ole tiedossa.");
      else
        outF = new PrintWriter(new FileOutputStream(outFile), true); //näin avattua tiedostoa ei tarvitse sulkea
    }

    public void write(String row) {
      if (outF == null)
        System.out.println("Virhe tiedoston kirjoituksessa.");
      else
        outF.println(row); // kirjoitetaan rivi
    }
}
