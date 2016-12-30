package com.example.leandro.dictionnaire.Models;

/**
 * Created by Leandro on 29/12/16.
 */

public class Woord {

    private String woord;
    private String vertaling;
    private int likes;
    private String studentnaam;
    private String studentID;

    public Woord(){

    }

    public Woord(String woord, String vertaling, int likes, String studentnaam) {
        this.woord = woord;
        this.vertaling = vertaling;
        this.likes = likes;
        this.studentnaam = studentnaam;
    }

    public String getWoord() {
        return woord;
    }

    public void setWoord(String woord) {
        this.woord = woord;
    }

    public String getVertaling() {
        return vertaling;
    }

    public void setVertaling(String vertaling) {
        this.vertaling = vertaling;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getStudentnaam() { return studentnaam; }

    public void setStudentnaam(String studentnaam) { this.studentnaam = studentnaam; }

    public String getStudentID() { return studentID; }

    public void setStudentID(String studentID) { this.studentID = studentID; }
}
