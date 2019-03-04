package com.dev.marcellocamara.pgm.View.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.Contract.IExpense;
import com.dev.marcellocamara.pgm.Presenter.ExpensePresenter;
import com.dev.marcellocamara.pgm.R;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.Calendar;

import dmax.dialog.SpotsDialog;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ExpenseActivity extends AppCompatActivity implements IExpense.View, View.OnClickListener {

    private IExpense.Presenter expensePresenter;
    private EditText title, description, price;
    private TextView textViewDate, textInstallments;
    private Button btnCancel, btnSave;
    private int installments = 1, calendarDay, calendarMonth, calendarYear;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder, builderInstallments ;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        ViewBind();

        expensePresenter = new ExpensePresenter(this, this);

        calendar = Calendar.getInstance();
        calendarDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendarMonth = calendar.get(Calendar.MONTH);
        calendarYear = calendar.get(Calendar.YEAR);
        expensePresenter.OnCalculateDate(calendarDay,calendarMonth,calendarYear);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(R.string.view_expense_customAlertDialog)
                .setCancelable(false)
                .build();

        builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.view_expense_title_expense);
        builder.setCancelable(false);
    }

    private void ViewBind() {

        textViewDate = findViewById(R.id.tvDate);
        textViewDate.setOnClickListener(this);
        textInstallments = findViewById(R.id.tvInstallment);
        textInstallments.setOnClickListener(this);
        title = findViewById(R.id.etTitle);
        description = findViewById(R.id.etDescription);
        price = findViewById(R.id.etPrice);

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.view_expense_title_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                        textViewDate.getText().toString().trim(),
                        title.getText().toString().trim(),
                        description.getText().toString().trim(),
                        price.getText().toString().trim(),
                        installments
                );
                UIUtil.hideKeyboard(this);
                break;
            }
            case R.id.tvDate : {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                            expensePresenter.OnCalculateDate(dayOfMonth,month,year);
                    }
                },calendarYear,calendarMonth,calendarDay);
                datePickerDialog.show();
                break;
            }
            case R.id.tvInstallment : {
                builderInstallments = new AlertDialog.Builder(this);
                builderInstallments.setCancelable(false);
                builderInstallments.setTitle(R.string.view_expense_installments_number);
                builderInstallments.setItems(R.array.installments, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        installments = (which + 1);
                        textInstallments.setText(installments + getString(R.string.view_overview_installments));
                    }
                });
                builderInstallments.show();
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
    public void OnInvalidField(String message) {
        builder.setMessage(message);
        builder.setPositiveButton(R.string.view_expense_alertDialog_positive_button, null);
        builder.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnCalculatedDate(String day, String month, String year) {
        textViewDate.setText(day+"/"+month+"/"+year);
    }

    @Override
    public void OnAddExpenseSuccessful() {
        builder.setMessage(R.string.view_expense_alertDialog_success);
        builder.setPositiveButton(R.string.view_expense_alertDialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void OnAddExpenseFailure(String message) {
        builder.setMessage(R.string.view_expense_alertDialog_error);
        builder.setPositiveButton(R.string.view_expense_alertDialog_positive_button, null);
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
}