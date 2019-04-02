package com.dev.marcellocamara.pgm.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.adapter.ExpensesAdapter;
import com.dev.marcellocamara.pgm.adapter.IAdapter;
import com.dev.marcellocamara.pgm.utils.NumberFormat;
import com.dev.marcellocamara.pgm.utils.SpecificExpenseCard;
import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.model.ExpenseModel;
import com.dev.marcellocamara.pgm.R;
import com.dev.marcellocamara.pgm.ui.new_card.NewCardActivity;
import com.dev.marcellocamara.pgm.ui.new_expense.NewExpenseActivity;
import com.dev.marcellocamara.pgm.ui.expense_overview.ExpenseOverviewActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindString;
import dmax.dialog.SpotsDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/***
    marcellocamara@id.uff.br
            2019
***/

public class HomeFragment extends Fragment implements IHome.View, IAdapter, OnMonthChangedListener {

    @BindView(R.id.textViewPrice) protected TextView textViewPrice;

    @BindView(R.id.materialCalendarView) protected MaterialCalendarView materialCalendarView;

    @BindView(R.id.recyclerView) protected RecyclerView recyclerView;

    @BindString(R.string.new_expense) protected String new_expense;
    @BindString(R.string.expenses_loading) protected String loading_expenses;
    @BindString(R.string.parcelable_expense) protected String parcelable_expense;
    @BindString(R.string.parcelable_card) protected String parcelable_card;
    @BindString(R.string.fab_no_card) protected String fab_no_card;
    @BindString(R.string.close) protected String close;

    private IHome.Presenter presenter;
    private List<ExpenseModel> expensesList;
    private ArrayList<CardModel> cardsList;
    private String calendarMonth, calendarYear;
    private AlertDialog alertDialog;
    private Unbinder unbinder;

    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        unbinder = ButterKnife.bind(this, view);

        presenter = new HomePresenter(this);

        Calendar calendar = Calendar.getInstance();
        calendarMonth = NumberFormat.getMonth( (calendar.get(Calendar.MONTH)) + 1 );
        calendarYear = String.valueOf( calendar.get(Calendar.YEAR) );

        materialCalendarView.setOnMonthChangedListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        alertDialog = new SpotsDialog.Builder()
                .setContext(view.getContext())
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(loading_expenses)
                .setCancelable(false)
                .build();

        return view;
    }

    @OnClick(R.id.mutativeFAB)
    public void OnFloatingActionButtonClick(){
        presenter.OnCheckUserCards(cardsList);
    }

    @Override
    public void AllowAddNewExpense() {
        startActivity(new Intent(getContext(), NewExpenseActivity.class)
                .putParcelableArrayListExtra(parcelable_card, cardsList)
        );
    }

    @Override
    public void DenyAddNewExpense() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(new_expense);
        builder.setCancelable(false);
        builder.setMessage(fab_no_card);
        builder.setPositiveButton(close, null);
        builder.setNegativeButton(R.string.fabCard, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getContext(), NewCardActivity.class));
            }
        });
        builder.show();
    }

    @Override
    public void OnItemClick(int position) {
        startActivity(new Intent(getContext(), ExpenseOverviewActivity.class)
                .putExtra(parcelable_expense, expensesList.get(position))
                .putExtra(parcelable_card, SpecificExpenseCard.getCard(expensesList.get(position).getCreditCard(), cardsList))
        );
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        calendarMonth = NumberFormat.getMonth(date.getMonth());
        calendarYear = String.valueOf(date.getYear());
        presenter.OnStop();
        presenter.OnRequestExpenses( calendarMonth + calendarYear );
    }

    @Override
    public void OnRequestExpensesResult(List<ExpenseModel> expensesList, ArrayList<CardModel> cardsList) {
        this.expensesList = expensesList;
        this.cardsList = cardsList;
        ExpensesAdapter adapter = new ExpensesAdapter(getContext(), this.expensesList, this.cardsList, this);
        recyclerView.setAdapter(adapter);
        presenter.OnTotalCalculate(this.expensesList);
    }

    @Override
    public void OnRequestTotalCalculateResult(String value) {
        textViewPrice.setText(value);
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
    public void onStart() {
        super.onStart();
        presenter.OnRequestExpenses(calendarMonth + calendarYear);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.OnStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.OnDestroy();
        unbinder.unbind();
    }

}