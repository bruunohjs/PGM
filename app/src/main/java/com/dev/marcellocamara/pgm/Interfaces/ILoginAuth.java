package com.dev.marcellocamara.pgm.Interfaces;

/***
    marcellocamara@id.uff.br
            2018
***/

public interface ILoginAuth {

    interface Presenter{

        void Login(String email, String password);

    }

    interface View{

        void OnEmptyEmail(String message);

        void OnEmptyPassword(String message);

        void OnInvalidEmail(String message);

        void OnInvalidPassword(String message);

        void OnLoginSuccessful(String message);

        void OnLoginFailure(String message);

    }

}