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

        void OnInvalidName(String message);

        void OnInvalidEmail(String message);

        void OnInvalidPassword1(String message);

        void OnInvalidPassword2(String message);

        void OnRegistrationSuccessful(String message);

        void OnRegistrationFailure(String message);

    }

    interface Model{

        void DoRegister(String name, String email, String password);

    }

}