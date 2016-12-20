package com.example.leandro.parleur;

/**
 * Created by Leandro on 20/12/16.
 */

public class Word {
    private String woord, vertaling;

    public Word() {

    }

    public Word(String woord, String vertaling) {
        this.woord = woord;
        this.vertaling = vertaling;
    }

    public void setWoord(String woord) {this.woord = woord;}
    public String getWoord() {return woord;}
    public void setVertaling(String vertaling) {this.vertaling = vertaling;}
    public String getVertaling() {return vertaling;}
}
