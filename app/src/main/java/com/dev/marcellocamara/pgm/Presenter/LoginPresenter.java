package com.dev.marcellocamara.pgm.Presenter;

import android.content.Context;
import android.util.Patterns;

import com.dev.marcellocamara.pgm.Interfaces.ILogin;
import com.dev.marcellocamara.pgm.Model.LoginModel;
import com.dev.marcellocamara.pgm.R;

/***
    marcellocamara@id.uff.br
            2018
***/

public class LoginPresenter implements ILogin.Presenter, ILogin.TaskListener {

    private ILogin.View view;
    private ILogin.Model model;
    private Context context;

    public LoginPresenter(ILogin.View view, Context context) {
        this.view = view;
        this.model = new LoginModel(this);
        this.context = context;
    }

    @Override
    public void OnLogin(String email, String password) {

        if (email.isEmpty() || password.isEmpty()){

            if (email.isEmpty()){
                view.OnInvalidEmail(context.getString(R.string.presenter_login_empty_email));
            }else {
                view.OnInvalidPassword(context.getString(R.string.presenter_login_empty_password));
            }

        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            view.OnInvalidEmail(context.getString(R.string.presenter_login_invalid_email));

        }else if (password.length() < 6){

            view.OnInvalidPassword(context.getString(R.string.presenter_login_invalid_password));

        }else {
            view.ShowProgress();
            model.DoLogin(email, password);
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
            view.OnLoginSuccessful(context.getString(R.string.presenter_login_successful));
        }
    }

    @Override
    public void OnError(String message) {
        if (view != null){
            view.HideProgress();
            view.OnLoginFailure(message);
        }
    }
}