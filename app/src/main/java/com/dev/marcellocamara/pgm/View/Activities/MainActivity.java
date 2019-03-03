package com.dev.marcellocamara.pgm.View.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev.marcellocamara.pgm.Contract.IMain;
import com.dev.marcellocamara.pgm.Presenter.MainPresenter;
import com.dev.marcellocamara.pgm.R;
import com.dev.marcellocamara.pgm.View.Fragments.CardsFragment;
import com.dev.marcellocamara.pgm.View.Fragments.HomeFragment;
import com.dev.marcellocamara.pgm.View.Fragments.ProfileFragment;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Objects;

/***
    marcellocamara@id.uff.br
            2019
***/

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMain.View {

    private IMain.Presenter mainPresenter;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView textViewUserName, textViewUserEmail;
    private CircularImageView imageViewUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewBind();

        mainPresenter = new MainPresenter(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    private void ViewBind() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        textViewUserName = headerView.findViewById(R.id.textViewUserName);
        textViewUserEmail = headerView.findViewById(R.id.textViewUserEmail);
        imageViewUserProfile = headerView.findViewById(R.id.imageViewUserProfile);
    }

    @Override
    public void OnRequestUserDataSuccessful(String name, String email) {
        textViewUserName.setText(name);
        textViewUserEmail.setText(email);
    }

    @Override
    public void OnSetUserImage(Uri uri) {
        Glide.with(MainActivity.this).load(uri).into(imageViewUserProfile);
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
                mainPresenter.OnLogout();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home : {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
                break;
            }
            case R.id.nav_cards : {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new CardsFragment()).commit();
                break;
            }
            case R.id.nav_profile : {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProfileFragment()).commit();
                break;
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void OnLogoutSuccessful() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPresenter.OnRequestUserData();
    }
}