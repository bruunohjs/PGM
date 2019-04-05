package com.dev.marcellocamara.pgm.ui.new_card;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.ui.dialogs.IDialog;
import com.dev.marcellocamara.pgm.utils.Tooltip;
import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.ui.dialogs.CardColorDialog;
import com.dev.marcellocamara.pgm.utils.CardUtils;
import com.dev.marcellocamara.pgm.ui.dialogs.CardFlagDialog;
import com.dev.marcellocamara.pgm.R;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.ArrayList;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/***
    marcellocamara@id.uff.br
            2019
***/

public class NewCardActivity extends AppCompatActivity implements INewCard.View, IDialog.Color, IDialog.Flag{

    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @BindView(R.id.textViewTitleCard) protected TextView textViewTitleCard;
    @BindView(R.id.textViewCardNumber) protected TextView textViewCardNumber;
    @BindView(R.id.textViewUserName) protected TextView textViewUserName;

    @BindView(R.id.editTextCardTitle) protected EditText editTextCardTitle;
    @BindView(R.id.editTextFinalNumber) protected EditText editTextFinalNumber;
    @BindView(R.id.editTextBestBuyDate) protected EditText editTextBestBuyDate;

    @BindView(R.id.imageViewCardColor) protected ImageView imageViewCardColor;
    @BindView(R.id.imageViewCardFlag) protected ImageView imageViewCardFlag;
    @BindView(R.id.imageViewSelectedFlag) protected ImageView imageViewSelectedFlag;
    @BindView(R.id.imageViewInfoFinalDigits) protected ImageView imageViewInfoFinalDigits;
    @BindView(R.id.imageViewInfoBetterDay) protected ImageView imageViewInfoBetterDay;

    @BindView(R.id.layoutCard) protected ConstraintLayout layoutCard;

    @BindView(R.id.btnSave) protected Button btnSave;
    @BindView(R.id.btnEdit) protected Button btnEdit;

    @BindString(R.string.new_card) protected String new_card;
    @BindString(R.string.update_card) protected String update_card;
    @BindString(R.string.parcelable_card) protected String parcelable_card;
    @BindString(R.string.cards_numbers) protected String cards_numbers;
    @BindString(R.string.card_number) protected String card_number;
    @BindString(R.string.card_number_0) protected String card_number_0;
    @BindString(R.string.card_number_1) protected String card_number_1;
    @BindString(R.string.card_number_2) protected String card_number_2;
    @BindString(R.string.card_number_3) protected String card_number_3;
    @BindString(R.string.card_title_empty) protected String card_title_empty;
    @BindString(R.string.card_number_empty) protected String card_number_empty;
    @BindString(R.string.date_empty) protected String date_empty;
    @BindString(R.string.date_invalid) protected String date_invalid;
    @BindString(R.string.no_changes) protected String no_changes;
    @BindString(R.string.close) protected String close;
    @BindString(R.string.adding_new_card) protected String adding_new_card;
    @BindString(R.string.new_card_success) protected String new_card_success;
    @BindString(R.string.update_success) protected String update_success;
    @BindString(R.string.card_already_exists) protected String card_already_exists;
    @BindString(R.string.info_digits) protected String info_digits;
    @BindString(R.string.info_best_day) protected String info_best_day;

    @BindColor(R.color.colorAccent) protected int colorAccent;

    private INewCard.Presenter presenter;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private ArrayList<String> cardsNumbers;
    private CardModel card;
    private String builderTitle, card_success;
    private int cardColor = 2 /*Purple*/, cardFlag = 2 /*MasterCard*/;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        ButterKnife.bind(this);

        builderTitle = new_card;
        card_success = new_card_success;

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(new_card);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewCardNumber.setText(card_number + card_number_0);

        presenter = new NewCardPresenter(this);
        card = getIntent().getParcelableExtra(parcelable_card);
        cardsNumbers = getIntent().getStringArrayListExtra(cards_numbers);
        presenter.OnCheckCardDataUpdate(card);

