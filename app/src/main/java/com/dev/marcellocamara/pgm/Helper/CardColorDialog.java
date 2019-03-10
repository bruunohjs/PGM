package com.dev.marcellocamara.pgm.Helper;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dev.marcellocamara.pgm.Contract.IDialog;
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

    @BindView(R.id.btnCancel) protected Button btnCancel;

    private IDialog.Color colorListener;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.dialog_card_color, container, false);

        unbinder = ButterKnife.bind(this, itemView);

        return itemView;
    }

    @OnClick({R.id.layoutYellow, R.id.layoutPurple, R.id.layoutGreen, R.id.layoutGrey, R.id.layoutRed})
    public void OnImageViewClick(ConstraintLayout v){
        switch (v.getId()){
            case R.id.layoutYellow : {
                colorListener.getSelectedColor(1);
                getDialog().dismiss();
                break;
            }
            case R.id.layoutPurple : {
                colorListener.getSelectedColor(2);
                getDialog().dismiss();
                break;
            }
            case R.id.layoutGreen : {
                colorListener.getSelectedColor(3);
                getDialog().dismiss();
                break;
            }
            case R.id.layoutGrey : {
                colorListener.getSelectedColor(4);
                getDialog().dismiss();
                break;
            }
            case R.id.layoutRed : {
                colorListener.getSelectedColor(5);
                getDialog().dismiss();
                break;
            }
        }
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