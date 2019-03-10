package com.dev.marcellocamara.pgm.View.Activities;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.Contract.ICard;
import com.dev.marcellocamara.pgm.Contract.IDialog;
import com.dev.marcellocamara.pgm.Helper.CardColorDialog;
import com.dev.marcellocamara.pgm.Helper.FlagDialog;
import com.dev.marcellocamara.pgm.Presenter.CardPresenter;
import com.dev.marcellocamara.pgm.R;

import java.util.Objects;

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

public class NewCardActivity extends AppCompatActivity implements ICard.View, IDialog.Color, IDialog.Flag{

    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @BindView(R.id.textViewCardNumber) protected TextView textViewCardNumber;
    @BindView(R.id.textViewUserName) protected TextView textViewUserName;

    @BindView(R.id.editTextFinalNumber) protected EditText editTextFinalNumber;
    @BindView(R.id.editTextPaymentDate) protected EditText editTextPaymentDate;

    @BindView(R.id.imageViewCardColor) protected ImageView imageViewCardColor;
    @BindView(R.id.imageViewCardFlag) protected ImageView imageViewCardFlag;
    @BindView(R.id.imageViewSelectedFlag) protected ImageView imageViewSelectedFlag;
    @BindView(R.id.imageViewChip) protected ImageView imageViewChip;

    @BindView(R.id.layoutCard) protected ConstraintLayout layoutCard;

    @BindView(R.id.btnCancel) protected Button btnCancel;
    @BindView(R.id.btnSave) protected Button btnSave;

    @BindDrawable(R.drawable.flag_mastercard) protected Drawable flag_mastercard;
    @BindDrawable(R.drawable.flag_visa) protected Drawable flag_visa;
    @BindDrawable(R.drawable.chip_gold) protected Drawable chip_gold;
    @BindDrawable(R.drawable.chip_silver) protected Drawable chip_silver;
    @BindDrawable(R.drawable.card_yellow) protected Drawable card_yellow;
    @BindDrawable(R.drawable.card_purple) protected Drawable card_purple;
    @BindDrawable(R.drawable.card_green) protected Drawable card_green;
    @BindDrawable(R.drawable.card_grey) protected Drawable card_grey;
    @BindDrawable(R.drawable.card_red) protected Drawable card_red;

    @BindColor(R.color.white) protected int white;
    @BindColor(R.color.black) protected int black;
    @BindColor(R.color.cardYellow) protected int cardYellow;
    @BindColor(R.color.cardPurple) protected int cardPurple;
    @BindColor(R.color.cardGreen) protected int cardGreen;
    @BindColor(R.color.cardGrey) protected int cardGrey;
    @BindColor(R.color.cardRed) protected int cardRed;

    @BindString(R.string.new_card) protected String new_card;

    private ICard.Presenter presenter;
    private int cardColor = 1, cardFlag = 1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(new_card);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new CardPresenter(this);

        textViewCardNumber.setText(
                getText(R.string.card_number) + getString(R.string.card_number_0)
        );
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

    @OnClick(R.id.btnCancel)
    public void OnButtonClick(){
        finish();
    }

    @Override
    public void getSelectedColor(int color) {
        cardColor = color;
        switch (color){
            case 1 : {
                layoutCard.setBackground(card_yellow);
                imageViewCardColor.setColorFilter(cardYellow);
                imageViewChip.setImageDrawable(chip_gold);
                setTextColor(1);
                break;
            }
            case 2 : {
                layoutCard.setBackground(card_purple);
                imageViewCardColor.setColorFilter(cardPurple);
                imageViewChip.setImageDrawable(chip_silver);
                setTextColor(2);
                break;
            }
            case 3 : {
                layoutCard.setBackground(card_green);
                imageViewCardColor.setColorFilter(cardGreen);
                imageViewChip.setImageDrawable(chip_gold);
                setTextColor(1);
                break;
            }
            case 4 : {
                layoutCard.setBackground(card_grey);
                imageViewCardColor.setColorFilter(cardGrey);
                imageViewChip.setImageDrawable(chip_silver);
                setTextColor(2);
                break;
            }
            case 5 : {
                layoutCard.setBackground(card_red);
                imageViewCardColor.setColorFilter(cardRed);
                imageViewChip.setImageDrawable(chip_gold);
                setTextColor(2);
                break;
            }
        }
    }

    private void setTextColor(int color){
        switch (color){
            case 1 : {
                textViewUserName.setTextColor(black);
                textViewCardNumber.setTextColor(black);
                break;
            }
            case 2 : {
                textViewUserName.setTextColor(white);
                textViewCardNumber.setTextColor(white);
                break;
            }
        }
    }

    @Override
    public void getFlag(int flag) {
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
        }
    }

    @OnTextChanged(R.id.editTextFinalNumber)
    protected void onTextChanged(CharSequence text) {
        presenter.OnGetFinalNumbers(text.toString());
    }

    @Override
    public void OnRequestUserDataSuccessful(String name) {
        textViewUserName.setText(name);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnSetZeroFinalNumbers() {
        textViewCardNumber.setText(
                getText(R.string.card_number) + getString(R.string.card_number_0)
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnSetOneFinalNumber(String num) {
        textViewCardNumber.setText(
                getText(R.string.card_number) + getString(R.string.card_number_1) + num
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnSetTwoFinalNumbers(String num) {
        textViewCardNumber.setText(
                getText(R.string.card_number) + getString(R.string.card_number_2) + num
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnSetThreeFinalNumbers(String num) {
        textViewCardNumber.setText(
                getText(R.string.card_number) + getString(R.string.card_number_3) + num
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnSetFourFinalNumbers(String num) {
        textViewCardNumber.setText(
                getText(R.string.card_number) + num
        );
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