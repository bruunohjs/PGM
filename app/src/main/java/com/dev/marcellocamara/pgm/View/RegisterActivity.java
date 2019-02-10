package com.dev.marcellocamara.pgm.View;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dev.marcellocamara.pgm.Contract.IRegister;
import com.dev.marcellocamara.pgm.Presenter.RegisterPresenter;
import com.dev.marcellocamara.pgm.R;

import dmax.dialog.SpotsDialog;

/***
    marcellocamara@id.uff.br
            2018
***/

public class RegisterActivity extends AppCompatActivity implements IRegister.View, View.OnClickListener{

    IRegister.Presenter registerPresenter;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword1, inputLayoutPassword2;
    private TextInputEditText editTextName, editTextEmail, editTextPassword1, editTextPassword2;
    private Button btnRegister, btnBackToLogin;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ViewBind();

        registerPresenter = new RegisterPresenter(this, this);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(R.string.view_register_customAlertDialog)
                .setCancelable(false)
                .build();
    }

    private void ViewBind() {

        inputLayoutName = findViewById(R.id.tilName);
        inputLayoutEmail = findViewById(R.id.tilEmail);
        inputLayoutPassword1 = findViewById(R.id.tilPassword1);
        inputLayoutPassword2 = findViewById(R.id.tilPassword2);

        editTextName = findViewById(R.id.etName);
        editTextEmail = findViewById(R.id.etEmail);
        editTextPassword1 = findViewById(R.id.etPassword1);
        editTextPassword2 = findViewById(R.id.etPassword2);

        btnRegister = findViewById(R.id.btnRegister);
        btnBackToLogin = findViewById(R.id.btnBacktoLogin);
        btnRegister.setOnClickListener(this);
        btnBackToLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister : {
                inputLayoutName.setErrorEnabled(false);
                inputLayoutEmail.setErrorEnabled(false);
                inputLayoutPassword1.setErrorEnabled(false);
                inputLayoutPassword2.setErrorEnabled(false);
                registerPresenter.OnRegister(
                        editTextName.getText().toString().trim(),
                        editTextEmail.getText().toString().trim(),
                        editTextPassword1.getText().toString().trim(),
                        editTextPassword2.getText().toString().trim()
                );
                break;
            }
            case R.id.btnBacktoLogin : {
                finish();
                break;
            }
        }
    }

    @Override
    public void OnInvalidName(String message) {
        inputLayoutName.setError(message);
        inputLayoutName.setErrorEnabled(true);
    }

    @Override
    public void OnInvalidEmail(String message) {
        inputLayoutEmail.setError(message);
        inputLayoutEmail.setErrorEnabled(true);
    }

    @Override
    public void OnInvalidPassword1(String message) {
        inputLayoutPassword1.setError(message);
        inputLayoutPassword1.setErrorEnabled(true);
    }

    @Override
    public void OnInvalidPassword2(String message) {
        inputLayoutPassword2.setError(message);
        inputLayoutPassword2.setErrorEnabled(true);
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
    public void OnRegistrationSuccessful(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnRegistrationFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        registerPresenter.OnDestroy();
        super.onDestroy();
    }
}