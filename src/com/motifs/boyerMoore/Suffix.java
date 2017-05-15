package com.motifs.boyerMoore;

import com.motifs.Motif;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nufer on 06.05.17.
 */
public class Suffix {

    private int [] s;
    private int [] f;
    private HashMap<Integer,String> strSuffix;
    private List<Association> list;


    public Suffix(Motif pM)
    {
        strSuffix = new HashMap<Integer, String>();
        list = new ArrayList<Association>();
        suffixOccurs(pM);
        partOfSuffixOccurs(pM);
    }

    private String getStrSuffix(Motif pM, int id)
    {
        String str = "";
        for (int i = id; i < pM.getLength(); i++) {
            str += pM.charAt(i);
        }
        return new String(str);
    }
    private void suffixOccurs(Motif pM)
    {
        Motif p = pM;
        int m = pM.getLength(); //m = la taille du motif
        int i=m ;
        int j=m+1;
        //s[i] contains the shift distance of the pattern if a mismatch at position i – 1 occurs
        this.s = new int[m+1];

        // f[i]contains the starting position of the widest border of the suffix of the pattern beginning at position i.
        this.f = new int[m+1];
        f[i]=j;

        while (i>0)
        {
            while (j<=m && p.charAt(i-1) != p.charAt(j-1))
            {
                if (s[j]==0)
                {
                    s[j]=j-i;
                    String str = getStrSuffix(pM,j);
                    if (!str.isEmpty())
                    {
                        this.list.add(new Association(i,str));
                    }
                }
                j=f[j];
            }
            i--;
            j--;
            f[i]=j;
        }
    }

    void partOfSuffixOccurs(Motif pM)
    {
        Motif p = pM;
        int m = pM.getLength(); //m = la taille du motif
        int j=f[0];

        for (int i=0; i<=m; i++)
        {
            if (s[i]==0)
            {
                s[i]=j;
                String str = getStrSuffix(pM,i);
                this.list.add(new Association(j,str));
            }
            if (i==j)
            {
                j=f[j];
            }
        }
    }

    public void displaySuffixTab()
    {
        for (Association a:list) {
            System.out.print(a.sMotif + " ");
        }
        System.out.println();
        for (Association a:list) {

            String str = "";
            for (int i = 0; i < a.sMotif.length(); i++) {
                str += " ";
            }
            System.out.print(a.val + str);
        }
        System.out.println();
    }

    public int[] getS() {
        return s;
    }

    public int[] getF() {
        return f;
    }


    /*
    private void suffixCalculation(Motif pM)
    {
        int iprec = 0;
        int index = motifLength -1;
        this.suffixes[motifLength-1] = motifLength;

        for (int i = motifLength-2; i >= 0; i--) {
            if (i > index && this.suffixes[i + motifLength-1 -iprec] < i-index)
            {
                suffixes[i] = suffixes[i+motifLength-1-iprec];
            }
            else
            {
                if (i < index)
                {
                    index = i;
                }
                iprec = i;
                while (index >= 0 && pM.charAt(index) ==  pM.charAt(index + motifLength - 1 - iprec))
                {
                    index--;
                }
                suffixes[i] = iprec - index;
            }
        }
    }

    private void suffixTableCalculation(Motif pM)
    {
        suffixCalculation(pM);

        for (int i = motifLength-1; i >= 0; i--) {
            this.suffixesTab[i] = motifLength;
        }
        for (int i = motifLength-1; i >= -1; i--) {
            if (i == -1 || this.suffixes[i] == i + 1)
            {
                for (int j = 0; j < motifLength-1-i; j++) {
                    if (this.suffixesTab[j] == motifLength)
                    {
                        this.suffixesTab[j] = motifLength-1-i;
                    }
                }
            }
        }
        for (int i = 0; i <= motifLength-2; i++) {
            this.suffixesTab[motifLength-1-this.suffixes[i]] = motifLength-1-i;
        }
    }
*/

}
