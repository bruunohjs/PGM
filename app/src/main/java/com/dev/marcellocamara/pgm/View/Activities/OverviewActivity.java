package com.dev.marcellocamara.pgm.View.Activities;

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

import java.util.Objects;

import dmax.dialog.SpotsDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
    marcellocamara@id.uff.br
            2019
***/

public class OverviewActivity extends AppCompatActivity implements IOverview.View {

    @BindView(R.id.textViewTitle) protected TextView textViewTitle;
    @BindView(R.id.textViewDescription) protected TextView textViewDescription;
    @BindView(R.id.textViewPrice) protected TextView textViewPrice;
    @BindView(R.id.textViewInstallment) protected TextView textViewInstallment;
    @BindView(R.id.textViewDate) protected TextView textViewDate;
    @BindView(R.id.textViewEachInstallment) protected TextView textViewEachInstallment;

    @BindView(R.id.layoutEachInstallment) protected ConstraintLayout layoutEachInstallment;

    @BindView(R.id.btnDelete) protected Button btnDelete;

    private IOverview.Presenter overviewPresenter;
    private ExpenseModel expenseModel;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.view_overview_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        expenseModel = getIntent().getParcelableExtra(getString(R.string.view_parcelable_name));

        textViewTitle.setText(expenseModel.getTitle());
        textViewDescription.setText(expenseModel.getDescription());
        textViewPrice.setText(NumberHelper.GetDecimal(expenseModel.getPrice()));
        textViewInstallment.setText(String.valueOf(Integer.parseInt(expenseModel.getInstallments())));
        textViewDate.setText(expenseModel.getPaymentDate());

        overviewPresenter = new OverviewPresenter(this);
        overviewPresenter.OnVerifyInstallments(expenseModel.getInstallments(), expenseModel.getPrice());

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(R.string.view_overview_custom_alertDialog)
                .setCancelable(false)
                .build();
    }

    @OnClick(R.id.btnDelete)
    public void OnButtonClick(){
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.view_overview_title);
        builder.setMessage(R.string.view_overview_delete_confirm);
        builder.setPositiveButton(R.string.view_overview_delete_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                overviewPresenter.OnDeleteExpense(expenseModel);
            }
        });
        builder.setNegativeButton(R.string.view_overview_delete_cancel, null);
        builder.show();
    }

    @Override
    public void OnSetInstallments(String value) {
        layoutEachInstallment.setVisibility(View.VISIBLE);
        textViewEachInstallment.setText(value);
    }

    @Override
    public void OnDeleteExpenseSuccess() {
        finish();
    }

    @Override
    public void OnDeleteExpenseFailure(String message) {
        builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.view_overview_title);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.view_overview_dialog_close, null);
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
        overviewPresenter.OnDestroy();
    }
}