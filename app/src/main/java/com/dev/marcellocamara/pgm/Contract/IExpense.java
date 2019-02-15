package com.dev.marcellocamara.pgm.Contract;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IExpense {

    interface Presenter {

        void OnAddExpense(String date, String title, String description, String price, int installments);

        void OnCalculateDate(int day, int month, int year);

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnInvalidField(String message);

        void OnCalculatedDate(String day, String month, String year);

        void OnAddExpenseSuccessful();

        void OnAddExpenseFailure(String message);

    }

    interface Model {

        void DoAddExpense(String date, String title, String description, double price, int installments);

    }

}
