package com.dev.marcellocamara.pgm.Presenter;

import android.util.Patterns;

import com.dev.marcellocamara.pgm.Contract.IRecoverPassword;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;

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
    public void OnRecoverPassword(String email) {

        if (email.isEmpty()){

            view.OnEmptyEmail();

        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            view.OnInvalidEmail();

        }else {

            view.ShowProgress();
            model.DoRecoverPassword(email);

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