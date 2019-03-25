package com.dev.marcellocamara.pgm.ui.home;

import com.dev.marcellocamara.pgm.ui.IProgressLoading;
import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.model.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IHome {

    interface Presenter {

        void OnRequestExpenses(String monthYear);

        void OnTotalCalculate(List<ExpenseModel> list, String sum);

        void OnCheckUserCards(ArrayList<CardModel> cards);

        void OnStop();

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnRequestExpensesResult(List<ExpenseModel> expensesList, ArrayList<CardModel> cards);

        void AllowAddNewExpense();

        void DenyAddNewExpense();

        void OnRequestTotalCalculateResult(String value);

    }

    interface Model {

        ArrayList<CardModel> DoRecoverCards();

        List<ExpenseModel> DoRecoverExpenses(String monthYear);

        void RemoveExpensesEventListener();

    }

}