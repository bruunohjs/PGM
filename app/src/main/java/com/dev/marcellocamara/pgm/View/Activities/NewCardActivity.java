package com.dev.marcellocamara.pgm.View.Activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.Contract.INewCard;
import com.dev.marcellocamara.pgm.Contract.IDialog;
import com.dev.marcellocamara.pgm.Helper.CardColorDialog;
import com.dev.marcellocamara.pgm.Helper.FlagDialog;
import com.dev.marcellocamara.pgm.Presenter.NewCardPresenter;
import com.dev.marcellocamara.pgm.R;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

import butterknife.BindColor;
import butterknife.BindDrawable;
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
    @BindView(R.id.editTextPaymentDate) protected EditText editTextPaymentDate;
    @BindView(R.id.editTextAnnuity) protected EditText editTextAnnuity;

    @BindView(R.id.imageViewCardColor) protected ImageView imageViewCardColor;
    @BindView(R.id.imageViewCardFlag) protected ImageView imageViewCardFlag;
    @BindView(R.id.imageViewSelectedFlag) protected ImageView imageViewSelectedFlag;
    @BindView(R.id.imageViewInfoFinalDigits) protected ImageView imageViewInfoFinalDigits;
    @BindView(R.id.imageViewInfoBetterDay) protected ImageView imageViewInfoBetterDay;
    @BindView(R.id.imageViewInfoAnnuity) protected ImageView imageViewInfoAnnuity;

    @BindView(R.id.layoutCard) protected ConstraintLayout layoutCard;

    @BindView(R.id.btnCancel) protected Button btnCancel;
    @BindView(R.id.btnSave) protected Button btnSave;

    @BindDrawable(R.drawable.flag_mastercard) protected Drawable flag_mastercard;
    @BindDrawable(R.drawable.flag_visa) protected Drawable flag_visa;
    @BindDrawable(R.drawable.flag_elo) protected Drawable flag_elo;
    @BindDrawable(R.drawable.chip_gold) protected Drawable chip_gold;
    @BindDrawable(R.drawable.card_yellow) protected Drawable card_yellow;
    @BindDrawable(R.drawable.card_purple) protected Drawable card_purple;
    @BindDrawable(R.drawable.card_green) protected Drawable card_green;
    @BindDrawable(R.drawable.card_grey) protected Drawable card_grey;
    @BindDrawable(R.drawable.card_red) protected Drawable card_red;

    @BindColor(R.color.cardYellow) protected int cardYellow;
    @BindColor(R.color.cardPurple) protected int cardPurple;
    @BindColor(R.color.cardGreen) protected int cardGreen;
    @BindColor(R.color.cardGrey) protected int cardGrey;
    @BindColor(R.color.cardRed) protected int cardRed;

    @BindString(R.string.new_card) protected String new_card;
    @BindString(R.string.card_number) protected String card_number;
    @BindString(R.string.card_number_0) protected String card_number_0;
    @BindString(R.string.card_number_1) protected String card_number_1;
    @BindString(R.string.card_number_2) protected String card_number_2;
    @BindString(R.string.card_number_3) protected String card_number_3;
    @BindString(R.string.card_title_empty) protected String card_title_empty;
    @BindString(R.string.card_number_empty) protected String card_number_empty;
    @BindString(R.string.date_empty) protected String date_empty;
    @BindString(R.string.date_invalid) protected String date_invalid;
    @BindString(R.string.annuity_empty) protected String annuity_empty;
    @BindString(R.string.annuity_invalid) protected String annuity_invalid;
    @BindString(R.string.close) protected String close;
    @BindString(R.string.adding_new_card) protected String adding_new_card;
    @BindString(R.string.new_card_success) protected String new_card_success;

    private INewCard.Presenter presenter;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private int cardColor = 2 /*Purple*/, cardFlag = 1 /*MasterCard*/;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(new_card);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new NewCardPresenter(this);

        textViewCardNumber.setText(card_number + card_number_0);

        builder = new AlertDialog.Builder(this);
        builder.setTitle(new_card);
        builder.setCancelable(false);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(adding_new_card)
                .setCancelable(false)
                .build();
    }

    @OnClick({R.id.imageViewCardColor, R.id.imageViewCardFlag})
    public void OnImageViewClick(ImageView v){
        switch (v.getId()){
            case R.id.imageViewCardColor : {
                CardColorDialog cardColor = new CardColorDialog();
                cardColor.show(getSupportFragmentManager(), "SelectColor");
                break;
            }
            case R.id.imageViewCardFlag : {
                FlagDialog flag = new FlagDialog();
                flag.show(getSupportFragmentManager(),"SelectFlag");
            }
        }
    }

    @OnClick({R.id.btnSave, R.id.btnCancel})
    public void OnButtonClick(Button button){
        switch (button.getId()){
            case R.id.btnSave : {
                editTextFinalNumber.clearFocus();
                editTextPaymentDate.clearFocus();
                presenter.OnAddCard(
                        editTextCardTitle.getText().toString().trim(),
                        editTextFinalNumber.getText().toString().trim(),
                        editTextPaymentDate.getText().toString().trim(),
                        editTextAnnuity.getText().toString().trim(),
                        cardColor,
                        cardFlag
                );
                UIUtil.hideKeyboard(this);
                break;
            }
            case R.id.btnCancel : {
                finish();
                break;
            }
        }
    }

    @Override
    public void getSelectedColor(int color) {
        //TODO : Class Helper to change the background
        cardColor = color;
        switch (color){
            case 1 : {
                layoutCard.setBackground(card_yellow);
                imageViewCardColor.setColorFilter(cardYellow);
                break;
            }
            case 2 : {
                layoutCard.setBackground(card_purple);
                imageViewCardColor.setColorFilter(cardPurple);
                break;
            }
            case 3 : {
                layoutCard.setBackground(card_green);
                imageViewCardColor.setColorFilter(cardGreen);
                break;
            }
            case 4 : {
                layoutCard.setBackground(card_grey);
                imageViewCardColor.setColorFilter(cardGrey);
                break;
            }
            case 5 : {
                layoutCard.setBackground(card_red);
                imageViewCardColor.setColorFilter(cardRed);
                break;
            }
        }
    }

    @Override
    public void getFlag(int flag) {
        //TODO : Class Helper to change the flag
        cardFlag = flag;
        switch (flag){
            case 1 : {
                imageViewCardFlag.setImageDrawable(flag_mastercard);
                imageViewSelectedFlag.setImageDrawable(flag_mastercard);
                break;
            }
            case 2 : {
                imageViewCardFlag.setImageDrawable(flag_visa);
                imageViewSelectedFlag.setImageDrawable(flag_visa);
                break;
            }
            case 3 : {
                imageViewCardFlag.setImageDrawable(flag_elo);
                imageViewSelectedFlag.setImageDrawable(flag_elo);
                break;
            }
        }
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
    public void OnDateInvalid() {
        builder.setMessage(date_empty);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnAnnuityInvalid() {
        builder.setMessage(annuity_empty);
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
    public void OnAnnuityInvalidValue() {
        builder.setMessage(annuity_invalid);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnAddCardSuccess() {
        builder.setMessage(new_card_success);
        builder.setPositiveButton(close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
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
        presenter.OnRequestUserData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.OnDestroy();
    }
}