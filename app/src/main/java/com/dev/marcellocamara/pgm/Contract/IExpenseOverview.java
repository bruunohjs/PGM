package com.dev.marcellocamara.pgm.Contract;

import com.dev.marcellocamara.pgm.Model.ExpenseModel;

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

    }

    interface Model {

        void DoDeleteExpense(String date, int installments, String uniqueId);

    }

}