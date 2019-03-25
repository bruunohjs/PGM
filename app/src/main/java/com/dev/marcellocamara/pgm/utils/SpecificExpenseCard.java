package com.dev.marcellocamara.pgm.utils;

import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.model.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

/***
    marcellocamara@id.uff.br
            2019
***/

public class SpecificExpenseCard {

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

    /**
     * Searches for the unique key of the credit card in the Expenses List
     * @param expenseList - All expenses for a month/year given
     * @param uniqueId - unique key of the credit card
     * @return expenses objects (Expenses List) of the credit card unique key or null if it does not find
     */
    public static List<ExpenseModel> getExpensesList(List<ExpenseModel> expenseList, String uniqueId) {
        List<ExpenseModel> specificExpenses = new ArrayList<>();
        for ( ExpenseModel expense : expenseList ) {
            if (expense.getCreditCard().equals(uniqueId)){
                specificExpenses.add(expense);
            }
        }
        return specificExpenses;
    }
}