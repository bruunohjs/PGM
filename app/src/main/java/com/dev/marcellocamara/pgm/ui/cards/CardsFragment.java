package com.dev.marcellocamara.pgm.ui.cards;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.dev.marcellocamara.pgm.utils.Constants.MAX_NUMBER_OF_CARDS;
import static com.dev.marcellocamara.pgm.utils.Constants.parcelable_card;
import static com.dev.marcellocamara.pgm.utils.Constants.cards_numbers;

import com.dev.marcellocamara.pgm.adapter.CardsAdapter;
import com.dev.marcellocamara.pgm.adapter.IAdapter;
import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.R;
import com.dev.marcellocamara.pgm.ui.card_overview.CardOverviewActivity;
import com.dev.marcellocamara.pgm.ui.new_card.NewCardActivity;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

/***
    marcellocamara@id.uff.br
            2019
***/

public class CardsFragment extends Fragment implements ICards.View, IAdapter {

    @BindView(R.id.viewPager) protected ViewPager viewPager;

    @BindString(R.string.new_card) protected String new_card;
    @BindString(R.string.close) protected String close;
    @BindString(R.string.cards_limit_reached) protected String cards_limit_reached;
    @BindString(R.string.cards_limit_reached_final) protected String cards_limit_reached_final;

    private ICards.Presenter cardsPresenter;
    private ArrayList<CardModel> cards;
    private AlertDialog alertDialog;
    private String userName;

    public CardsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cards, container, false);

        ButterKnife.bind(this, view);

        cardsPresenter = new CardsPresenter(this);

        alertDialog = new SpotsDialog.Builder()
                .setContext(view.getContext())
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(R.string.loading_cards)
                .setCancelable(false)
                .build();

        return view;
    }

    @OnClick(R.id.mutativeFAB)
    public void OnFloatingActionButtonClick(){
        cardsPresenter.OnCountMaxCards(cards);
    }

    @Override
    public void OnRequestUserDataSuccessful(String name) {
        this.userName = name;
    }

    @Override
    public void OnRequestCardsResult(ArrayList<CardModel> cards) {
        this.cards = cards;
        CardsAdapter adapter = new CardsAdapter(cards, userName, getContext(), this);
        int margin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics()
        );
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void AllowAddNewCard(ArrayList<String> cardsNumbers) {
        startActivity(new Intent(getContext(), NewCardActivity.class)
                .putStringArrayListExtra(cards_numbers, cardsNumbers)
        );
    }

    @Override
    public void DenyAddNewCard() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(new_card);
        builder.setMessage(cards_limit_reached + MAX_NUMBER_OF_CARDS + cards_limit_reached_final);
        builder.setCancelable(false);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnItemClick(int position) {
        startActivity(new Intent(getContext(), CardOverviewActivity.class)
                .putExtra(parcelable_card, cards.get(position).getUniqueId()));
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
    public void onStart() {
        super.onStart();
        cardsPresenter.OnRequestUserData();
        cardsPresenter.OnRequestCards();
    }

    @Override
    public void onStop() {
        super.onStop();
        cardsPresenter.OnStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cardsPresenter.OnDestroy();
    }

}