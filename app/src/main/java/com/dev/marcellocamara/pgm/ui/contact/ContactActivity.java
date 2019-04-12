package com.dev.marcellocamara.pgm.ui.contact;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dev.marcellocamara.pgm.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ContactActivity extends AppCompatActivity implements IContact.View, AdapterView.OnItemSelectedListener {

    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @BindView(R.id.editTextMessage) protected TextInputEditText editTextMessage;

    @BindView(R.id.spinnerSubject) protected Spinner spinner;

    @BindView(R.id.adView) protected AdView adView;

    @BindString(R.string.contact) protected String contact;
    @BindString(R.string.close) protected String close;
    @BindString(R.string.email_client) protected String email_client;
    @BindString(R.string.email_to) protected String email_to;
    @BindString(R.string.yes) protected String yes;
    @BindString(R.string.no) protected String no;
    @BindString(R.string.sent_email_message) protected String sent_email_message;

    private IContact.Presenter presenter;
    private String defaultSubject, subject, userName;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ButterKnife.bind(this);

        presenter = new ContactPresenter(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.subjects, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        String subjectsArray[] = getResources().getStringArray(R.array.subjects);
        defaultSubject = subjectsArray[0];

        builder = new AlertDialog.Builder(this);
        builder.setTitle(contact);
        builder.setCancelable(false);

        adView.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @OnClick(R.id.btnSendMessage)
    public void OnButtonSendMessageClick(){
        presenter.OnSendMessage(
                defaultSubject,
                subject,
                editTextMessage.getText().toString().trim()
        );
        UIUtil.hideKeyboard(this);
    }

    @Override
    public void OnRequestUserDataSuccessful(String name) {
        userName = name;
    }

    @Override
    public void OnInvalidSubject() {
        builder.setMessage(R.string.invalid_subject);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnEmptyMessage() {
        builder.setMessage(R.string.empty_message);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void DoSendMessage(String subject, String text) {
        String[] recipient = {email_to};
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipient);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, email_client));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        subject = spinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    @Override
    protected void onRestart() {
        super.onRestart();
        builder.setMessage(sent_email_message);
        builder.setPositiveButton(yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(no, null);
        builder.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.OnRequestUserData();
    }

}
