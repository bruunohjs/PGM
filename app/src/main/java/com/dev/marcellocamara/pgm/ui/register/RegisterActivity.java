package com.dev.marcellocamara.pgm.ui.register;

import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dev.marcellocamara.pgm.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
    marcellocamara@id.uff.br
            2019
***/

public class RegisterActivity extends AppCompatActivity implements IRegister.View {

    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @BindView(R.id.layoutName) protected TextInputLayout layoutName;
    @BindView(R.id.layoutEmail) protected TextInputLayout layoutEmail;
    @BindView(R.id.layoutPassword1) protected TextInputLayout layoutPassword1;
    @BindView(R.id.layoutPassword2) protected TextInputLayout layoutPassword2;

    @BindView(R.id.editTextName) protected TextInputEditText editTextName;
    @BindView(R.id.editTextEmail) protected TextInputEditText editTextEmail;
    @BindView(R.id.editTextPassword1) protected TextInputEditText editTextPassword1;
    @BindView(R.id.editTextPassword2) protected TextInputEditText editTextPassword2;

    @BindView(R.id.adView) protected AdView adView;

    @BindString(R.string.register) protected String register;
    @BindString(R.string.empty_name) protected String empty_name;
    @BindString(R.string.empty_email) protected String empty_email;
    @BindString(R.string.invalid_email) protected String invalid_email;
    @BindString(R.string.empty_password) protected String empty_password;
    @BindString(R.string.invalid_password) protected String invalid_password;
    @BindString(R.string.match_password) protected String match_password;
    @BindString(R.string.close) protected String close;
    @BindString(R.string.registering) protected String registering;
    @BindString(R.string.register_success) protected String register_success;

    private IRegister.Presenter registerPresenter;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        registerPresenter = new RegisterPresenter(this);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(registering)
                .setCancelable(false)
                .build();

        builder = new AlertDialog.Builder(this);
        builder.setTitle(register);
        builder.setCancelable(false);

        adView.loadAd(new AdRequest.Builder().build());
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
    public void OnEmptyName() {
        layoutName.setError(empty_name);
        layoutName.setErrorEnabled(true);
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
    public void OnEmptyPassword1() {
        layoutPassword1.setError(empty_password);
        layoutPassword1.setErrorEnabled(true);
    }

    @Override
    public void OnEmptyPassword2() {
        layoutPassword2.setError(empty_password);
        layoutPassword2.setErrorEnabled(true);
    }

    @Override
    public void OnInvalidPassword1() {
        layoutPassword1.setError(invalid_password);
        layoutPassword1.setErrorEnabled(true);
    }

    @Override
    public void OnInvalidPassword2() {
        layoutPassword2.setError(invalid_password);
        layoutPassword2.setErrorEnabled(true);
    }

    @Override
    public void OnMatchPasswords() {
        layoutPassword2.setError(match_password);
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
    public void OnRegistrationSuccessful() {
        builder.setMessage(register_success);
        builder.setPositiveButton(close, new DialogInterface.OnClickListener() {
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
        builder.setPositiveButton(close, null);
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