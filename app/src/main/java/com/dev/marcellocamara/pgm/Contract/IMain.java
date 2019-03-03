package com.dev.marcellocamara.pgm.Contract;

import android.net.Uri;

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