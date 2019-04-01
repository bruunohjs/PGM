package com.dev.marcellocamara.pgm.ui.contact;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IContact {

    interface Presenter {

        void OnRequestUserData();

        void OnSendMessage(String defaultSubject, String subject, String message);

        void OnDestroy();

    }

    interface View {

        void OnRequestUserDataSuccessful(String name);

        void OnInvalidSubject();

        void OnEmptyMessage();

        void DoSendMessage(String subject, String text);

    }

    interface Model {

        String GetUserDisplayName();

    }

}