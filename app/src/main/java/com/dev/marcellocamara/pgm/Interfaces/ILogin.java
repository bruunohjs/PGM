package com.dev.marcellocamara.pgm.Interfaces;

/***
    marcellocamara@id.uff.br
            2018
***/

public interface ILogin {

    interface Presenter{

        void OnLogin(String email, String password);

        void OnDestroy();

    }

    interface View{

        void OnInvalidEmail(String message);

        void OnInvalidPassword(String message);

        void ShowProgress();

        void HideProgress();

        void OnLoginSuccessful(String message);

        void OnLoginFailure(String message);

    }

    interface Model{

        void DoLogin(String email, String password);

    }

    interface TaskListener{

        void OnSuccess();

        void OnError(String message);

    }

}