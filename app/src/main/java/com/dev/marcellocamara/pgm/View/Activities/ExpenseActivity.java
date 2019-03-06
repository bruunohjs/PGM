package com.dev.marcellocamara.pgm.View.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.Contract.IExpense;
import com.dev.marcellocamara.pgm.Presenter.ExpensePresenter;
import com.dev.marcellocamara.pgm.R;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import dmax.dialog.SpotsDialog;

import java.util.Calendar;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ExpenseActivity extends AppCompatActivity implements IExpense.View {

    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @BindView(R.id.textViewDate) protected TextView textViewDate;
    @BindView(R.id.textViewInstallment) protected TextView textViewInstallment;

    @BindView(R.id.editTextTitle) protected EditText editTextTitle;
    @BindView(R.id.editTextDescription) protected EditText editTextDescription;
    @BindView(R.id.editTextPrice) protected EditText editTextPrice;

    @BindView(R.id.btnCancel) protected Button btnCancel;
    @BindView(R.id.btnSave) protected Button btnSave;

    @BindString(R.string.new_expense) protected String title;
    @BindString(R.string.installments_number) protected String installmentsNumber;
    @BindString(R.string.empty_title) protected String empty_title;
    @BindString(R.string.empty_description) protected String empty_description;
    @BindString(R.string.empty_price) protected String empty_price;
    @BindString(R.string.max_price) protected String max_price;
    @BindString(R.string.view_overview_installments) protected String installmentsX;
    @BindString(R.string.close) protected String close;
    @BindString(R.string.adding_expense) protected String alertDialogMessage;
    @BindString(R.string.adding_expense_success) protected String alertDialogSuccess;

    private IExpense.Presenter expensePresenter;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder ;
    private int installments = 1, calendarDay, calendarMonth, calendarYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        expensePresenter = new ExpensePresenter(this);

        Calendar calendar = Calendar.getInstance();
        calendarDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendarMonth = calendar.get(Calendar.MONTH);
        calendarYear = calendar.get(Calendar.YEAR);
        expensePresenter.OnCalculateDate(calendarDay,calendarMonth,calendarYear);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(alertDialogMessage)
                .setCancelable(false)
                .build();

        builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(false);
    }

    @OnClick({R.id.textViewDate, R.id.textViewInstallment})
    public void OnTextViewClick(TextView v){
        switch (v.getId()){
            case R.id.textViewDate : {
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
            case R.id.textViewInstallment : {
                AlertDialog.Builder builderInstallments = new AlertDialog.Builder(this);
                builderInstallments.setCancelable(false);
                builderInstallments.setTitle(installmentsNumber);
                builderInstallments.setItems(R.array.installments, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        installments = (which + 1);
                        textViewInstallment.setText(installments + installmentsX);
                    }
                });
                builderInstallments.show();
                break;
            }
        }
    }

    @OnClick(R.id.btnSave)
    public void OnSaveClick(){
        expensePresenter.OnAddExpense(
                textViewDate.getText().toString().trim(),
                editTextTitle.getText().toString().trim(),
                editTextDescription.getText().toString().trim(),
                editTextPrice.getText().toString().trim(),
                installments
        );
        UIUtil.hideKeyboard(this);
    }

    @OnClick(R.id.btnCancel)
    public void OnCancelClick(){ finish(); }

    @Override
    public void OnEmptyTitle() {
        builder.setMessage(empty_title);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnEmptyDescription() {
        builder.setMessage(empty_description);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnEmptyPrice() {
        builder.setMessage(empty_price);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnMaxPrice() {
        builder.setMessage(max_price);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnCalculatedDate(String day, String month, String year) {
        textViewDate.setText(day+"/"+month+"/"+year);
    }

    @Override
    public void OnAddExpenseSuccessful() {
        builder.setMessage(alertDialogSuccess);
        builder.setPositiveButton(close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void OnAddExpenseFailure(String message) {
        builder.setMessage(message);
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
        expensePresenter.OnDestroy();
    }
}