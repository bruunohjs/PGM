package com.dev.marcellocamara.pgm.ui.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.marcellocamara.pgm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/***
    marcellocamara@id.uff.br
            2019
***/

public class CardFlagDialog extends DialogFragment {

    @BindView(R.id.layoutVisa) protected ConstraintLayout layoutVisa;
    @BindView(R.id.layoutMasterCard) protected ConstraintLayout layoutMasterCard;
    @BindView(R.id.layoutElo) protected ConstraintLayout layoutElo;
    @BindView(R.id.layoutAmericanExpress) protected ConstraintLayout layoutAmericanExpress;
    @BindView(R.id.layoutDinnersClub) protected ConstraintLayout layoutDinnersClub;
    @BindView(R.id.layoutHipercard) protected ConstraintLayout layoutHipercard;

    private IDialog.Flag flagListener;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.dialog_flags, container, false);

        unbinder = ButterKnife.bind(this, itemView);

        return itemView;
    }

    @OnClick(R.id.layoutVisa)
    public void OnVisaClick(){
        flagListener.getFlag(1);
        getDialog().dismiss();
    }

    @OnClick(R.id.layoutMasterCard)
    public void OnMasterCardClick(){
        flagListener.getFlag(2);
        getDialog().dismiss();
    }

    @OnClick(R.id.layoutElo)
    public void OnEloClick(){
        flagListener.getFlag(3);
        getDialog().dismiss();
    }

    @OnClick(R.id.layoutAmericanExpress)
    public void OnAmericanExpressClick(){
        flagListener.getFlag(4);
        getDialog().dismiss();
    }

    @OnClick(R.id.layoutDinnersClub)
    public void OnDinnersClubClick(){
        flagListener.getFlag(5);
        getDialog().dismiss();
    }

    @OnClick(R.id.layoutHipercard)
    public void OnHipercardClick(){
        flagListener.getFlag(6);
        getDialog().dismiss();
    }

    @OnClick(R.id.btnCancel)
    public void OnButtonClick(){
        getDialog().dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            flagListener = (IDialog.Flag) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "Must implement IDialog.Flag");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}