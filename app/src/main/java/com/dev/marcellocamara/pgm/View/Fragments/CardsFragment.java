package com.dev.marcellocamara.pgm.View.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aniket.mutativefloatingactionbutton.MutativeFab;
import com.dev.marcellocamara.pgm.Adapter.CardsAdapter;
import com.dev.marcellocamara.pgm.Contract.IAdapter;
import com.dev.marcellocamara.pgm.Contract.ICards;
import com.dev.marcellocamara.pgm.Model.CardModel;
import com.dev.marcellocamara.pgm.Presenter.CardsPresenter;
import com.dev.marcellocamara.pgm.R;
import com.dev.marcellocamara.pgm.View.Activities.NewCardActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

/***
    marcellocamara@id.uff.br
            2019
***/

public class CardsFragment extends Fragment implements ICards.View, IAdapter {

    @BindView(R.id.mutativeFAB) protected MutativeFab mutativeFab;

    @BindView(R.id.viewPager) protected ViewPager viewPager;

    private ICards.Presenter cardsPresenter;
    private List<CardModel> list;
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
        startActivity(new Intent(getContext(), NewCardActivity.class));
    }

    @Override
    public void OnRequestUserDataSuccessful(String name) {
        this.userName = name;
    }

    @Override
    public void OnRequestExpensesResult(List<CardModel> list) {
        this.list = list;
        CardsAdapter adapter = new CardsAdapter(list, userName, getContext(), this);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void OnItemClick(int position) {
        Toast.makeText(getContext(),
                "Item clicked: " + position +
                "\nCard: " + list.get(position).getFinalDigits(), Toast.LENGTH_SHORT).show();
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