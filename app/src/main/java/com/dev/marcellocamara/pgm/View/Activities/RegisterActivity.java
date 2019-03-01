package com.dev.marcellocamara.pgm.View.Activities;

import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dev.marcellocamara.pgm.Contract.IRegister;
import com.dev.marcellocamara.pgm.Presenter.RegisterPresenter;
import com.dev.marcellocamara.pgm.R;

import dmax.dialog.SpotsDialog;

/***
    marcellocamara@id.uff.br
            2019
***/

public class RegisterActivity extends AppCompatActivity implements IRegister.View, View.OnClickListener{

    private IRegister.Presenter registerPresenter;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword1, inputLayoutPassword2;
    private TextInputEditText editTextName, editTextEmail, editTextPassword1, editTextPassword2;
    private Button btnRegister, btnBackToLogin;
    private AlertDialog alertDialog, alert;
    private AlertDialog.Builder builder;

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

        builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.view_register_alertDialog_title);
        builder.setCancelable(false);
    }

    private void ViewBind() {

        inputLayoutName = findViewById(R.id.tilName);
        inputLayoutEmail = findViewById(R.id.layoutEmail);
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
        builder.setMessage(message);
        builder.setPositiveButton(R.string.view_expense_alertDialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert = builder.create();
        alert.show();
    }

    @Override
    public void OnRegistrationFailure(String message) {
        builder.setMessage(message);
        builder.setPositiveButton(R.string.view_expense_alertDialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.dismiss();
            }
        });
        alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy() {
        registerPresenter.OnDestroy();
        super.onDestroy();
    }
}