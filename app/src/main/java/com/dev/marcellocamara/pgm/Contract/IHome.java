package com.dev.marcellocamara.pgm.Contract;

import com.dev.marcellocamara.pgm.Model.ExpenseModel;

import java.util.List;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IHome {

    interface Presenter {

        void OnLogout();

        void OnRequestExpenses(String monthYear);

        void OnTotalCalculate(List<ExpenseModel> list);

        void OnStop();

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnLogoutSuccessful();

        void OnRequestExpensesResult(List<ExpenseModel> list);

        void OnRequestTotalCalculateResult(String value);

    }

    interface Model {

        void DoLogout();

        void DoRecoverExpenses(String monthYear);

        void RemoveEventListener();

    }

}
