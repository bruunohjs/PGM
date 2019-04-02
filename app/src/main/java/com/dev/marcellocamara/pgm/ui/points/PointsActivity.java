package com.dev.marcellocamara.pgm.ui.points;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.R;
import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.utils.Tooltip;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import dmax.dialog.SpotsDialog;

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

public class PointsActivity extends AppCompatActivity implements IPoints.View {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.textViewPointsTotal) protected TextView textViewPointsTotal;

    @BindView(R.id.editTextValue) protected EditText editTextValue;

    @BindView(R.id.imageViewInfoPoints) protected ImageView imageViewInfoPoints;

    @BindString(R.string.points) protected String points;
    @BindString(R.string.parcelable_card) protected String parcelable_card;
    @BindString(R.string.loading_points) protected String loading_points;
    @BindString(R.string.value_invalid) protected String value_invalid;
    @BindString(R.string.subtract_failure) protected String subtract_failure;
    @BindString(R.string.add_failure) protected String add_failure;
    @BindString(R.string.info_points) protected String info_points;
    @BindString(R.string.points_updated) protected String points_updated;
    @BindString(R.string.close) protected String close;

    @BindColor(R.color.colorAccent) protected int colorAccent;

    private IPoints.Presenter presenter;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        ButterKnife.bind(this);

        CardModel card = getIntent().getParcelableExtra(parcelable_card);
        textViewPointsTotal.setText(String.format("%.0f", Objects.requireNonNull(card).getPoints()));

        presenter = new PointsPresenter(this, card);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(points);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(loading_points)
                .setCancelable(false)
                .build();

        builder = new AlertDialog.Builder(this);
        builder.setTitle(points);
        builder.setCancelable(false);
    }

    @OnClick(R.id.btnSubtract)
    public void OnButtonSubtractClick(){
        editTextValue.clearFocus();
        presenter.OnSubtractPoints(editTextValue.getText().toString().trim());
        UIUtil.hideKeyboard(this);
    }

    @OnClick(R.id.btnAdd)
    public void OnButtonAddClick(){
        editTextValue.clearFocus();
        presenter.OnAddPoints(editTextValue.getText().toString().trim());
        UIUtil.hideKeyboard(this);
    }

    @OnClick(R.id.imageViewInfoPoints)
    public void OnInfoPointsClick(){
        Tooltip.show(imageViewInfoPoints, Gravity.TOP, info_points, colorAccent);
    }

    @Override
    public void OnUpdatePointsSuccessful(String points) {
        textViewPointsTotal.setText(points);
        editTextValue.setText("");
        builder.setMessage(points_updated);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnUpdatePointsFailure(String message) {
        builder.setMessage(message);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnValueEmpty() {
        builder.setMessage(value_invalid);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnAddPointsFailure() {
        builder.setMessage(add_failure);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnSubtractPointsFailure() {
        builder.setMessage(subtract_failure);
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
        presenter.OnDestroy();
    }

}