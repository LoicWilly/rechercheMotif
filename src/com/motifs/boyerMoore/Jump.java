package com.motifs.boyerMoore;

import com.motifs.Motif;

import java.util.*;

/**
 * Created by nufer on 06.05.17.
 */
public class Jump {

    LinkedHashMap<Character,Integer> relationLetterJump;
    private int size;

    public Jump(Motif pM)
    {
        this.relationLetterJump = new LinkedHashMap<Character, Integer>();
        this.size = pM.getLength();
        init(pM);
    }

    private void init(Motif pM)
    {
        char sym = '\0';
        int d = 0;

        for (int i = this.size-1; i >= 0; i--) {

            sym = pM.charAt(i);
            if (!this.relationLetterJump.containsKey(sym))
            {
                this.relationLetterJump.put(sym,d);
            }
            d++;
        }
        this.relationLetterJump.put('*',this.size);
    }

    public int badCharacter(char c)
    {
        if (containLetter(c))
        {
          return getJump(c);
        }
        return getJump('*');
    }

    public int getJump(char c)
    {
        return this.relationLetterJump.get(c);
    }

    public boolean containLetter(char c)
    {
        return (this.relationLetterJump.containsKey(c));
    }

    public void displayLetterJump() {

        for (Character key : this.relationLetterJump.keySet()) {
            System.out.print(key + " ");
        }
        System.out.println();
        for (Character key : this.relationLetterJump.keySet()) {
            System.out.print(this.relationLetterJump.get(key) + " ");
        }
        System.out.println();
    }
}
