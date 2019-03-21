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

        void OnStop();

        void OnDestroy();

    }

    interface View {

        void OnRequestUserDataSuccessful(String name);

        void OnRequestCardSuccessful(CardModel card);

        void OnRequestTotalCalculateResult(String value);

    }

    interface Model {

        String GetUserDisplayName();

        ArrayList<CardModel> DoRecoverCards();

        List<ExpenseModel> DoRecoverExpenses(String monthYear);

        void RemoveCardsEventListener();

        void RemoveExpensesEventListener();

    }

}