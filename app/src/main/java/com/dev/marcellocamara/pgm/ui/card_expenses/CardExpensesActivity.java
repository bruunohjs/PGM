package com.dev.marcellocamara.pgm.ui.card_expenses;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.adapter.ExpensesAdapter;
import com.dev.marcellocamara.pgm.adapter.IAdapter;
import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.model.ExpenseModel;
import com.dev.marcellocamara.pgm.R;
import com.dev.marcellocamara.pgm.ui.expense_overview.ExpenseOverviewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/***
    marcellocamara@id.uff.br
            2019
***/

public class CardExpensesActivity extends AppCompatActivity implements ICardExpenses.View, IAdapter {

    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @BindView(R.id.recyclerView) protected RecyclerView recyclerView;

    @BindView(R.id.textViewExpenses) protected TextView textViewExpenses;

    @BindString(R.string.parcelable_card) protected String parcelable_card;
    @BindString(R.string.parcelable_expense) protected String parcelable_expense;
    @BindString(R.string.card_expenses) protected String  card_expenses;
    @BindString(R.string.expenses_for) protected String expenses_for;
    @BindString(R.string.close) protected String close;

    private CardExpensesPresenter presenter;
    private List<ExpenseModel> expensesList;
    private ArrayList<CardModel> cardArray;
    private String monthYear;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_expenses);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(card_expenses);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cardArray = getIntent().getParcelableArrayListExtra(parcelable_card);
        monthYear = getIntent().getStringExtra(parcelable_expense);

        textViewExpenses.setText(expenses_for + " " + monthYear.substring(0, 2) + "/" + monthYear.substring(2,6));

        presenter = new CardExpensesPresenter(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

    }

    @Override
    public void OnRequestExpensesResult(List<ExpenseModel> expensesList) {
        this.expensesList = expensesList;
        ExpensesAdapter adapter = new ExpensesAdapter(this, this.expensesList, this.cardArray, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnItemClick(int position) {
        startActivity(new Intent(this, ExpenseOverviewActivity.class)
                .putExtra(parcelable_expense, expensesList.get(position))
                .putExtra(parcelable_card, cardArray.get(0))
        );
    }

    @Override
    public void OnRequestExpensesFailure(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(card_expenses);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.OnRequestExpenses(monthYear, cardArray.get(0).getUniqueId());
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.OnStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.OnDestroy();
    }
}