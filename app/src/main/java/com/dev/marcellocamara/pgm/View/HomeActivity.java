package com.dev.marcellocamara.pgm.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.aniket.mutativefloatingactionbutton.MutativeFab;
import com.dev.marcellocamara.pgm.Adapter.ExpensesAdapter;
import com.dev.marcellocamara.pgm.Contract.IHome;
import com.dev.marcellocamara.pgm.Helper.NumberHelper;
import com.dev.marcellocamara.pgm.Model.ExpenseModel;
import com.dev.marcellocamara.pgm.Presenter.HomePresenter;
import com.dev.marcellocamara.pgm.R;
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

public class HomeActivity extends AppCompatActivity implements IHome.View, View.OnClickListener, OnMonthChangedListener, ExpensesAdapter.OnRecyclerViewClick {

    private IHome.Presenter homePresenter;
    private RecyclerView recyclerView;
    private MutativeFab mutativeFab;
    private MaterialCalendarView calendarView;
    private Toolbar toolbar;
    private TextView name, total;
    private List<ExpenseModel> list;
    private Calendar calendar;
    private String calendarMonth, calendarYear;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ViewBind();

        homePresenter = new HomePresenter(this);
        homePresenter.OnRequestUserName();

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(R.string.view_home_alertDialog_loading)
                .setCancelable(false)
                .build();
    }

    private void ViewBind() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        name = findViewById(R.id.textViewName);
        total = findViewById(R.id.textViewPrice);

        mutativeFab = findViewById(R.id.floatingAB);
        mutativeFab.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerExpenses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        calendarView = findViewById(R.id.materialCalendar);
        calendarView.setOnMonthChangedListener(this);

        calendar = Calendar.getInstance();
        calendarMonth = NumberHelper.GetMonth( (calendar.get(Calendar.MONTH)) + 1 );
        calendarYear = String.valueOf( calendar.get(Calendar.YEAR) );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingAB : {
                startActivity(new Intent(this, ExpenseActivity.class));
                break;
            }
        }
    }

    @Override
    public void OnItemClick(int position) {
        startActivity(new Intent(this, OverviewActivity.class).putExtra(getString(R.string.view_parcelable_name), list.get(position)));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout : {
                homePresenter.OnLogout();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnLogoutSuccessful() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void OnRequestUserNameResult(String result) {
        name.setText(result);
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
    protected void onStart() {
        super.onStart();
        homePresenter.OnRequestExpenses(calendarMonth + calendarYear);
    }

    @Override
    protected void onStop() {
        super.onStop();
        homePresenter.OnStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.OnDestroy();
    }
}
