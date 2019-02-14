package com.dev.marcellocamara.pgm.View;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dev.marcellocamara.pgm.Contract.IExpense;
import com.dev.marcellocamara.pgm.Presenter.ExpensePresenter;
import com.dev.marcellocamara.pgm.R;

import dmax.dialog.SpotsDialog;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ExpenseActivity extends AppCompatActivity implements IExpense.View, View.OnClickListener, AdapterView.OnItemSelectedListener{

    private IExpense.Presenter expensePresenter;
    private EditText title, description, price;
    private Toolbar toolbar;
    private Spinner spinner;
    private Button btnCancel, btnSave;
    private int parcels;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        ViewBind();

        expensePresenter = new ExpensePresenter(this, this);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(R.string.view_expense_customAlertDialog)
                .setCancelable(false)
                .build();
    }

    private void ViewBind() {

        title = findViewById(R.id.etTitle);
        description = findViewById(R.id.etDescription);
        price = findViewById(R.id.etPrice);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = findViewById(R.id.spinnerParcels);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_parcels,
                android.R.layout.simple_spinner_item
        );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        btnCancel = findViewById(R.id.buttonCancel);
        btnSave = findViewById(R.id.buttonSave);
        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonCancel : {
                finish();
                break;
            }
            case R.id.buttonSave : {
                expensePresenter.OnAddExpense(
                        title.getText().toString().trim(),
                        description.getText().toString().trim(),
                        price.getText().toString().trim(),
                        parcels
                );
                break;
            }
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parcels = Integer.parseInt(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public void OnInvalidField(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnAddExpenseSuccessful() {
        //TODO : AlertDialog with button to close activity
        Toast.makeText(this, "Sucesso.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnAddExpenseFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowProgress() {
        alertDialog.show();
    }

    @Override
    public void HideProgress() {
        alertDialog.show();
    }
}
