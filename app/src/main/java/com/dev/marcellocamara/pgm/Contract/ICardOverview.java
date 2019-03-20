package com.dev.marcellocamara.pgm.Contract;

import com.dev.marcellocamara.pgm.Model.CardModel;

import java.util.ArrayList;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface ICardOverview {

    interface Presenter {

        void OnRequestUserData();

        void OnRequestCard(String uniqueId);

        void OnDestroy();

    }

    interface View {

        void OnRequestUserDataSuccessful(String name);

        void OnRequestCardSuccessful(CardModel card);

    }

    interface Model {

        String GetUserDisplayName();

        ArrayList<CardModel> DoRecoverCards();

    }

}