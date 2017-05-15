package com.motifs.boyerMoore;

import com.motifs.Motif;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nufer on 06.05.17.
 */
public class BoyerMoore {

    Jump theBad;
    Suffix theGood;
    List<Integer> id;
    int occurence;

    public BoyerMoore(Motif pM)
    {
        init(pM);
    }

    void init(Motif pM)
    {
        this.theBad = new Jump(pM);
        this.theGood = new Suffix(pM);
        this.id = new ArrayList<Integer>();
        this.occurence = 0;
    }

    public void theUgly(String pText,Motif pM)
    {
        int m = pM.getLength();
        int s = m;
        int t = pText.length();

        int j;

        while (s <= t)
        {
            j = m;
            while (j > 0 && pText.charAt(s-m+j-1) == pM.charAt(j-1))
            {
                j--;
            }

            if(j == 0)
            {
                this.id.add(new Integer(s-m));
                occurence++;
            }

            if(j == m)
            {
                s = s + this.theBad.badCharacter(pText.charAt(s-1));
            }
            else
            {
                s = s + this.theGood.getS()[m-j];
            }
        }
    }

    public void displayTabs()
    {
        this.theBad.displayLetterJump();
        this.theGood.displaySuffixTab();
    }

    public void displayResult()
    {
        System.out.println(this.occurence);
        for (Integer i : this.id) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
