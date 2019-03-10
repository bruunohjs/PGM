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

public class FlagDialog extends DialogFragment {

    @BindView(R.id.layoutMasterCard) protected ConstraintLayout layoutMasterCard;
    @BindView(R.id.layoutVisa) protected ConstraintLayout layoutVisa;
    @BindView(R.id.layoutElo) protected ConstraintLayout layoutElo;

    @BindView(R.id.btnCancel) protected Button btnCancel;

    private IDialog.Flag flagListener;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.dialog_flags, container, false);

        unbinder = ButterKnife.bind(this, itemView);

        return itemView;
    }

    @OnClick({R.id.layoutMasterCard, R.id.layoutVisa, R.id.layoutElo})
    public void OnLayoutClick(ConstraintLayout v){
        switch (v.getId()){
            case R.id.layoutMasterCard : {
                flagListener.getFlag(1);
                getDialog().dismiss();
                break;
            }
            case R.id.layoutVisa : {
                flagListener.getFlag(2);
                getDialog().dismiss();
                break;
            }
            case R.id.layoutElo : {
                flagListener.getFlag(3);
                getDialog().dismiss();
                break;
            }
        }
    }

    @OnClick({R.id.btnCancel})
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