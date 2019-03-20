package com.dev.marcellocamara.pgm.View.Activities;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.Contract.ICardOverview;
import com.dev.marcellocamara.pgm.Helper.CardHelper;
import com.dev.marcellocamara.pgm.Helper.TooltipHelper;
import com.dev.marcellocamara.pgm.Model.CardModel;
import com.dev.marcellocamara.pgm.Presenter.CardOverviewPresenter;
import com.dev.marcellocamara.pgm.R;

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

public class CardOverviewActivity extends AppCompatActivity implements ICardOverview.View {

    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @BindView(R.id.textViewTitleCard) protected TextView textViewTitleCard;
    @BindView(R.id.textViewCardNumber) protected TextView textViewCardNumber;
    @BindView(R.id.textViewUserName) protected TextView textViewUserName;

    @BindView(R.id.layoutCard) protected ConstraintLayout layoutCard;

    @BindView(R.id.imageViewSelectedFlag) protected ImageView imageViewSelectedFlag;
    @BindView(R.id.imageViewInfoPoints) protected ImageView imageViewInfoPoints;
    @BindView(R.id.imageViewInfoAnnuityNotification) protected ImageView imageViewInfoAnnuityNotification;

    @BindView(R.id.btnEditCard) protected Button btnEditCard;

    @BindString(R.string.card_overview) protected String card_overview;
    @BindString(R.string.parcelable_card) protected String parcelable_card;
    @BindString(R.string.card_number) protected String card_number;
    @BindString(R.string.info_best_day) protected String info_best_day;

    @BindColor(R.color.colorAccent) protected int colorAccent;

    private ICardOverview.Presenter presenter;
    private CardModel card;
    private String cardId;

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
    }

    @Override
    public void OnRequestUserDataSuccessful(String name) {
        textViewUserName.setText(name);
    }

    @Override
    public void OnRequestCardSuccessful(CardModel card) {
        this.card = card;
        layoutCard.setBackground( CardHelper.getBackground(this, card.getCardColor() ) );
        imageViewSelectedFlag.setImageDrawable( CardHelper.getFlag(this, card.getCardFlag()) );
        textViewTitleCard.setText(card.getCardTitle());
        String number = (card_number) + (card.getFinalDigits());
        textViewCardNumber.setText(number);
    }

    @OnClick(R.id.imageViewInfoPoints)
    public void OnInfoBetterDayClick(){
        TooltipHelper.show(imageViewInfoPoints, "Points information", colorAccent);
    }

    @OnClick(R.id.imageViewInfoAnnuityNotification)
    public void OnInfoAnnuityNotificationsClick(){
        TooltipHelper.show(imageViewInfoAnnuityNotification, "Annuity notification information", colorAccent);
    }

    @OnClick(R.id.btnEditCard)
    public void OnButtonEditCardClick(){
        startActivity(new Intent(this, NewCardActivity.class)
                .putExtra(parcelable_card, this.card));
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
        presenter.OnRequestCard(cardId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.OnDestroy();
    }
}