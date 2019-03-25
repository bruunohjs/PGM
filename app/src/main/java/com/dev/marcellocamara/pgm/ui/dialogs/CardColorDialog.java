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

public class CardColorDialog extends DialogFragment {

    @BindView(R.id.layoutYellow) protected ConstraintLayout layoutYellow;
    @BindView(R.id.layoutPurple) protected ConstraintLayout layoutPurple;
    @BindView(R.id.layoutGreen) protected ConstraintLayout layoutGreen;
    @BindView(R.id.layoutGrey) protected ConstraintLayout layoutGrey;
    @BindView(R.id.layoutRed) protected ConstraintLayout layoutRed;
    @BindView(R.id.layoutBlue) protected ConstraintLayout layoutBlue;

    private IDialog.Color colorListener;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.dialog_card_color, container, false);

        unbinder = ButterKnife.bind(this, itemView);

        return itemView;
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

    @OnClick(R.id.btnCancel)
    public void OnButtonClick(){
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