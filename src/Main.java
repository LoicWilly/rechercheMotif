import com.motifs.*;
import com.motifs.FileReader;
import com.motifs.automateFini.AutomateFini;
import com.motifs.boyerMoore.BoyerMoore;
import com.motifs.rabinKarp.RabinKarp;
import com.motifs.knutMorrisPratt.KnutMorrisPratt;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException, NumberFormatException {
        // Ne pas modifier cette partie
        String fileName = null;
        String motif = null;
        int algo = 0;
        switch (args.length) {
            case 3:
                fileName = args[2];
            case 2:
                algo = Integer.parseInt(args[1]);
                motif = args[0];
                break;
            default:
                System.err.println("usage: java Main <motif> <algo> (<fichier_texte>)");
                System.exit(1);
        }

        String text = FileReader.getText(fileName);
        Motif M = new Motif(motif);

        // Rien d'autre ne doit etre affiche que ce qui est indique ci-dessous
        switch (algo) {
            case 1: //Rabin-Karp
                // Format de sortie -> à générer avec votre code
                RabinKarp rabinKarp = new RabinKarp(motif);
                if (fileName == null) {
                    rabinKarp.displayOptions();
                } else {
                    rabinKarp.rabinKarpAlgorithme(text);
                    rabinKarp.displayResults();
                }
                break;
            case 2: //Automate fini
                AutomateFini automateFini = new AutomateFini(motif,text);

                if (fileName == null) {
                    automateFini.displayTableEtats();

                } else {
                   automateFini.displayResults();
                }
                break;
            case 3: //Knut-Morris-Pratt
                KnutMorrisPratt knutMorrisPratt = new KnutMorrisPratt(text, new Motif(motif));

                if (fileName == null) {
                    knutMorrisPratt.displayTabPrefixes();
                } else {
                    knutMorrisPratt.search();
                    knutMorrisPratt.displayKMP();
                }
                break;
            case 4: //Boyer-Moore
                BoyerMoore boyerMoore = new BoyerMoore(new Motif(motif));

                if (fileName == null) {
                    boyerMoore.displayTabs();
                } else {
                    boyerMoore.theUgly(text,M);
                    boyerMoore.displayResult();
                }
                break;
            default:
                System.err.println("Algorithm not implemented");
                System.exit(2);
        }
    }
}