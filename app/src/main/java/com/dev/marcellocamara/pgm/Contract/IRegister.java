package com.dev.marcellocamara.pgm.Contract;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IRegister {

    interface Presenter{

        void OnRegister(String name, String email, String password1, String password2);

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

    }

    interface Model{

        void DoRegister(String name, String email, String password);

    }

}