        builder = new AlertDialog.Builder(this);
        builder.setTitle(builderTitle);
        builder.setCancelable(false);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(adding_new_card)
                .setCancelable(false)
                .build();
    }

    @OnClick(R.id.imageViewCardColor)
    public void OnImageViewClick(){
        CardColorDialog cardColor = new CardColorDialog();
        cardColor.show(getSupportFragmentManager(), "SelectColor");
    }

    @OnClick(R.id.imageViewCardFlag)
    public void OnCardFlagClick(){
        CardFlagDialog flag = new CardFlagDialog();
        flag.show(getSupportFragmentManager(),"SelectFlag");
    }

    @OnClick(R.id.btnSave)
    public void OnButtonSaveClick(){
        editTextFinalNumber.clearFocus();
        editTextBestBuyDate.clearFocus();
        presenter.OnAddCard(
                editTextCardTitle.getText().toString().trim(),
                editTextFinalNumber.getText().toString().trim(),
                editTextBestBuyDate.getText().toString().trim(),
                cardColor,
                cardFlag
        );
        UIUtil.hideKeyboard(this);
    }

    @OnClick(R.id.btnEdit)
    public void OnButtonEditClick(){
        editTextFinalNumber.clearFocus();
        editTextBestBuyDate.clearFocus();
        presenter.OnUpdateCard(
                card,
                editTextCardTitle.getText().toString().trim(),
                editTextFinalNumber.getText().toString().trim(),
                editTextBestBuyDate.getText().toString().trim(),
                cardColor,
                cardFlag
        );
        UIUtil.hideKeyboard(this);
    }

    @OnClick(R.id.imageViewInfoFinalDigits)
    public void OnInfoFinalDigitsClick(){
        Tooltip.show(imageViewInfoFinalDigits, Gravity.TOP, info_digits, colorAccent);
    }

    @OnClick(R.id.imageViewInfoBetterDay)
    public void OnInfoBetterDayClick(){
        Tooltip.show(imageViewInfoBetterDay, Gravity.TOP, info_best_day, colorAccent);
    }

    @Override
    public void getSelectedColor(int color) {
        cardColor = color;
        layoutCard.setBackground( CardUtils.getBackground(this, color) );
        imageViewCardColor.setColorFilter( CardUtils.getColor(this, color) );
    }

    @Override
    public void getFlag(int flag) {
        cardFlag = flag;
        imageViewCardFlag.setImageDrawable( CardUtils.getFlag(this, cardFlag));
        imageViewSelectedFlag.setImageDrawable( CardUtils.getFlag(this, cardFlag) );
    }

    @OnTextChanged(R.id.editTextCardTitle)
    protected void OnTitleTextChanged(CharSequence text){
        textViewTitleCard.setText(text.toString());
    }

    @OnTextChanged(R.id.editTextFinalNumber)
    protected void onFinalNumbersTextChanged(CharSequence text) {
        presenter.OnGetFinalNumbers(text.toString());
    }

    @Override
    public void OnRequestUserDataSuccessful(String name) {
        textViewUserName.setText(name);
    }

    @Override
    public void OnCheckCardDataUpdateSuccessful() {
        Objects.requireNonNull(getSupportActionBar()).setTitle(update_card);
        builderTitle = update_card;
        card_success = update_success;
        editTextCardTitle.setText(card.getCardTitle());
        editTextFinalNumber.setText(card.getFinalDigits());
        editTextBestBuyDate.setText(card.getBetterDayToBuy());
        getSelectedColor(card.getCardColor());
        getFlag(card.getCardFlag());
        btnSave.setVisibility(View.GONE);
        btnEdit.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnSetZeroFinalNumbers() {
        textViewCardNumber.setText(card_number + card_number_0);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnSetOneFinalNumber(String num) {
        textViewCardNumber.setText(card_number + card_number_1 + num);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnSetTwoFinalNumbers(String num) {
        textViewCardNumber.setText(card_number + card_number_2 + num);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnSetThreeFinalNumbers(String num) {
        textViewCardNumber.setText(card_number + card_number_3 + num);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnSetFourFinalNumbers(String num) {
        textViewCardNumber.setText(card_number + num);
    }

    @Override
    public void OnTitleEmpty() {
        builder.setMessage(card_title_empty);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnFinalNumbersInvalid() {
        builder.setMessage(card_number_empty);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnFinalNumbersAlreadyExists() {
        builder.setMessage(card_already_exists);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnDateInvalid() {
        builder.setMessage(date_empty);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnDateInvalidValue() {
        builder.setMessage(date_invalid);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnAddCardSuccess() {
        builder.setMessage(card_success);
        builder.setPositiveButton(close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void OnAddCardFailure(String message) {
        builder.setMessage(message);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnUpdateCardFailure() {
        builder.setMessage(no_changes);
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
    protected void onStart() {
        super.onStart();
        presenter.OnRequestUserData(cardsNumbers);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.OnDestroy();
    }

}