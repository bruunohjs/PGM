package com.dev.marcellocamara.pgm.ui.contact;

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

    @BindView(R.id.editTextEmail) protected TextInputEditText editTextEmail;
    @BindView(R.id.editTextMessage) protected TextInputEditText editTextMessage;

    @BindView(R.id.spinnerSubject) protected Spinner spinner;

    @BindString(R.string.contact) protected String contact;
    @BindString(R.string.close) protected String close;

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
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @OnClick(R.id.btnSendMessage)
    public void OnButtonSendMessageClick(){
        presenter.OnSendMessage(
                userName,
                editTextEmail.getText().toString(),
                defaultSubject,
                subject,
                editTextMessage.getText().toString().trim()
        );
    }

    @Override
    public void OnRequestUserDataSuccessful(String name, String email) {
        userName = name;
        editTextEmail.setText(email);
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
    public void DoSendMessage() {
        //TODO : Send email allowed
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        subject = spinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.OnRequestUserData();
    }

}
