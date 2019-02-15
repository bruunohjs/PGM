package com.dev.marcellocamara.pgm.Model;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ExpenseModel {

    private String date;
    private String title, description;
    private double price;
    private int parcels;

    public ExpenseModel(String date, String title, String description, double price, int parcels) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.price = price;
        this.parcels = parcels;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getParcels() {
        return parcels;
    }

    public void setParcels(int parcels) {
        this.parcels = parcels;
    }
}