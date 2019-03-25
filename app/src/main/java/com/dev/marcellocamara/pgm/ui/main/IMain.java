package com.dev.marcellocamara.pgm.ui.main;

import android.net.Uri;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IMain {

    interface Presenter {

        void OnRequestUserData();

        void OnLogout();

    }

    interface View {

        void OnRequestUserDataSuccessful(String name, String email);

        void OnSetUserImage(Uri uri);

        void OnLogoutSuccessful();

    }

    interface Model {

        String GetUserDisplayName();

        String GetUserEmail();

        Uri GetUserPhotoUri();

        void DoLogout();

    }

}