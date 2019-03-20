package com.dev.marcellocamara.pgm.Presenter;

import com.dev.marcellocamara.pgm.Contract.ICardOverview;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Model.CardModel;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;

import java.util.ArrayList;

/***
    marcellocamara@id.uff.br
            2019
***/

public class CardOverviewPresenter implements ICardOverview.Presenter, ITaskListener {

    private ICardOverview.View view;
    private ICardOverview.Model model;
    private ArrayList<CardModel> cardList;
    private String uniqueId;

    public CardOverviewPresenter(ICardOverview.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnRequestUserData(){
        view.OnRequestUserDataSuccessful(model.GetUserDisplayName());
    }

    @Override
    public void OnRequestCard(String uniqueId) {
        this.cardList = model.DoRecoverCards();
        this.uniqueId = uniqueId;
    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

    private void checkCardUpdate() {
        for (CardModel card : cardList){
            if (card.getUniqueId().equals(uniqueId)){
                view.OnRequestCardSuccessful(card);
            }
        }
    }

    @Override
    public void OnSuccess() {
        if (view != null){
            checkCardUpdate();
        }
    }

    @Override
    public void OnError(String message) {

    }
}