package com.dev.marcellocamara.pgm.Contract;

import com.dev.marcellocamara.pgm.Model.CardModel;
import com.dev.marcellocamara.pgm.Model.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface ICardOverview {

    interface Presenter {

        void OnRequestUserData();

        void OnRequestCardExpenses(String uniqueId, String monthYear);

        void OnTotalCalculate(List<ExpenseModel> list);

        void OnCheckExpenses(String price);

        void OnStop();

        void OnDestroy();

    }

    interface View {

        void OnRequestUserDataSuccessful(String name);

        void OnRequestCardSuccessful(ArrayList<CardModel> card);

        void OnRequestTotalCalculateResult(String value);

        void OnAllowViewExpenses();

        void OnDenyViewExpenses();

        void OnRequestCardExpensesFailure(String message);

    }

    interface Model {

        String GetUserDisplayName();

        ArrayList<CardModel> DoRecoverCards();

        List<ExpenseModel> DoRecoverExpenses(String monthYear);

        void RemoveCardsEventListener();

        void RemoveExpensesEventListener();

    }

}