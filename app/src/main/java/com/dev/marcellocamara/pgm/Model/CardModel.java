package com.dev.marcellocamara.pgm.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

/***
    marcellocamara@id.uff.br
            2019
***/

public class CardModel implements Parcelable {

    private String cardTitle, finalDigits, betterDayToBuy, annuityNegotiationMonth, uniqueId;
    private int cardColor, cardFlag;

    public CardModel (){}

    public CardModel(String cardTitle, String finalDigits, String paymentDay, String annuityNegotiationMonth, int color, int flag) {
        this.cardTitle = cardTitle;
        this.finalDigits = finalDigits;
        this.betterDayToBuy = paymentDay;
        this.annuityNegotiationMonth = annuityNegotiationMonth;
        this.cardColor = color;
        this.cardFlag = flag;
    }

    protected CardModel(Parcel in) {
        cardTitle = in.readString();
        finalDigits = in.readString();
        betterDayToBuy = in.readString();
        annuityNegotiationMonth = in.readString();
        uniqueId = in.readString();
        cardColor = in.readInt();
        cardFlag = in.readInt();
    }

    public static final Creator<CardModel> CREATOR = new Creator<CardModel>() {
        @Override
        public CardModel createFromParcel(Parcel in) {
            return new CardModel(in);
        }

        @Override
        public CardModel[] newArray(int size) {
            return new CardModel[size];
        }
    };

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getFinalDigits() {
        return finalDigits;
    }

    public void setFinalDigits(String finalDigits) {
        this.finalDigits = finalDigits;
    }

    public String getBetterDayToBuy() {
        return betterDayToBuy;
    }

    public void setBetterDayToBuy(String betterDayToBuy) {
        this.betterDayToBuy = betterDayToBuy;
    }

    public String getAnnuityNegotiationMonth() {
        return annuityNegotiationMonth;
    }

    public void setAnnuityNegotiationMonth(String annuityNegotiationMonth) {
        this.annuityNegotiationMonth = annuityNegotiationMonth;
    }

    @Exclude
    public String getUniqueId() {
        return uniqueId;
    }

    @Exclude
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getCardColor() {
        return cardColor;
    }

    public void setCardColor(int cardColor) {
        this.cardColor = cardColor;
    }

    public int getCardFlag() {
        return cardFlag;
    }

    public void setCardFlag(int cardFlag) {
        this.cardFlag = cardFlag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cardTitle);
        dest.writeString(finalDigits);
        dest.writeString(betterDayToBuy);
        dest.writeString(annuityNegotiationMonth);
        dest.writeString(uniqueId);
        dest.writeInt(cardColor);
        dest.writeInt(cardFlag);
    }
}