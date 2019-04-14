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

public class CardColorDialog extends DialogFragment {

    @BindView(R.id.layoutYellow) protected ConstraintLayout layoutYellow;
    @BindView(R.id.layoutPurple) protected ConstraintLayout layoutPurple;
    @BindView(R.id.layoutGreen) protected ConstraintLayout layoutGreen;
    @BindView(R.id.layoutGrey) protected ConstraintLayout layoutGrey;
    @BindView(R.id.layoutRed) protected ConstraintLayout layoutRed;
    @BindView(R.id.layoutBlue) protected ConstraintLayout layoutBlue;

    private IDialog.Color colorListener;
    private Unbinder unbinder;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.select_card_color)
                .setPositiveButton(R.string.cancel, null);

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_card_color, null);

        unbinder = ButterKnife.bind(this, view);

        builder.setView(view);

        return builder.create();
    }

    @OnClick(R.id.layoutYellow)
    public void OnYellowClick(){
        colorListener.getSelectedColor(1);
        getDialog().dismiss();
    }

    @OnClick(R.id.layoutPurple)
    public void OnPurpleClick(){
        colorListener.getSelectedColor(2);
        getDialog().dismiss();
    }

    @OnClick(R.id.layoutGreen)
    public void OnGreenClick(){
        colorListener.getSelectedColor(3);
        getDialog().dismiss();
    }

    @OnClick(R.id.layoutGrey)
    public void OnGreyClick(){
        colorListener.getSelectedColor(4);
        getDialog().dismiss();
    }

    @OnClick(R.id.layoutRed)
    public void OnRedClick(){
        colorListener.getSelectedColor(5);
        getDialog().dismiss();
    }

    @OnClick(R.id.layoutBlue)
    public void OnBlueClick(){
        colorListener.getSelectedColor(6);
        getDialog().dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            colorListener = (IDialog.Color) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "Must implement IDialog.Color");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}