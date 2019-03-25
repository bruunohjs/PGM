package com.dev.marcellocamara.pgm.ui.recover_password;

import com.dev.marcellocamara.pgm.ui.IProgressLoading;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IRecoverPassword {

    interface Presenter {

        void OnRecoverPassword(String email);

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnEmptyEmail();

        void OnInvalidEmail();

        void OnRecoverPasswordSuccessful();

        void OnRecoverPasswordFailure(String message);

    }

    interface Model {

        void DoRecoverPassword(String email);

    }

}