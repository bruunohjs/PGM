package com.dev.marcellocamara.pgm.ui.login;

import com.dev.marcellocamara.pgm.ui.IProgressLoading;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface ILogin {

    interface Presenter{

        void OnLogin(String email, String password);

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

    }

    interface Model{

        void DoLogin(String email, String password);

        boolean CheckLoggedIn();

    }

}