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
import com.dev.marcellocamara.pgm.Contract.IRecyclerView;
import com.dev.marcellocamara.pgm.Helper.NumberHelper;
import com.dev.marcellocamara.pgm.Model.ExpenseModel;
import com.dev.marcellocamara.pgm.Presenter.HomePresenter;
import com.dev.marcellocamara.pgm.R;
import com.dev.marcellocamara.pgm.View.Activities.ExpenseActivity;
import com.dev.marcellocamara.pgm.View.Activities.OverviewActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;

/***
    marcellocamara@id.uff.br
            2019
***/

public class HomeFragment extends Fragment implements IHome.View, IRecyclerView, View.OnClickListener, OnMonthChangedListener {

    private IHome.Presenter homePresenter;
    private RecyclerView recyclerView;
    private MaterialCalendarView calendarView;
    private TextView total;
    private List<ExpenseModel> list;
    private Calendar calendar;
    private String calendarMonth, calendarYear;
    private AlertDialog alertDialog;
    private MutativeFab mutativeFab;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        ViewBind(view);

        homePresenter = new HomePresenter(this);
        //homePresenter.OnRequestUserName();

        alertDialog = new SpotsDialog.Builder()
                .setContext(view.getContext())
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(R.string.view_home_alertDialog_loading)
                .setCancelable(false)
                .build();        

        return view;
    }

    private void ViewBind(View view) {

        total = view.findViewById(R.id.textViewPrice);

        mutativeFab = view.findViewById(R.id.floatingAB);
        mutativeFab.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.recyclerExpenses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        calendarView = view.findViewById(R.id.materialCalendar);
        calendarView.setOnMonthChangedListener(this);

        calendar = Calendar.getInstance();
        calendarMonth = NumberHelper.GetMonth( (calendar.get(Calendar.MONTH)) + 1 );
        calendarYear = String.valueOf( calendar.get(Calendar.YEAR) );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingAB : {
                startActivity(new Intent(getContext(), ExpenseActivity.class));
                break;
            }
        }
    }

    @Override
    public void OnItemClick(int position) {
        startActivity(new Intent(getContext(), OverviewActivity.class).putExtra(getString(R.string.view_parcelable_name), list.get(position)));
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
        homePresenter.OnTotalCalculate(list, getString(R.string.view_home_price_example));
    }

    @Override
    public void OnRequestTotalCalculateResult(String value) {
        total.setText(value);
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
    }
}