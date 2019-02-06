package com.dev.marcellocamara.pgm.View;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dev.marcellocamara.pgm.Interfaces.ILoginAuth;
import com.dev.marcellocamara.pgm.Presenter.LoginPresenter;
import com.dev.marcellocamara.pgm.R;

/***
    marcellocamara@id.uff.br
            2018
***/

public class LoginActivity extends AppCompatActivity implements ILoginAuth.View, View.OnClickListener {

    private LoginPresenter loginPresenter;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private TextInputEditText editTextEmail, editTextPassword;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginPresenter(this, this);

        ViewBind();
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
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                loginPresenter.Login(email, password);
                break;
            }
            case R.id.btnRegister : {
                //TODO: Open new Activity - RegisterActivity
                break;
            }
        }

    }

    @Override
    public void OnEmptyEmail(String message) {
        inputLayoutEmail.setError(message);
        inputLayoutEmail.setErrorEnabled(true);
    }

    @Override
    public void OnEmptyPassword(String message) {
        inputLayoutPassword.setError(message);
        inputLayoutPassword.setErrorEnabled(true);
    }

    @Override
    public void OnInvalidEmail(String message) {
        inputLayoutEmail.setError(message);
        inputLayoutEmail.setErrorEnabled(true);
    }

    @Override
    public void OnInvalidPassword(String message){
        inputLayoutPassword.setError(message);
        inputLayoutPassword.setErrorEnabled(true);
    }

    @Override
    public void OnLoginSuccessful(String message) {
        //TODO: Open new Activity - MainActivity
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnLoginFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}