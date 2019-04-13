package com.dev.marcellocamara.pgm.ui.new_expense;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.utils.CardUtils;
import com.dev.marcellocamara.pgm.utils.NumberFormat;
import com.dev.marcellocamara.pgm.utils.Tooltip;
import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static com.dev.marcellocamara.pgm.utils.Constants.MAX_PRICE_EXPENSE;
import static com.dev.marcellocamara.pgm.utils.Constants.parcelable_card;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import dmax.dialog.SpotsDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/***
    marcellocamara@id.uff.br
            2019
***/

public class NewExpenseActivity extends AppCompatActivity implements INewExpense.View {

    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @BindView(R.id.textViewDate) protected TextView textViewDate;
    @BindView(R.id.textViewInstallment) protected TextView textViewInstallment;
    @BindView(R.id.textViewCard) protected TextView textViewCard;

    @BindView(R.id.editTextTitle) protected EditText editTextTitle;
    @BindView(R.id.editTextDescription) protected EditText editTextDescription;
    @BindView(R.id.editTextPrice) protected EditText editTextPrice;

    @BindView(R.id.layoutSelectDate) protected LinearLayout layoutSelectDate;
    @BindView(R.id.layoutSelectInstallment) protected LinearLayout layoutSelectInstallment;
    @BindView(R.id.layoutSelectCard) protected LinearLayout layoutSelectCard;

    @BindView(R.id.imageViewCard) protected ImageView imageViewCard;
    @BindView(R.id.imageViewInfoDate) protected ImageView imageViewInfoDate;
    @BindView(R.id.imageViewInfoPrice) protected ImageView imageViewInfoPrice;

    @BindView(R.id.adView) protected AdView adView;

    @BindString(R.string.new_expense) protected String title;
    @BindString(R.string.installments_number) protected String installments_number;
    @BindString(R.string.select_card) protected String select_card;
    @BindString(R.string.empty_title) protected String empty_title;
    @BindString(R.string.empty_description) protected String empty_description;
    @BindString(R.string.empty_price) protected String empty_price;
    @BindString(R.string.info_date) protected String info_date;
    @BindString(R.string.max_price) protected String max_price;
    @BindString(R.string.coin_type) protected String coin_type;
    @BindString(R.string.coin_sub_unity) protected String coin_sub_unity;
    @BindString(R.string.max_price_unity) protected String max_price_unity;
    @BindString(R.string.view_overview_installments) protected String installmentsX;
    @BindString(R.string.close) protected String close;
    @BindString(R.string.adding_expense) protected String alertDialogMessage;
    @BindString(R.string.adding_expense_success) protected String alertDialogSuccess;
    @BindString(R.string.no_internet) protected String no_internet;

    @BindColor(R.color.colorAccent) protected int colorAccent;

    private INewExpense.Presenter expensePresenter;
    private ArrayList<CardModel> cardsList = new ArrayList<>();
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder ;
    private CharSequence charSequence[];
    private int installments = 1, card = 0, calendarDay, calendarMonth, calendarYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cardsList = getIntent().getParcelableArrayListExtra(parcelable_card);

        expensePresenter = new NewExpensePresenter(this);
        expensePresenter.OnRequestCardSequence(cardsList);

        textViewCard.setText(cardsList.get(0).getFinalDigits());
        imageViewCard.setColorFilter(CardUtils.getColor(this, cardsList.get(0).getCardColor()));

        Calendar calendar = Calendar.getInstance();
        calendarDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendarMonth = calendar.get(Calendar.MONTH);
        calendarYear = calendar.get(Calendar.YEAR);
        expensePresenter.OnCalculateDate(calendarDay,calendarMonth,calendarYear);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(alertDialogMessage)
                .setCancelable(false)
                .build();

        builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(false);

        adView.loadAd(new AdRequest.Builder().build());
    }

    @OnClick(R.id.layoutSelectDate)
    public void OnSelectDateClick(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                expensePresenter.OnCalculateDate(dayOfMonth,month,year);
            }
        },calendarYear,calendarMonth,calendarDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.layoutSelectInstallment)
    public void OnSelectInstallmentsClick(){
        AlertDialog.Builder builderInstallments = new AlertDialog.Builder(this);
        builderInstallments.setCancelable(false);
        builderInstallments.setTitle(installments_number);
        builderInstallments.setItems(R.array.installments, new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                installments = (which + 1);
                textViewInstallment.setText(installments + installmentsX);
            }
        });
        builderInstallments.show();
    }

    @OnClick(R.id.layoutSelectCard)
    public void voidOnSelectCardClick(){
        AlertDialog.Builder builderInstallments = new AlertDialog.Builder(this);
        builderInstallments.setCancelable(false);
        builderInstallments.setTitle(select_card);
        builderInstallments.setItems(charSequence, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                card = which;
                textViewCard.setText(cardsList.get(which).getFinalDigits());
                imageViewCard.setColorFilter(
                        CardUtils.getColor(getApplicationContext(), cardsList.get(which).getCardColor())
                );
            }
        });
        builderInstallments.show();
    }

    @OnClick(R.id.imageViewInfoDate)
    public void OnInfoDateClick(){
        Tooltip.show(imageViewInfoDate, Gravity.BOTTOM, info_date, colorAccent);
    }

    @OnClick(R.id.imageViewInfoPrice)
    public void OnInfoPriceClick(){
        Tooltip.show(imageViewInfoPrice, Gravity.TOP, max_price, colorAccent);
    }

    @OnClick(R.id.btnSave)
    public void OnButtonSaveClick(){
        editTextTitle.clearFocus();
        editTextDescription.clearFocus();
        editTextPrice.clearFocus();
        expensePresenter.OnAddExpense(
                textViewDate.getText().toString().trim(),
                editTextTitle.getText().toString().trim(),
                editTextDescription.getText().toString().trim(),
                editTextPrice.getText().toString().trim(),
                installments,
                cardsList.get(card).getUniqueId(),
                cardsList.get(card).getBetterDayToBuy()
        );
        UIUtil.hideKeyboard(this);
    }

    @Override
    public void OnEmptyTitle() {
        builder.setMessage(empty_title);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnEmptyDescription() {
        builder.setMessage(empty_description);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnEmptyPrice() {
        builder.setMessage(empty_price);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnMaxPrice() {
        builder.setMessage(max_price + coin_type + NumberFormat.getDecimal(MAX_PRICE_EXPENSE) + max_price_unity + coin_sub_unity);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnRequestCharSequenceResult(CharSequence[] charSequence) {
        this.charSequence = charSequence;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnCalculatedDate(String day, String month, String year) {
        textViewDate.setText(day+"/"+month+"/"+year);
    }

    @Override
    public void OnAddExpenseSuccessful() {
        builder.setMessage(alertDialogSuccess);
        builder.setPositiveButton(close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void OnAddExpenseFailure(String message) {
        builder.setMessage(message);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnInternetFailure() {
        builder.setMessage(no_internet);
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
        expensePresenter.OnDestroy();
    }

}