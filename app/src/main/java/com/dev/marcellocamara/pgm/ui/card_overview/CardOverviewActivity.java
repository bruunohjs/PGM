package com.dev.marcellocamara.pgm.ui.card_overview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.marcellocamara.pgm.ui.card_expenses.CardExpensesActivity;
import com.dev.marcellocamara.pgm.ui.new_card.NewCardActivity;
import com.dev.marcellocamara.pgm.ui.points.PointsActivity;
import com.dev.marcellocamara.pgm.utils.CardUtils;
import com.dev.marcellocamara.pgm.utils.NumberFormat;
import com.dev.marcellocamara.pgm.utils.Tooltip;
import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
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

public class CardOverviewActivity extends AppCompatActivity implements ICardOverview.View, OnMonthChangedListener {

    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @BindView(R.id.textViewTitleCard) protected TextView textViewTitleCard;
    @BindView(R.id.textViewCardNumber) protected TextView textViewCardNumber;
    @BindView(R.id.textViewUserName) protected TextView textViewUserName;
    @BindView(R.id.textViewPrice) protected TextView textViewPrice;
    @BindView(R.id.textViewPointsTotal) protected TextView textViewPointsTotal;

    @BindView(R.id.layoutCard) protected ConstraintLayout layoutCard;

    @BindView(R.id.imageViewSelectedFlag) protected ImageView imageViewSelectedFlag;
    @BindView(R.id.imageViewInfoPoints) protected ImageView imageViewInfoPoints;
    @BindView(R.id.imageViewInfoAnnuityNotification) protected ImageView imageViewInfoAnnuityNotification;

    @BindView(R.id.materialCalendarView) protected MaterialCalendarView materialCalendarView;

    @BindString(R.string.card_overview) protected String card_overview;
    @BindString(R.string.parcelable_card) protected String parcelable_card;
    @BindString(R.string.parcelable_expense) protected String parcelable_expense;
    @BindString(R.string.cards_numbers) protected String cards_numbers;
    @BindString(R.string.info_points) protected String info_points;
    @BindString(R.string.info_annuity_notification) protected String info_annuity_notification;
    @BindString(R.string.close) protected String close;
    @BindString(R.string.card_number) protected String card_number;
    @BindString(R.string.view_expenses_denied) protected String view_expenses_denied;

    @BindColor(R.color.colorAccent) protected int colorAccent;

    private ICardOverview.Presenter presenter;
    private ArrayList<CardModel> cardArray;
    private String cardId, calendarMonth, calendarYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_overview);

        ButterKnife.bind(this);

        presenter = new CardOverviewPresenter(this);

        cardId = getIntent().getStringExtra(parcelable_card);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(card_overview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar calendar = Calendar.getInstance();
        calendarMonth = NumberFormat.getMonth( (calendar.get(Calendar.MONTH)) + 1 );
        calendarYear = String.valueOf( calendar.get(Calendar.YEAR) );

        materialCalendarView.setOnMonthChangedListener(this);
    }

    @Override
    public void OnRequestUserDataSuccessful(String name) {
        textViewUserName.setText(name);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void OnRequestCardSuccessful(ArrayList<CardModel> card) {
        this.cardArray = card;
        layoutCard.setBackground( CardUtils.getBackground(this, card.get(0).getCardColor() ) );
        imageViewSelectedFlag.setImageDrawable( CardUtils.getFlag(this, card.get(0).getCardFlag()) );
        textViewTitleCard.setText(card.get(0).getCardTitle());
        String number = (card_number) + (card.get(0).getFinalDigits());
        textViewCardNumber.setText(number);
        textViewPointsTotal.setText(String.format("%.0f", cardArray.get(0).getPoints()));
    }

    @Override
    public void OnRequestTotalCalculateResult(String value) {
        textViewPrice.setText(value);
    }

    @Override
    public void OnAllowViewExpenses() {
        startActivity(new Intent(this, CardExpensesActivity.class)
                .putExtra(parcelable_expense, calendarMonth+calendarYear)
                .putParcelableArrayListExtra(parcelable_card, cardArray));
    }

    @Override
    public void OnDenyViewExpenses() {
        Toast.makeText(this, view_expenses_denied, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        calendarMonth = NumberFormat.getMonth(date.getMonth());
        calendarYear = String.valueOf(date.getYear());
        presenter.OnStop();
        presenter.OnRequestCardExpenses( cardId, calendarMonth + calendarYear );
    }

    @OnClick(R.id.imageViewInfoPoints)
    public void OnInfoBetterDayClick(){
        Tooltip.show(imageViewInfoPoints, info_points, colorAccent);
    }

    @OnClick(R.id.imageViewInfoAnnuityNotification)
    public void OnInfoAnnuityNotificationsClick(){
        Tooltip.show(imageViewInfoAnnuityNotification, info_annuity_notification, colorAccent);
    }

    @OnClick(R.id.btnExpenses)
    public void OnButtonExpensesClick(){
        presenter.OnCheckExpenses(textViewPrice.getText().toString().trim());
    }

    @OnClick(R.id.btnEditPoints)
    public void OnButtonPointsClick(){
        startActivity(new Intent(this, PointsActivity.class)
                .putExtra(parcelable_card, cardArray.get(0))
        );
    }

    @OnClick(R.id.btnEditCard)
    public void OnButtonEditCardClick(){
        startActivity(new Intent(this, NewCardActivity.class)
                .putExtra(parcelable_card, cardArray.get(0))
                .putStringArrayListExtra(cards_numbers, presenter.GetCardsNumbers())
        );
    }

    @Override
    public void OnRequestCardExpensesFailure(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(card_overview);
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
        presenter.OnRequestUserData();
        presenter.OnRequestCardExpenses(cardId, calendarMonth + calendarYear);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.OnStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.OnDestroy();
    }
}