package com.dev.marcellocamara.pgm.Contract;

import com.dev.marcellocamara.pgm.Model.CardModel;

import java.util.ArrayList;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface ICards {

    interface Presenter {

        void OnRequestUserData();

        void OnRequestCards();

        void OnStop();

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnRequestUserDataSuccessful(String name);

        void OnRequestCardsResult(ArrayList<CardModel> list);

    }

    interface Model {

        String GetUserDisplayName();

        ArrayList<CardModel> DoRecoverCards();

        void RemoveCardsEventListener();

    }

}