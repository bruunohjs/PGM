package com.dev.marcellocamara.pgm.ui.expense_overview;

import com.dev.marcellocamara.pgm.ui.IProgressLoading;
import com.dev.marcellocamara.pgm.model.ExpenseModel;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IExpenseOverview {

    interface Presenter{

        void OnVerifyInstallments(String installments, double price);

        void OnDeleteExpense(ExpenseModel expenseModel);

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnSetInstallments(String eachInstallment);

        void OnDeleteExpenseSuccess();

        void OnDeleteExpenseFailure(String message);

        void OnInternetFailure();

    }

    interface Model {

        void DoDeleteExpense(ExpenseModel expense);

    }

}