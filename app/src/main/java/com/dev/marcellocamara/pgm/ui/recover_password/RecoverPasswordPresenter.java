package com.dev.marcellocamara.pgm.ui.recover_password;

import android.util.Patterns;

import com.dev.marcellocamara.pgm.ui.ITaskListener;
import com.dev.marcellocamara.pgm.model.DatabaseModel;

import static com.dev.marcellocamara.pgm.utils.InternetConnection.hasInternet;

/***
    marcellocamara@id.uff.br
            2019
***/

public class RecoverPasswordPresenter implements IRecoverPassword.Presenter, ITaskListener {

    private IRecoverPassword.View view;
    private IRecoverPassword.Model model;

    public RecoverPasswordPresenter(IRecoverPassword.View view) {
        this.view = view;
        model = new DatabaseModel(this);
    }

    @Override
    public void OnRecoverPasswordRequest(String email) {

        if (email.isEmpty()){

            view.OnEmptyEmail();

        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            view.OnInvalidEmail();

        }else {
            if (hasInternet()){
                view.ShowProgress();
                model.DoRecoverPassword(email);
            }else {
                view.OnInternetFailure();
            }
        }
    }

    @Override
    public void OnSuccess() {
        if (view != null){
            view.HideProgress();
            view.OnRecoverPasswordSuccessful();
        }
    }

    @Override
    public void OnError(String message) {
        if (view != null){
            view.HideProgress();
            view.OnRecoverPasswordFailure(message);
        }
    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

}