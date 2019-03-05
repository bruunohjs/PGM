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

import butterknife.BindView;
import butterknife.ButterKnife;
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
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.view_expense_title_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        expensePresenter = new ExpensePresenter(this, this);

        Calendar calendar = Calendar.getInstance();
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
                builderInstallments.setTitle(R.string.view_expense_installments_number);
                builderInstallments.setItems(R.array.installments, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        installments = (which + 1);
                        textViewInstallment.setText(installments + getString(R.string.view_overview_installments));
                    }
                });
                builderInstallments.show();
                break;
            }
        }
    }

    @OnClick({R.id.btnCancel, R.id.btnSave})
    public void OnButtonClick(Button v){
        switch (v.getId()) {
            case R.id.btnCancel: {
                finish();
                break;
            }
            case R.id.btnSave: {
                expensePresenter.OnAddExpense(
                        textViewDate.getText().toString().trim(),
                        editTextTitle.getText().toString().trim(),
                        editTextDescription.getText().toString().trim(),
                        editTextPrice.getText().toString().trim(),
                        installments
                );
                UIUtil.hideKeyboard(this);
                break;
            }
        }
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