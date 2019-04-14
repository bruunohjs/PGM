package com.dev.marcellocamara.pgm.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.select_flag)
                .setPositiveButton(R.string.cancel, null);

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_flags, null);

        unbinder = ButterKnife.bind(this, view);

        builder.setView(view);

        return builder.create();
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