package com.dev.marcellocamara.pgm.Helper;

import com.dev.marcellocamara.pgm.Model.CardModel;

import java.util.ArrayList;

/***
    marcellocamara@id.uff.br
            2019
***/

public class SpecificCard {

    /**
     * Searches for the unique key of the credit card in ArrayList of credit cards
     * @param creditCard - credit card unique key string
     * @param cards - ArrayList of credit cards CardModel
     * @return credit card CardModel object or null if it does not find
     */
    public static CardModel getCard(String creditCard, ArrayList<CardModel> cards) {
        for ( CardModel card : cards ) {
            if (card.getUniqueId().equals(creditCard)){
                return card;
            }
        }
        return null;
    }

}