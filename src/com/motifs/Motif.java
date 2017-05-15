package com.motifs;

/**
 * Created by nufer on 05.05.17.
 */
public class Motif {
    private String tab;
    private int position;
    private int length;

    public Motif(String str)
    {
        this.tab = new String(str);
        this.position = 0;
        this.length = tab.length();
    }

    public char charAt(int i)
    {
        return  this.tab.charAt(i);
    }
    public String getTab() {
        return tab;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getLength() {
        return length;
    }
}
