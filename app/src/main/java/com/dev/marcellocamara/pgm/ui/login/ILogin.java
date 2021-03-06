package com.dev.marcellocamara.pgm.ui.login;

import com.dev.marcellocamara.pgm.ui.IProgressLoading;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface ILogin {

    interface Presenter{

        void OnLoginRequest(String email, String password);

        void OnLoginRequest(GoogleSignInResult result);

        void OnDestroy();

        void OnAlreadyLogged();

    }

    interface View extends IProgressLoading {

        void OnEmptyEmail();

        void OnInvalidEmail();

        void OnEmptyPassword();

        void OnInvalidPassword();

        void OnLoginSuccessful();

        void OnLoginFailure(String message);

        void OnLoginWithGoogleFailure();

        void OnInternetFailure();

    }

    interface Model{

        void DoLogin(String email, String password);

        void DoLogin(GoogleSignInAccount account);

        boolean CheckLoggedIn();

    }

}