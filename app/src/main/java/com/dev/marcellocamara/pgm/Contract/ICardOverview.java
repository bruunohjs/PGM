package com.dev.marcellocamara.pgm.Contract;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface ICardOverview {

    interface Presenter {

        void OnRequestUserData();

        void OnDestroy();

    }

    interface View {

        void OnRequestUserDataSuccessful(String name);

    }

    interface Model {

        String GetUserDisplayName();

    }

}