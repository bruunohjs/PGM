package com.dev.marcellocamara.pgm.ui.points;

import android.annotation.SuppressLint;

import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.model.DatabaseModel;
import com.dev.marcellocamara.pgm.ui.ITaskListener;

import java.util.Objects;

/***
    marcellocamara@id.uff.br
            2019
***/

public class PointsPresenter implements IPoints.Presenter, ITaskListener {

    private IPoints.View view;
    private IPoints.Model model;
    private CardModel card;

    public PointsPresenter(IPoints.View view, CardModel card){
        this.view = view;
        this.card = card;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnAddPoints(String value) {
        if ( value.isEmpty() || (Double.parseDouble(value) == 0) ){
            view.OnValueEmpty();
        }else {
            double result = card.getPoints() + Double.parseDouble(value);
            if (result > 999999999){
                view.OnAddPointsFailure();
            }else {
                view.ShowProgress();
                card.setPoints(result);
                model.DoUpdateCardPoints(card);
            }
        }
    }

    @Override
    public void OnSubtractPoints(String value) {
        if ( value.isEmpty() || (Double.parseDouble(value) == 0) ){
            view.OnValueEmpty();
        }else {
            double result = card.getPoints() - Double.parseDouble(value);
            if (result < 0){
                view.OnSubtractPointsFailure();
            }else {
                card.setPoints(result);
                view.ShowProgress();
                model.DoUpdateCardPoints(card);
            }
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void OnSuccess() {
        if (view != null){
            view.HideProgress();
            view.OnUpdatePointsSuccessful(
                    String.format("%.0f", Objects.requireNonNull(card).getPoints()));
        }
    }

    @Override
    public void OnError(String message) {
        if (view != null){
            view.HideProgress();
            view.OnUpdatePointsFailure(message);
        }
    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

}