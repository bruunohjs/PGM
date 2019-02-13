package com.dev.marcellocamara.pgm.Presenter;

import android.content.Context;
import android.util.Patterns;

import com.dev.marcellocamara.pgm.Contract.IRegister;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;
import com.dev.marcellocamara.pgm.R;

/***
    marcellocamara@id.uff.br
            2019
***/

public class RegisterPresenter implements IRegister.Presenter, ITaskListener {

    private IRegister.View view;
    private IRegister.Model model;
    private Context context;

    public RegisterPresenter(IRegister.View view, Context context) {
        this.view = view;
        this.model = new DatabaseModel(this);
        this.context = context;
    }

    @Override
    public void OnRegister(String name, String email, String password1, String password2) {

        if (name.isEmpty() || email.isEmpty() || password1.isEmpty() || password2.isEmpty()){

            if (name.isEmpty()){
                view.OnInvalidName(context.getString(R.string.presenter_register_name));
            } else if (email.isEmpty()){
                view.OnInvalidEmail(context.getString(R.string.presenter_login_empty_email));
            }else if (password1.isEmpty()){
                view.OnInvalidPassword1(context.getString(R.string.presenter_login_empty_password));
            }else {
                view.OnInvalidPassword2(context.getString(R.string.presenter_login_empty_password));
            }

        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            view.OnInvalidEmail(context.getString(R.string.presenter_login_invalid_email));

        }else if ((password1.length() < 6) || (password2.length() < 6)){

            if (password1.length() < 6){
                view.OnInvalidPassword1(context.getString(R.string.presenter_login_invalid_password));
            }else {
                view.OnInvalidPassword2(context.getString(R.string.presenter_login_invalid_password));
            }

        }else if (!password1.equals(password2)){
            view.OnInvalidPassword2(context.getString(R.string.presenter_register_passwords_not_match));
        }else {
            view.ShowProgress();
            model.DoRegister(name, email, password1);
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
            view.OnRegistrationSuccessful(context.getString(R.string.presenter_register_successful));
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