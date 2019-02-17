package com.dev.marcellocamara.pgm.Model;

import android.os.Parcel;
import android.os.Parcelable;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ExpenseModel implements Parcelable {

    private String paymentDate;
    private String title, description, currentInstallment, installments;
    private double price;

    public ExpenseModel(){}

    protected ExpenseModel(Parcel in) {
        paymentDate = in.readString();
        title = in.readString();
        description = in.readString();
        currentInstallment = in.readString();
        installments = in.readString();
        price = in.readDouble();
    }

    public static final Creator<ExpenseModel> CREATOR = new Creator<ExpenseModel>() {
        @Override
        public ExpenseModel createFromParcel(Parcel in) {
            return new ExpenseModel(in);
        }

        @Override
        public ExpenseModel[] newArray(int size) {
            return new ExpenseModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(paymentDate);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(currentInstallment);
        dest.writeString(installments);
        dest.writeDouble(price);
    }
}