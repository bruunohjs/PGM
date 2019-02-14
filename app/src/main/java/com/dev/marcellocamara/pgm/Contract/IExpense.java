package com.dev.marcellocamara.pgm.Contract;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IExpense {

    interface Presenter {

        void OnAddExpense(String title, String description, String price, int parcel);

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnInvalidField(String message);

        void OnAddExpenseSuccessful();

        void OnAddExpenseFailure(String message);

    }

    interface Model {

        void DoAddExpense();

    }

}
