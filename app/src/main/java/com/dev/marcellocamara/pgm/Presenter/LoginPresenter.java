package com.dev.marcellocamara.pgm.Presenter;

import android.content.Context;
import android.util.Patterns;

import com.dev.marcellocamara.pgm.Contract.ILogin;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;
import com.dev.marcellocamara.pgm.R;

/***
    marcellocamara@id.uff.br
            2019
***/

public class LoginPresenter implements ILogin.Presenter, ITaskListener {

    private ILogin.View view;
    private ILogin.Model model;
    private Context context;

    public LoginPresenter(ILogin.View view, Context context) {
        this.view = view;
        this.model = new DatabaseModel(this);
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
    public void OnAlreadyLogged() {
        if (model.CheckLoggedIn()){
            view.OnLoginSuccessful();
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
            view.OnLoginSuccessful();
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