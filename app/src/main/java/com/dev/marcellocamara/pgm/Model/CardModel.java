package com.dev.marcellocamara.pgm.Model;

public class CardModel {

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

    public String getUniqueId() {
        return uniqueId;
    }

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
}