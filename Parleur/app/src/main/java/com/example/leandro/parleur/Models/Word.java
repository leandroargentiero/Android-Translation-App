package com.example.leandro.parleur.Models;

/**
 * Created by Leandro on 20/12/16.
 */

// JAVA OBJECT
public class Word {
    private String woord, vertaling;
    private int stemmen;

    public Word() {

    }

    public Word(String woord, String vertaling, int stemmen) {
        this.woord = woord;
        this.vertaling = vertaling;
        this.stemmen = stemmen;
    }

    public void setWoord(String woord) {this.woord = woord;}
    public String getWoord() {return woord;}
    public void setVertaling(String vertaling) {this.vertaling = vertaling;}
    public String getVertaling() {return vertaling;}
    public void setStemmen(int stemmen) {this.stemmen = stemmen;}
    public int getStemmen() {return stemmen;}
}
