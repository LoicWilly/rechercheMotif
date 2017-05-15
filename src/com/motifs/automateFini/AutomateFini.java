package com.motifs.automateFini;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.print.DocFlavor.CHAR_ARRAY;

public class AutomateFini {

	final int NBALPHABET = 62; //62 comprend les maj, les min et les nombres de 0 � 9
	private int[][] tabEtats;
	private String motif;
	private int m;
	private char[] alphabet;
	private String texteEntier;
	private int nbOccurences = 0;
	private ArrayList<Integer> positions;
    private HashMap<Character,Integer> alphabetAssociation;

	// Constructeur
	public AutomateFini(String pMotif, String pTextEntier){
        this.alphabetAssociation = new HashMap<Character, Integer>();
        this.positions = new ArrayList<Integer>();
        this.motif = pMotif;
		this.m = pMotif.length();
		int i = 0;
		this.texteEntier = pTextEntier;

		// PARTIE initialisation de l'alphabet
				this.alphabet = new char[NBALPHABET];
				for(char alphabetMin = 'a'; alphabetMin <= 'z';alphabetMin++) {
					this.alphabet[i] = alphabetMin;
                    alphabetAssociation.put(alphabetMin,i);
					i++;
				}
				for(char alphabetMaj = 'A'; alphabetMaj <= 'Z';alphabetMaj++) {
					this.alphabet[i] = alphabetMaj;
                    alphabetAssociation.put(alphabetMaj,i);
					i++;
				}
				for(char alphabetNombre = '0'; alphabetNombre <= '9';alphabetNombre++) {
                    alphabetAssociation.put(alphabetNombre,i);
					this.alphabet[i] = alphabetNombre;
					i++;
				}
		this.tabEtats = initTabEtats();

        fonctionTransfert(0,this.tabEtats);
	}

	public int[][] initTabEtats(){
		char[] sequence = new char[m+1];
		int[][] Etats = new int[m+1][NBALPHABET];
//		alphabet = new char[3];
//		alphabet[0] = 'a';
//		alphabet[1] = 'b';
//		alphabet[2] = 'c';

		for (int i = 0; i <=  this.m ; i++) { // [62][4] donc ligne, colonne // pour chaque etat de notre DFA
			for (int j = 0; j < alphabet.length; j++) { // pour chaque lettre de notre alphabet
				sequence[i] = alphabet[j];
				Etats[i][j] = FindNextState(motif, sequence[i], i);
			}
		}
		finishLastState(Etats);
		return Etats;
	}

	private void finishLastState(int[][] pStates) {
		ArrayList<Integer> listIDs = new ArrayList<Integer>();
		int idSousMotif = -1;
		boolean allSame = true;
		boolean first = true;
		char firstLetter = motif.charAt(0);
		for (int i = 0; i < motif.length(); i++) {
			
			if(firstLetter == motif.charAt(i)){
				if(!first){
					listIDs.add(i);
				} else{
					first = false;
				}
			} else{
				allSame = false;
			}
		}
		if(allSame){
			// cas particulier ou tous le motif est le m�me symbole
			char multiSameLetter = motif.charAt(0);
			for (int i = 0; i < alphabet.length; i++) {
				
				if(multiSameLetter == alphabet[i]){
					pStates[motif.length()][i] = motif.length()-1;
				} else {
					pStates[motif.length()][i] = 0;
				}
			}
			return;
		} 	   
			//ababaca
			for (Integer id : listIDs) {
				for (int i = 0; i < motif.length(); i++) {
					if(motif.charAt(id) != motif.charAt(i)){
						break;
					}
					if(i == motif.length()-1-id){
						idSousMotif = id;
					}
				}
				if(idSousMotif != -1){
					break;
				}
			}
			if (idSousMotif != -1) {
				int sautMax = motif.length()-idSousMotif+1;
				int idNextLetterSM = motif.length() - idSousMotif;
				for (int i = 0; i < alphabet.length; i++) {
					if (motif.charAt(idNextLetterSM) == alphabet[i]) {
						pStates[motif.length()][i] = sautMax;
					}else if (motif.charAt(0) == motif.charAt(motif.length()-1) && motif.charAt(0) == alphabet[i] ) {
						pStates[motif.length()][i] = 1;
					}else {
						pStates[motif.length()][i] = 0;
					}
				}
			}
			else
			{
				if (motif.charAt(0) == motif.charAt(motif.length()-1)) {
					idSousMotif = motif.length()-1;
					for (int i = 0; i < alphabet.length; i++) {
						if (motif.charAt(idSousMotif)==alphabet[i]) {
							pStates[motif.length()][i] = 1;
						}else {
							pStates[motif.length()][i] = 0;
						}
					}
				}else {
					for (int i = 0; i < alphabet.length; i++) {
							pStates[i][motif.length()] = 0;
					}
				}
			}
	}

	public int FindNextState(String motif, Character character, int id) {
		if (id == motif.length() && motif.charAt(0) == character) { // si c'est le dernier caract�re de la cha�ne
			return 1; // si le 1er et le char entr� sont les m�mes
		} else {
			int idx = (character == motif.charAt(0)) ? (1) : (0); // on commence soit � 0 si l'input est faux ou � 1 si juste
			if (character == motif.charAt(id%m)) { // si juste alors on retourne le prochain �tat
				return (id + 1);
			} else { // si faux alors on remonte dans la table pour le placer au plus haut
				if (id > 0) {
					int i = id - 1;
					while (i > 0) {
						if (character == motif.charAt(i)) {
							int j = id - 1;
							idx = i + 1;
							i--;
							while (motif.charAt(j) == motif.charAt(i) && i > 0) {
								i--;
								j--;
							}
							if (motif.charAt(j) != motif.charAt(i)) {
								idx = (character == motif.charAt(0)) ? (1) : (0);
							}
						}
						if (i != 0) {
							idx = (character == motif.charAt(0)) ? (1) : (0);
						}
						i--;
					}
					return idx;
				} else {
					return idx;
				}
			}
		}
	}

	public void fonctionTransfert(int pEtatCourant, int[][] pTab){
		int q = pEtatCourant;
        int m = motif.length();
		for (int i = 0; i < texteEntier.length(); i++) {
            int n = alphabetAssociation.get(texteEntier.charAt(i));
			q = pTab[q][n];
            if (q == m)
            {
                nbOccurences++;
                positions.add(new Integer(i-m+1));
            }
		}
	}

	public void displayTableEtats() {
		if (this.tabEtats != null) {
            for (int k = 0; k < this.alphabet.length; k++) {
                System.out.print(" " + alphabet[k] + " ");
            }
            System.out.println();
            for (int i = 0; i <=  this.m ; i++) { // [62][4] donc ligne, colonne
				for (int j = 0; j < this.alphabet.length; j++) {
					System.out.print("[" + this.tabEtats[i][j] + "]");
				}
				System.out.println();
			}
		}
	}

	public void displayResults()
    {
        System.out.println(nbOccurences);
        for (Integer i : positions) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
