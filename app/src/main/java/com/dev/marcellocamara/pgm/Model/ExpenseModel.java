package com.dev.marcellocamara.pgm.Model;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ExpenseModel {

    private String paymentDate;
    private String title, description, currentInstallment, installments;
    private double price;

    public ExpenseModel(){}

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
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

    public String getCurrentInstallment() {
        return currentInstallment;
    }

    public void setCurrentInstallment(String currentInstallment) {
        this.currentInstallment = currentInstallment;
    }

    public String getInstallments() {
        return installments;
    }

    public void setInstallments(String installments) {
        this.installments = installments;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}