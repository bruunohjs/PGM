package com.dev.marcellocamara.pgm.ui.register;

import android.util.Patterns;

import com.dev.marcellocamara.pgm.ui.ITaskListener;
import com.dev.marcellocamara.pgm.model.DatabaseModel;

import static com.dev.marcellocamara.pgm.utils.InternetConnection.hasInternet;

/***
    marcellocamara@id.uff.br
            2019
***/

public class RegisterPresenter implements IRegister.Presenter, ITaskListener {

    private IRegister.View view;
    private IRegister.Model model;

    public RegisterPresenter(IRegister.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnRegisterRequest(String name, String email, String password1, String password2) {

        if (name.isEmpty() || email.isEmpty() || password1.isEmpty() || password2.isEmpty()){

            if (name.isEmpty()){
                view.OnEmptyName();
            } else if (email.isEmpty()){
                view.OnEmptyEmail();
            }else if (password1.isEmpty()){
                view.OnEmptyPassword1();
            }else {
                view.OnEmptyPassword2();
            }

        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            view.OnInvalidEmail();

        }else if ((password1.length() < 6) || (password2.length() < 6)){

            if (password1.length() < 6){
                view.OnInvalidPassword1();
            }else {
                view.OnInvalidPassword2();
            }

        }else if (!password1.equals(password2)){

            view.OnMatchPasswords();

        }else {
            if (hasInternet()){
                view.ShowProgress();
                model.DoRegister(name, email, password1);
            }else {
                view.OnInternetFailure();
            }
        }

    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

    @Override
    public void OnSuccess() {
        if (view != null){
            view.HideProgress();
            view.OnRegistrationSuccessful();
        }
    }

    @Override
    public void OnError(String message) {
        if (view != null){
            view.HideProgress();
            view.OnRegistrationFailure(message);
        }
    }

}