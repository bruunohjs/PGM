package com.dev.marcellocamara.pgm.ui.cards;

import com.dev.marcellocamara.pgm.ui.IProgressLoading;
import com.dev.marcellocamara.pgm.model.CardModel;

import java.util.ArrayList;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface ICards {

    interface Presenter {

        void OnRequestUserData();

        void OnRequestCards();

        void OnCountMaxCards(ArrayList<CardModel> cards);

        void OnStop();

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnRequestUserDataSuccessful(String name);

        void OnRequestCardsResult(ArrayList<CardModel> list);

        void AllowAddNewCard();

        void DenyAddNewCard();

    }

    interface Model {

        String GetUserDisplayName();

        ArrayList<CardModel> DoRecoverCards();

        void RemoveCardsEventListener();

    }

}