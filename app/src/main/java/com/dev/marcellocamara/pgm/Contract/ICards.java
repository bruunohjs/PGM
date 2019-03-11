package com.dev.marcellocamara.pgm.Contract;

import com.dev.marcellocamara.pgm.Model.CardModel;

import java.util.List;

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

        void OnRequestExpensesResult(List<CardModel> list);

    }

    interface Model {

        String GetUserDisplayName();

        List<CardModel> DoRequestCards();

        void RemoveEventListener();

    }

}