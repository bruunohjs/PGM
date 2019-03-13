package com.dev.marcellocamara.pgm.Presenter;

import com.dev.marcellocamara.pgm.Contract.ICards;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Model.CardModel;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;

import java.util.ArrayList;

/***
    marcellocamara@id.uff.br
            2019
***/

public class CardsPresenter implements ICards.Presenter, ITaskListener {

    private ICards.View view;
    private ICards.Model model;
    private ArrayList<CardModel> list;

    public CardsPresenter(ICards.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnRequestUserData() {
        view.OnRequestUserDataSuccessful(model.GetUserDisplayName());
    }

    @Override
    public void OnRequestCards() {
        view.ShowProgress();
        this.list = model.DoRecoverCards();
        OnSuccess();
    }

    @Override
    public void OnStop() {
        model.RemoveCardsEventListener();
    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

    @Override
    public void OnSuccess() {
        if (view != null){
            view.HideProgress();
            view.OnRequestCardsResult(list);
        }
    }

    @Override
    public void OnError(String message) {
        if (view != null){
            view.HideProgress();
        }
    }
}