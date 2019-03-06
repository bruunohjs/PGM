package com.dev.marcellocamara.pgm.View.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.dev.marcellocamara.pgm.Contract.ILogin;
import com.dev.marcellocamara.pgm.Presenter.LoginPresenter;
import com.dev.marcellocamara.pgm.R;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import dmax.dialog.SpotsDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindString;
import butterknife.OnClick;

/***
    marcellocamara@id.uff.br
            2019
***/

public class LoginActivity extends AppCompatActivity implements ILogin.View {

    @BindView(R.id.layoutEmail) protected TextInputLayout layoutEmail;
    @BindView(R.id.layoutPassword) protected TextInputLayout layoutPassword;

    @BindView(R.id.editTextEmail) protected TextInputEditText editTextEmail;
    @BindView(R.id.editTextPassword) protected TextInputEditText editTextPassword;

    @BindView(R.id.btnLogin) protected Button btnLogin;
    @BindView(R.id.btnRegister) protected Button btnRegister;
    @BindView(R.id.btnRecoverPassword) protected Button btnRecoverPassword;

    @BindString(R.string.login) protected String login;
    @BindString(R.string.empty_email) protected String empty_email;
    @BindString(R.string.invalid_email) protected String invalid_email;
    @BindString(R.string.empty_password) protected String empty_password;
    @BindString(R.string.invalid_password) protected String invalid_password;
    @BindString(R.string.close) protected String close;
    @BindString(R.string.login_in) protected String login_in;

    private ILogin.Presenter loginPresenter;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        loginPresenter = new LoginPresenter(this);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(login_in)
                .setCancelable(false)
                .build();
    }

    @OnClick({R.id.btnLogin, R.id.btnRegister, R.id.btnRecoverPassword})
    public void OnButtonClick(Button v){
        switch (v.getId()){
            case R.id.btnLogin : {
                layoutEmail.setErrorEnabled(false);
                layoutPassword.setErrorEnabled(false);
                loginPresenter.OnLogin(
                        editTextEmail.getText().toString().trim(),
                        editTextPassword.getText().toString().trim()
                );
                UIUtil.hideKeyboard(this);
                break;
            }
            case R.id.btnRegister : {
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            }
            case R.id.btnRecoverPassword : {
                startActivity(new Intent(this, RecoverPasswordActivity.class));
                break;
            }
        }
    }

    @Override
    public void OnEmptyEmail() {
        layoutEmail.setError(empty_email);
        layoutEmail.setErrorEnabled(true);
    }

    @Override
    public void OnInvalidEmail() {
        layoutEmail.setError(invalid_email);
        layoutEmail.setErrorEnabled(true);
    }

    @Override
    public void OnEmptyPassword() {
        layoutPassword.setError(empty_password);
        layoutPassword.setErrorEnabled(true);
    }

    @Override
    public void OnInvalidPassword() {
        layoutPassword.setError(invalid_password);
        layoutPassword.setErrorEnabled(true);
    }

    @Override
    public void OnLoginSuccessful() {
        startActivity(new Intent (this, MainActivity.class));
        finish();
    }

    @Override
    public void OnLoginFailure(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(login);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void ShowProgress() {
        alertDialog.show();
    }

    @Override
    public void HideProgress() {
        alertDialog.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginPresenter.OnAlreadyLogged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.OnDestroy();
    }
}