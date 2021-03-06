package com.dev.marcellocamara.pgm.ui.login;

import android.util.Patterns;

import com.dev.marcellocamara.pgm.ui.ITaskListener;
import com.dev.marcellocamara.pgm.model.DatabaseModel;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import static com.dev.marcellocamara.pgm.utils.InternetConnection.hasInternet;

/***
    marcellocamara@id.uff.br
            2019
***/

public class LoginPresenter implements ILogin.Presenter, ITaskListener {

    private ILogin.View view;
    private ILogin.Model model;

    public LoginPresenter(ILogin.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnLoginRequest(String email, String password) {

        if (email.isEmpty() || password.isEmpty()){

            if (email.isEmpty()){
                view.OnEmptyEmail();
            }else {
                view.OnEmptyPassword();
            }

        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            view.OnInvalidEmail();

        }else if (password.length() < 6){

            view.OnInvalidPassword();

        }else {
            if (hasInternet()){
                view.ShowProgress();
                model.DoLogin(email, password);
            }else {
                view.OnInternetFailure();
            }
        }
    }

    @Override
    public void OnLoginRequest(GoogleSignInResult result) {
        if (hasInternet()){
            if (result.isSuccess()){
                view.ShowProgress();
                GoogleSignInAccount account = result.getSignInAccount();
                model.DoLogin(account);
            }else {
                view.OnLoginWithGoogleFailure();
            }
        }else {
            view.OnInternetFailure();
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