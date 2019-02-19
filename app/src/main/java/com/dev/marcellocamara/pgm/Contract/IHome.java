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

        void OnRequestUserName();

        void OnRequestExpenses(String monthYear);

        void OnTotalCalculate(List<ExpenseModel> list, String sum);

        void OnStop();

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnLogoutSuccessful();

        void OnRequestUserNameResult(String result);

        void OnRequestExpensesResult(List<ExpenseModel> list);

        void OnRequestTotalCalculateResult(String value);

    }

    interface Model {

        void DoLogout();

        String GetUserDisplayName();

        void DoRecoverExpenses(String monthYear);

        void RemoveEventListener();

    }

}
