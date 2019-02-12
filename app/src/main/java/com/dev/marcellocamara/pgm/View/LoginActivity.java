package com.dev.marcellocamara.pgm.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dev.marcellocamara.pgm.Contract.ILogin;
import com.dev.marcellocamara.pgm.Presenter.LoginPresenter;
import com.dev.marcellocamara.pgm.R;

import dmax.dialog.SpotsDialog;

/***
    marcellocamara@id.uff.br
            2019
***/

public class LoginActivity extends AppCompatActivity implements ILogin.View, View.OnClickListener {

    private ILogin.Presenter loginPresenter;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private TextInputEditText editTextEmail, editTextPassword;
    private Button btnLogin, btnRegister;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ViewBind();

        loginPresenter = new LoginPresenter(this, this);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(R.string.view_login_customAlertDialog)
                .setCancelable(false)
                .build();
    }

    private void ViewBind() {

        inputLayoutEmail = findViewById(R.id.tilEmail);
        inputLayoutPassword = findViewById(R.id.tilPassword);

        editTextEmail = findViewById(R.id.etEmail);
        editTextPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnLogin : {
                inputLayoutEmail.setErrorEnabled(false);
                inputLayoutPassword.setErrorEnabled(false);
                loginPresenter.OnLogin(
                        editTextEmail.getText().toString().trim(),
                        editTextPassword.getText().toString().trim()
                );
                break;
            }
            case R.id.btnRegister : {
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            }
        }

    }

    @Override
    public void OnInvalidEmail(String message) {
        inputLayoutEmail.setError(message);
        inputLayoutEmail.setErrorEnabled(true);
    }

    @Override
    public void OnInvalidPassword(String message) {
        inputLayoutPassword.setError(message);
        inputLayoutPassword.setErrorEnabled(true);
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
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void OnLoginFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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