package com.dev.marcellocamara.pgm.ui.expense_overview;

import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.utils.CardUtils;
import com.dev.marcellocamara.pgm.utils.NumberFormat;
import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.model.ExpenseModel;
import com.dev.marcellocamara.pgm.R;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

import butterknife.BindView;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ExpenseOverviewActivity extends AppCompatActivity implements IExpenseOverview.View {

    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @BindView(R.id.textViewTitle) protected TextView textViewTitle;
    @BindView(R.id.textViewDescription) protected TextView textViewDescription;
    @BindView(R.id.textViewPrice) protected TextView textViewPrice;
    @BindView(R.id.textViewInstallment) protected TextView textViewInstallment;
    @BindView(R.id.textViewCard) protected TextView textViewCard;
    @BindView(R.id.textViewFinalDigits) protected TextView textViewFinalDigits;
    @BindView(R.id.textViewDate) protected TextView textViewDate;
    @BindView(R.id.textViewEachInstallment) protected TextView textViewEachInstallment;

    @BindView(R.id.imageViewCreditCard) protected ImageView imageViewCreditCard;

    @BindView(R.id.layoutEachInstallment) protected ConstraintLayout layoutEachInstallment;

    @BindString(R.string.expense_overview) protected String overview_title;
    @BindString(R.string.deleting_expense) protected String deleting;
    @BindString(R.string.confirm_delete) protected String confirmation;
    @BindString(R.string.yes) protected String yes;
    @BindString(R.string.no) protected String no;
    @BindString(R.string.close) protected String close;
    @BindString(R.string.parcelable_expense) protected String parcelable_expense;
    @BindString(R.string.parcelable_card) protected String parcelable_card;

    private IExpenseOverview.Presenter overviewPresenter;
    private ExpenseModel expenseModel;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_overview);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(overview_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        expenseModel = getIntent().getParcelableExtra(parcelable_expense);
        CardModel cardModel = getIntent().getParcelableExtra(parcelable_card);
        SetCreditCard(cardModel);

        overviewPresenter = new ExpenseOverviewPresenter(this);
        overviewPresenter.OnVerifyInstallments(expenseModel.getInstallments(), expenseModel.getPrice());

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(deleting)
                .setCancelable(false)
                .build();
    }

    private void SetCreditCard(CardModel cardModel) {
        textViewTitle.setText(expenseModel.getTitle());
        textViewDescription.setText(expenseModel.getDescription());
        textViewPrice.setText(NumberFormat.getDecimal(expenseModel.getPrice()));
        textViewInstallment.setText(String.valueOf(Integer.parseInt(expenseModel.getInstallments())));
        textViewCard.setText(cardModel.getCardTitle());
        imageViewCreditCard.setColorFilter(CardUtils.getColor(this, cardModel.getCardColor()));
        textViewFinalDigits.setText(cardModel.getFinalDigits());
        textViewDate.setText(expenseModel.getPaymentDate());
    }

    @OnClick(R.id.btnDelete)
    public void OnButtonClick(){
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(overview_title);
        builder.setMessage(confirmation);
        builder.setPositiveButton(yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                overviewPresenter.OnDeleteExpense(expenseModel);
            }
        });
        builder.setNegativeButton(no, null);
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
        builder.setTitle(overview_title);
        builder.setCancelable(false);
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
        overviewPresenter.OnDestroy();
    }
}