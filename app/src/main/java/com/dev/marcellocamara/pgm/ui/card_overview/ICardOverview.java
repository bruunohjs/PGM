package com.dev.marcellocamara.pgm.ui.card_overview;

import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.model.ExpenseModel;
import com.dev.marcellocamara.pgm.ui.IProgressLoading;

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

        ArrayList<String> GetCardsNumbers();

        void OnRequestDeleteCard();

        void OnStop();

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnRequestUserDataSuccessful(String name);

        void OnRequestCardSuccessful(ArrayList<CardModel> card);

        void OnRequestTotalCalculateResult(String value);

        void OnAllowViewExpenses();

        void OnDenyViewExpenses();

        void OnRequestDeleteCardSuccessful();

        void OnRequestCardExpensesFailure(String message);

    }

    interface Model {

        String GetUserDisplayName();

        ArrayList<CardModel> DoRecoverCards();

        List<ExpenseModel> DoRecoverExpenses(String monthYear);

        void DoDeleteCard(String uniqueId);

        void RemoveCardsEventListener();

        void RemoveExpensesEventListener();

    }

}