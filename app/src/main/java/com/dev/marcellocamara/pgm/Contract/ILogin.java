package com.dev.marcellocamara.pgm.Contract;

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

        void OnInvalidEmail(String message);

        void OnInvalidPassword(String message);

        void OnLoginSuccessful();

        void OnLoginFailure(String message);

    }

    interface Model{

        void DoLogin(String email, String password);

        boolean CheckLoggedIn();

    }

}