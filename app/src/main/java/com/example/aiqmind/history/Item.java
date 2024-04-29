package com.example.aiqmind.history;

import java.util.Date;

public class Item {
    private Date date;
    private String id;
    private String score;

    public Item() {
        // Constructeur vide requis pour Firestore
    }

    public Item( Date date, String id, String score) {
        this.date = date;
        this.id = id;
        this.score = score;
    }

    // Getters and setters

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
