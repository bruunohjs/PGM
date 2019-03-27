package com.dev.marcellocamara.pgm.ui.points;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.dev.marcellocamara.pgm.R;
import com.dev.marcellocamara.pgm.utils.Tooltip;

import java.util.Objects;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
    marcellocamara@id.uff.br
            2019
***/

public class PointsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.imageViewInfoPoints) protected ImageView imageViewInfoPoints;

    @BindString(R.string.points) protected String points;

    @BindColor(R.color.colorAccent) protected int colorAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(points);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.imageViewInfoPoints)
    public void OnInfoPointsClick(){
        Tooltip.show(imageViewInfoPoints, "Points information", colorAccent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}