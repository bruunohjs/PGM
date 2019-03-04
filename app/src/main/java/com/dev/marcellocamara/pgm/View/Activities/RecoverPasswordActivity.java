package com.dev.marcellocamara.pgm.View.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.dev.marcellocamara.pgm.Contract.IProgressLoading;
import com.dev.marcellocamara.pgm.Contract.IRecoverPassword;
import com.dev.marcellocamara.pgm.Presenter.RecoverPasswordPresenter;
import com.dev.marcellocamara.pgm.R;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

/***
    marcellocamara@id.uff.br
            2019
***/

public class RecoverPasswordActivity extends AppCompatActivity implements IRecoverPassword.View, IProgressLoading, View.OnClickListener {

    private IRecoverPassword.Presenter presenter;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private TextInputLayout layoutEmail;
    private TextInputEditText editTextEmail;
    private Button buttonRecoverPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        ViewBind();

        presenter = new RecoverPasswordPresenter(this);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(R.string.view_recover_password_dialog_loading)
                .setCancelable(false)
                .build();

        builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.view_recover_button);
        builder.setCancelable(false);
    }

    private void ViewBind() {

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.view_recover_button);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutEmail = findViewById(R.id.layoutEmail);
        editTextEmail = findViewById(R.id.editTextEmail);
        
        buttonRecoverPassword = findViewById(R.id.btnRecoverPassword);
        buttonRecoverPassword.setOnClickListener(this);

    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRecoverPassword : {
                layoutEmail.setErrorEnabled(false);
                layoutEmail.clearFocus();
                presenter.OnRecoverPassword(editTextEmail.getText().toString().trim());
                UIUtil.hideKeyboard(this);
                break;
            }
        }
    }

    @Override
    public void OnEmptyEmail() {
        layoutEmail.setError(getResources().getString(R.string.presenter_login_empty_email));
        layoutEmail.setErrorEnabled(true);
    }

    @Override
    public void OnInvalidEmail() {
        layoutEmail.setError(getResources().getString(R.string.presenter_login_invalid_email));
        layoutEmail.setErrorEnabled(true);
    }

    @Override
    public void OnRecoverPasswordSuccessful() {
        String message = getString(R.string.view_recover_password_dialog_message1) + " " + editTextEmail.getText().toString().trim() +
                getString(R.string.view_recover_password_dialog_message2);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.view_expense_alertDialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void OnRecoverPasswordFailure(String message) {
        builder.setMessage(message);
        builder.setPositiveButton(R.string.view_overview_dialog_close, null);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.OnDestroy();
    }
}