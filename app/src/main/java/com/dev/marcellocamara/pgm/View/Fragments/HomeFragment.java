package com.dev.marcellocamara.pgm.View.Fragments;

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

import com.aniket.mutativefloatingactionbutton.MutativeFab;
import com.dev.marcellocamara.pgm.Adapter.ExpensesAdapter;
import com.dev.marcellocamara.pgm.Contract.IHome;
import com.dev.marcellocamara.pgm.Contract.IAdapter;
import com.dev.marcellocamara.pgm.Helper.NumberHelper;
import com.dev.marcellocamara.pgm.Model.ExpenseModel;
import com.dev.marcellocamara.pgm.Presenter.HomePresenter;
import com.dev.marcellocamara.pgm.R;
import com.dev.marcellocamara.pgm.View.Activities.NewExpenseActivity;
import com.dev.marcellocamara.pgm.View.Activities.ExpenseOverviewActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

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

    @BindView(R.id.mutativeFAB) protected MutativeFab mutativeFAB;

    @BindString(R.string.expenses_loading) protected String loading_expenses;
    @BindString(R.string.parcelable_name) protected String parcelable;
    @BindString(R.string.zero) protected String zero;

    private IHome.Presenter homePresenter;
    private List<ExpenseModel> list;
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

        homePresenter = new HomePresenter(this);

        Calendar calendar = Calendar.getInstance();
        calendarMonth = NumberHelper.GetMonth( (calendar.get(Calendar.MONTH)) + 1 );
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
        startActivity(new Intent(getContext(), NewExpenseActivity.class));
    }

    @Override
    public void OnItemClick(int position) {
        startActivity(new Intent(getContext(), ExpenseOverviewActivity.class).putExtra(parcelable, list.get(position)));
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        calendarMonth = NumberHelper.GetMonth(date.getMonth());
        calendarYear = String.valueOf(date.getYear());
        homePresenter.OnStop();
        homePresenter.OnRequestExpenses( calendarMonth + calendarYear );
    }

    @Override
    public void OnRequestExpensesResult(List<ExpenseModel> list) {
        this.list = list;
        ExpensesAdapter adapter = new ExpensesAdapter(this.list, this);
        recyclerView.setAdapter(adapter);
        homePresenter.OnTotalCalculate(list, zero);
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
        homePresenter.OnRequestExpenses(calendarMonth + calendarYear);
    }

    @Override
    public void onStop() {
        super.onStop();
        homePresenter.OnStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homePresenter.OnDestroy();
        unbinder.unbind();
    }
}