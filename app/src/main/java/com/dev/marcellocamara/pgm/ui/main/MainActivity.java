package com.dev.marcellocamara.pgm.ui.main;

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
import com.dev.marcellocamara.pgm.R;
import com.dev.marcellocamara.pgm.ui.cards.CardsFragment;
import com.dev.marcellocamara.pgm.ui.home.HomeFragment;
import com.dev.marcellocamara.pgm.ui.login.LoginActivity;
import com.dev.marcellocamara.pgm.ui.profile.ProfileFragment;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
    marcellocamara@id.uff.br
            2019
***/

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMain.View {

    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @BindView(R.id.navigationView) protected NavigationView navigationView;

    @BindView(R.id.drawerLayout) protected DrawerLayout drawerLayout;

    private IMain.Presenter mainPresenter;
    private TextView textViewUserName, textViewUserEmail;
    private CircularImageView imageViewUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mainPresenter = new MainPresenter(this);

        View headerView = navigationView.getHeaderView(0);
        textViewUserName = headerView.findViewById(R.id.textViewUserName);
        textViewUserEmail = headerView.findViewById(R.id.textViewUserEmail);
        imageViewUserProfile = headerView.findViewById(R.id.imageViewUserProfile);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_home);
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
        //TODO : Replace switch into methods
        switch (item.getItemId()){
            case R.id.nav_home : {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new HomeFragment()).commit();
                break;
            }
            case R.id.nav_cards : {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new CardsFragment()).commit();
                break;
            }
            case R.id.nav_profile : {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new ProfileFragment()).commit();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void OnLogoutSuccessful() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
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