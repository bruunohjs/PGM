package com.dev.marcellocamara.pgm.ui.recover_password;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dev.marcellocamara.pgm.ui.IProgressLoading;
import com.dev.marcellocamara.pgm.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import dmax.dialog.SpotsDialog;

import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
    marcellocamara@id.uff.br
            2019
***/

public class RecoverPasswordActivity extends AppCompatActivity implements IRecoverPassword.View, IProgressLoading {

    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @BindView(R.id.layoutEmail) protected TextInputLayout layoutEmail;
    @BindView(R.id.editTextEmail) protected TextInputEditText editTextEmail;

    @BindView(R.id.adView) protected AdView adView;

    @BindString(R.string.recover_password) protected String recover_password;
    @BindString(R.string.recovering_password) protected String recovering;
    @BindString(R.string.empty_email) protected String empty_email;
    @BindString(R.string.invalid_email) protected String invalid_email;
    @BindString(R.string.close) protected String close;
    @BindString(R.string.recover_message1) protected String message1;
    @BindString(R.string.recover_message2) protected String message2;
    @BindString(R.string.no_internet) protected String no_internet;

    private IRecoverPassword.Presenter presenter;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(recover_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new RecoverPasswordPresenter(this);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(recovering)
                .setCancelable(false)
                .build();

        builder = new AlertDialog.Builder(this);
        builder.setTitle(recover_password);
        builder.setCancelable(false);

        adView.loadAd(new AdRequest.Builder().build());
    }

    @OnClick(R.id.btnRecoverPassword)
    public void OnButtonClick(){
        layoutEmail.setErrorEnabled(false);
        layoutEmail.clearFocus();
        presenter.OnRecoverPasswordRequest(editTextEmail.getText().toString().trim());
        UIUtil.hideKeyboard(this);
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
    public void OnRecoverPasswordSuccessful() {
        String message = message1 + editTextEmail.getText().toString().trim() + message2;
        builder.setMessage(message);
        builder.setPositiveButton(close, new DialogInterface.OnClickListener() {
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
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnInternetFailure() {
        builder.setMessage(no_internet);
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