package com.dev.marcellocamara.pgm.View;

import android.support.constraint.ConstraintLayout;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class OverviewActivity extends AppCompatActivity implements IOverview.View{

    private IOverview.Presenter overviewPresenter;
    private ExpenseModel expenseModel;
    private Toolbar toolbar;
    private ConstraintLayout layoutInstallments, layoutEachInstallments;
    private TextView title, description, price, installment, date, eachInstallment;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        expenseModel = getIntent().getParcelableExtra("expense");

        ViewBind();

        overviewPresenter = new OverviewPresenter(this);

        title.setText(expenseModel.getTitle());
        description.setText(expenseModel.getDescription());
        price.setText(NumberHelper.GetDecimal(expenseModel.getPrice()));
        date.setText(expenseModel.getPaymentDate());

        overviewPresenter.OnVerifyInstallments(expenseModel.getInstallments(), expenseModel.getPrice());

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(R.string.view_overview_custom_alertDialog)
                .setCancelable(false)
                .build();
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

        layoutInstallments = findViewById(R.id.layoutInstallment);
        layoutEachInstallments = findViewById(R.id.layoutEachInstallment);

    }

    @Override
    public void OnSetInstallments(String value) {
        layoutInstallments.setVisibility(View.VISIBLE);
        layoutEachInstallments.setVisibility(View.VISIBLE);
        installment.setText(String.valueOf(Integer.parseInt(expenseModel.getInstallments())));
        eachInstallment.setText(value);
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
