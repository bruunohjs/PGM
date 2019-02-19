package com.dev.marcellocamara.pgm.View;

import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.Contract.IOverview;
import com.dev.marcellocamara.pgm.Helper.NumberHelper;
import com.dev.marcellocamara.pgm.Model.ExpenseModel;
import com.dev.marcellocamara.pgm.Presenter.OverviewPresenter;
import com.dev.marcellocamara.pgm.R;

import dmax.dialog.SpotsDialog;

/***
    marcellocamara@id.uff.br
            2019
***/

public class OverviewActivity extends AppCompatActivity implements IOverview.View, View.OnClickListener{

    private IOverview.Presenter overviewPresenter;
    private ExpenseModel expenseModel;
    private Toolbar toolbar;
    private Button delete;
    private ConstraintLayout layoutEachInstallments;
    private TextView title, description, price, installment, date, eachInstallment;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        expenseModel = getIntent().getParcelableExtra("expense");

        ViewBind();

        overviewPresenter = new OverviewPresenter(this);

        overviewPresenter.OnVerifyInstallments(expenseModel.getInstallments(), expenseModel.getPrice());

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(R.string.view_overview_custom_alertDialog)
                .setCancelable(false)
                .build();

        builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.view_overview_title);
        builder.setCancelable(false);
    }

    private void ViewBind() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.view_overview_actionbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.textViewTitle);
        description = findViewById(R.id.textViewDescription);
        price = findViewById(R.id.textViewPrice);
        installment = findViewById(R.id.textViewInstallment);
        date = findViewById(R.id.textViewDate);
        eachInstallment = findViewById(R.id.textViewEachInstallment);

        layoutEachInstallments = findViewById(R.id.layoutEachInstallment);

        delete = findViewById(R.id.buttonDelete);
        delete.setOnClickListener(this);

        title.setText(expenseModel.getTitle());
        description.setText(expenseModel.getDescription());
        price.setText(NumberHelper.GetDecimal(expenseModel.getPrice()));
        installment.setText(String.valueOf(Integer.parseInt(expenseModel.getInstallments())));
        date.setText(expenseModel.getPaymentDate());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonDelete : {
                builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle(R.string.view_overview_actionbar_title);
                builder.setMessage(R.string.view_overview_delete_confirm);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        overviewPresenter.OnDeleteExpense(expenseModel);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });
                builder.show();
                break;
            }
        }
    }

    @Override
    public void OnSetInstallments(String value) {
        layoutEachInstallments.setVisibility(View.VISIBLE);
        eachInstallment.setText(value);
    }

    @Override
    public void OnDeleteExpenseSuccess() {
        finish();
    }

    @Override
    public void OnDeleteExpenseFailure(String message) {
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.view_overview_actionbar_title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.view_expense_alertDialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        builder.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
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
    protected void onDestroy() {
        super.onDestroy();
        overviewPresenter.OnDestroy();
    }
}
