package com.dev.marcellocamara.pgm.ui.recover_password;

import com.dev.marcellocamara.pgm.ui.IProgressLoading;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IRecoverPassword {

    interface Presenter {

        void OnRecoverPasswordRequest(String email);

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnEmptyEmail();

        void OnInvalidEmail();

        void OnRecoverPasswordSuccessful();

        void OnRecoverPasswordFailure(String message);

        void OnInternetFailure();

    }

    interface Model {

        void DoRecoverPassword(String email);

    }

}