package com.dev.marcellocamara.pgm.ui.contact;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IContact {

    interface Presenter {

        void OnRequestUserData();

        void OnSendMessage(String name, String email, String defaultSubject, String subject, String message);

        void OnDestroy();

    }

    interface View {

        void OnRequestUserDataSuccessful(String name, String email);

        void OnInvalidSubject();

        void OnEmptyMessage();

        void DoSendMessage();

    }

    interface Model {

        String GetUserDisplayName();

        String GetUserEmail();

    }

}