package com.dev.marcellocamara.pgm.ui.points;

import android.annotation.SuppressLint;

import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.model.DatabaseModel;
import com.dev.marcellocamara.pgm.ui.ITaskListener;
import com.dev.marcellocamara.pgm.utils.NumberFormat;

import static com.dev.marcellocamara.pgm.utils.Constants.MAX_CARD_POINTS;
import static com.dev.marcellocamara.pgm.utils.InternetConnection.hasInternet;

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
            int result = card.getPoints() + Integer.parseInt(value);
            if (result > MAX_CARD_POINTS){
                view.OnAddPointsFailure();
            }else {
                if (hasInternet()){
                    view.ShowProgress();
                    card.setPoints(result);
                    model.DoUpdateCard(card);
                }else {
                    view.OnInternetFailure();
                }
            }
        }
    }

    @Override
    public void OnSubtractPoints(String value) {
        if ( value.isEmpty() || (Double.parseDouble(value) == 0) ){
            view.OnValueEmpty();
        }else {
            int result = card.getPoints() - Integer.parseInt(value);
            if (result < 0){
                view.OnSubtractPointsFailure();
            }else {
                if (hasInternet()){
                    card.setPoints(result);
                    view.ShowProgress();
                    model.DoUpdateCard(card);
                }else {
                    view.OnInternetFailure();
                }
            }
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void OnSuccess() {
        if (view != null){
            view.HideProgress();
            view.OnUpdatePointsSuccessful( NumberFormat.getIntSeparated(card.getPoints()) );
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