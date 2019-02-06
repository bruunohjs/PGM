package com.dev.marcellocamara.pgm.Presenter;

import android.content.Context;
import android.util.Patterns;

import com.dev.marcellocamara.pgm.Interfaces.ILoginAuth;
import com.dev.marcellocamara.pgm.R;

/***
    marcellocamara@id.uff.br
            2018
***/

public class LoginPresenter implements ILoginAuth.Presenter {

    private ILoginAuth.View view;
    private Context context;

    public LoginPresenter(ILoginAuth.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void Login(String email, String password) {

        if (email.isEmpty() || password.isEmpty()){

            if (email.isEmpty()){
                view.OnEmptyEmail(context.getString(R.string.presenter_login_empty_email));
            }else {
                view.OnEmptyPassword(context.getString(R.string.presenter_login_empty_password));
            }

        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            view.OnInvalidEmail(context.getString(R.string.presenter_login_invalid_email));

        }else if (password.length() < 6){
            view.OnInvalidPassword(context.getString(R.string.presenter_login_invalid_password));
        }else {
            view.OnLoginSuccessful(context.getString(R.string.presenter_login_successful));
        }

    }

}