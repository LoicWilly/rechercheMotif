    package com.motifs.rabinKarp;
    import java.util.ArrayList;
    import java.util.HashMap;

    public class RabinKarp {

        private HashMap<Character, Integer> map;
        private String motif = "";
        private int motifHash; // hash du motif
        private int q; // modulo
        private int m;
        private int nbOccurences = 0;
        private int base;
        private ArrayList<Integer> positions;

        public RabinKarp(String pMotif){
            HashMap<Character, Integer> constMap = new HashMap<Character, Integer>();
            this.positions = new ArrayList<Integer>();
            this.motif = pMotif;
            this.m = motif.length();
            int i = 1;
            for(char alphabet = 'a'; alphabet <= 'z';alphabet++) {
                constMap.put(alphabet, i);
                i++;
            }
            for(char alphabetMaj = 'A'; alphabetMaj <= 'Z';alphabetMaj++) {
                constMap.put(alphabetMaj, i);
                i++;
            }
            for(char alphabetNombre = '0'; alphabetNombre <= '9';alphabetNombre++) {
                constMap.put(alphabetNombre, i);
                i++;
            }
            this.map = constMap;
            this.base = constMap.size();
            this.q = 3;
            this.motifHash = getHash(pMotif);
        }

        public int getHash(String pText){
            int somme = 0;
            int ajout = 0;
            for (int i = 0; i < pText.length()-(m-1); i++) {
                for (int j = 0; j < m; j++) {
                    ajout = (int) (this.map.get(pText.charAt(j))*Math.pow(q, j));
                    somme = somme + ajout;
                }
            }
            return somme;

        }

        public int shiftRight(String pText, int pHash, char nextLetter){
            int newValue;
            char temp = pText.charAt(0); // garde en m�moire le caract�re qui sort
            newValue = pHash - this.map.get(temp); // on retire le nombre qui sort du hash
            newValue = newValue/q;
            newValue = (int) (newValue + (this.map.get(nextLetter)*(Math.pow(q, m-1))));

            return newValue;
        }

        public void rabinKarpAlgorithme(String texteEntier) {
            String morceauTexte;
            int textHash;
            if(this.motif.length() <= texteEntier.length()){
                morceauTexte = texteEntier.substring(0, m);
                textHash = getHash(morceauTexte);
                if (textHash == this.motifHash) {
                    if (morceauTexte.equals(this.motif)) {
                        this.positions.add(0);
                        this.nbOccurences++;
                    }
                }
                for (int i = 1; i < texteEntier.length()-(m-1); i++) { // boucle pour la longueur du fichier texte
                    textHash = shiftRight(morceauTexte, textHash, texteEntier.charAt(m+(i-1)));
                    morceauTexte = texteEntier.substring(i, i+m);
                    if (textHash == this.motifHash) {
                        if (morceauTexte.equals(this.motif)) {
                            this.positions.add(i);
                            this.nbOccurences++;
                        }
                    }
                }
            }
            else{
                nbOccurences--;
            }
        }

        public void displayOptions(){
            System.out.println(this.base + " " + this.q + " " + this.motifHash);
        }

        public void displayResults() {
                System.out.println(this.nbOccurences);
                for (int i = 0; i < positions.size(); i++) {
                    System.out.print(this.positions.get(i) + " ");
                }
        }

    }
