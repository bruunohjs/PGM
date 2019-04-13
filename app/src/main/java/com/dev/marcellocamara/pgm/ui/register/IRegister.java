package com.dev.marcellocamara.pgm.ui.register;

import com.dev.marcellocamara.pgm.ui.IProgressLoading;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IRegister {

    interface Presenter{

        void OnRegisterRequest(String name, String email, String password1, String password2);

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnEmptyName();

        void OnEmptyEmail();

        void OnInvalidEmail();

        void OnEmptyPassword1();

        void OnEmptyPassword2();

        void OnInvalidPassword1();

        void OnInvalidPassword2();

        void OnMatchPasswords();

        void OnRegistrationSuccessful();

        void OnRegistrationFailure(String message);

        void OnInternetFailure();

    }

    interface Model{

        void DoRegister(String name, String email, String password);

    }

}