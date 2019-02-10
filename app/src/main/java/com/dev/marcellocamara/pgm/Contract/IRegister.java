package com.dev.marcellocamara.pgm.Contract;

/***
    marcellocamara@id.uff.br
            2018
***/

public interface IRegister {

    interface Presenter{

        void OnRegister(String name, String email, String password);

        void OnDestroy();

    }

    interface View{

        void OnInvalidName(String message);

        void OnInvalidEmail(String message);

        void OnInvalidPassword(String message);

        void ShowProgress();

        void HideProgress();

        void OnRegistrationSuccessful(String message);

        void OnRegistrationFailure(String message);

    }

    interface Model{

        void DoRegister(String name, String email, String password);

    }

    interface TaskListener{

        void OnSuccess();

        void OnError(String message);

    }

}