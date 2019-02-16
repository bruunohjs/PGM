package com.dev.marcellocamara.pgm.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aniket.mutativefloatingactionbutton.MutativeFab;
import com.dev.marcellocamara.pgm.Adapter.ExpensesAdapter;
import com.dev.marcellocamara.pgm.Contract.IHome;
import com.dev.marcellocamara.pgm.Presenter.HomePresenter;
import com.dev.marcellocamara.pgm.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

/***
    marcellocamara@id.uff.br
            2019
***/

public class HomeActivity extends AppCompatActivity implements IHome.View, View.OnClickListener, OnMonthChangedListener {

    private IHome.Presenter homePresenter;
    private RecyclerView recyclerView;
    private MutativeFab mutativeFab;
    private MaterialCalendarView calendarView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ViewBind();

        homePresenter = new HomePresenter(this);

    }

    private void ViewBind() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        mutativeFab = findViewById(R.id.floatingAB);
        mutativeFab.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerExpenses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        ExpensesAdapter adapter = new ExpensesAdapter();
        recyclerView.setAdapter(adapter);

        calendarView = findViewById(R.id.materialCalendar);
        calendarView.setOnMonthChangedListener(this);

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
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //TODO : get values to update info
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
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.OnDestroy();
    }
}
