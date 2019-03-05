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

    private ILogin.Presenter loginPresenter;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        loginPresenter = new LoginPresenter(this, this);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(R.string.view_login_customAlertDialog)
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
    public void OnInvalidEmail(String message) {
        layoutEmail.setError(message);
        layoutEmail.setErrorEnabled(true);
    }

    @Override
    public void OnInvalidPassword(String message) {
        layoutPassword.setError(message);
        layoutPassword.setErrorEnabled(true);
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
    public void OnLoginSuccessful() {
        startActivity(new Intent (this, MainActivity.class));
        finish();
    }

    @Override
    public void OnLoginFailure(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.view_login_alertDialog_title);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.view_expense_alertDialog_positive_button, null);
        builder.show();
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