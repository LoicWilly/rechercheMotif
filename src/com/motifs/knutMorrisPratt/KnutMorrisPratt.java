package com.motifs.knutMorrisPratt;

import com.motifs.Motif;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nufer on 05.05.17.
 */
public class KnutMorrisPratt {

    private int[] tabPrefixes;
    private String text;
    private Motif M;
    private int occurrence;
    private List<Integer> positionsList;

    public KnutMorrisPratt(String pText,Motif pM)
    {
        this.text = pText;
        this.M = pM;
        this.occurrence = 0;
        this.positionsList = new ArrayList<Integer>();
        this.tabPrefixes = initTabPrefixes(pM.getTab());
    }

    public void search() {
        int i = 0;
        int j = 0;
        while (i < text.length()) {
            while (j >= 0 && text.charAt(i) != M.charAt(j)) {
                j = tabPrefixes[j];
            }
            i++;
            j++;
            if (j == M.getLength()) {
                occurrence++;
                positionsList.add(i - M.getLength());
                j = tabPrefixes[j];
            }
        }
    }

    private int[] initTabPrefixes(String str)
    {
        int size = str.length();
        int []pref = new int[size+1];
        int i = 0;
        int j = -1;
        pref[i] = j;
        while (i < M.getLength())
        {
            while (j >= 0 && str.charAt(i) != str.charAt(j))
            {
                j = pref[j];
            }
            i++;
            j++;
            pref[i] = j;
        }
        return pref;
    }

    public void displayTabPrefixes()
    {
        int size = tabPrefixes.length;
        for (int i = 0; i < size-1; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int j = 0; j < size-1; j++) {
            System.out.print(M.getTab().charAt(j) + " ");
        }
        System.out.println();

        for (int k = 0; k < size; k++) {
            if (tabPrefixes[k] != -1)
            {
                System.out.print(tabPrefixes[k] + " ");
            }

        }
        System.out.println();
    }

    public void displayKMP()
    {
        System.out.println(occurrence);
        for (Integer id : this.positionsList) {
            System.out.print(id + " ");
        }
        System.out.println();
    }
}
