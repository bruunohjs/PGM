package com.dev.marcellocamara.pgm.ui.card_expenses;

import com.dev.marcellocamara.pgm.model.ExpenseModel;

import java.util.List;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface ICardExpenses {

    interface Presenter {

        void OnRequestExpenses(String monthYear, String cardUniqueId);

        void OnStop();

        void OnDestroy();

    }

    interface View {

        void OnRequestExpensesResult(List<ExpenseModel> expensesList);

        void OnRequestExpensesFailure(String message);

    }

    interface Model {

        List<ExpenseModel> DoRecoverExpenses(String monthYear);

        void RemoveExpensesEventListener();

    }

}