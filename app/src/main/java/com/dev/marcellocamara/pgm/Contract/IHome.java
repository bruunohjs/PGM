package com.dev.marcellocamara.pgm.Contract;

import com.dev.marcellocamara.pgm.Model.CardModel;
import com.dev.marcellocamara.pgm.Model.ExpenseModel;

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

        void OnStop();

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnRequestExpensesResult(List<ExpenseModel> expensesList, ArrayList<CardModel> cardsList);

        void OnRequestTotalCalculateResult(String value);

    }

    interface Model {

        ArrayList<CardModel> DoRecoverCards();

        List<ExpenseModel> DoRecoverExpenses(String monthYear);

        void RemoveExpensesEventListener();

    }

}