package com.dev.marcellocamara.pgm.View.Activities;

import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.dev.marcellocamara.pgm.Contract.IRegister;
import com.dev.marcellocamara.pgm.Presenter.RegisterPresenter;
import com.dev.marcellocamara.pgm.R;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
    marcellocamara@id.uff.br
            2019
***/

public class RegisterActivity extends AppCompatActivity implements IRegister.View {

    @BindView(R.id.layoutName) protected TextInputLayout layoutName;
    @BindView(R.id.layoutEmail) protected TextInputLayout layoutEmail;
    @BindView(R.id.layoutPassword1) protected TextInputLayout layoutPassword1;
    @BindView(R.id.layoutPassword2) protected TextInputLayout layoutPassword2;

    @BindView(R.id.editTextName) protected TextInputEditText editTextName;
    @BindView(R.id.editTextEmail) protected TextInputEditText editTextEmail;
    @BindView(R.id.editTextPassword1) protected TextInputEditText editTextPassword1;
    @BindView(R.id.editTextPassword2) protected TextInputEditText editTextPassword2;

    @BindView(R.id.btnRegister) protected Button btnRegister;

    private IRegister.Presenter registerPresenter;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.view_register_alertDialog_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

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

    @OnClick(R.id.btnRegister)
    public void OnButtonClick(){
        layoutName.setErrorEnabled(false);
        layoutEmail.setErrorEnabled(false);
        layoutPassword1.setErrorEnabled(false);
        layoutPassword2.setErrorEnabled(false);
        registerPresenter.OnRegister(
                editTextName.getText().toString().trim(),
                editTextEmail.getText().toString().trim(),
                editTextPassword1.getText().toString().trim(),
                editTextPassword2.getText().toString().trim()
        );
        UIUtil.hideKeyboard(this);
    }

    @Override
    public void OnInvalidName(String message) {
        layoutName.setError(message);
        layoutName.setErrorEnabled(true);
    }

    @Override
    public void OnInvalidEmail(String message) {
        layoutEmail.setError(message);
        layoutEmail.setErrorEnabled(true);
    }

    @Override
    public void OnInvalidPassword1(String message) {
        layoutPassword1.setError(message);
        layoutPassword1.setErrorEnabled(true);
    }

    @Override
    public void OnInvalidPassword2(String message) {
        layoutPassword2.setError(message);
        layoutPassword2.setErrorEnabled(true);
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
        builder.show();
    }

    @Override
    public void OnRegistrationFailure(String message) {
        builder.setMessage(message);
        builder.setPositiveButton(R.string.view_expense_alertDialog_positive_button, null);
        builder.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        registerPresenter.OnDestroy();
        super.onDestroy();
    }
}