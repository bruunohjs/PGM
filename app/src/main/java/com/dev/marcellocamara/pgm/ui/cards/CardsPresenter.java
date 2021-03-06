package com.dev.marcellocamara.pgm.ui.cards;

import static com.dev.marcellocamara.pgm.utils.Constants.MAX_NUMBER_OF_CARDS;

import com.dev.marcellocamara.pgm.ui.ITaskListener;
import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.model.DatabaseModel;

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
    }

    @Override
    public void OnCountMaxCards(ArrayList<CardModel> cards) {
        if (cards.size() < MAX_NUMBER_OF_CARDS){
            ArrayList<String> cardsNumbers = new ArrayList<>();
            for (CardModel card : list){
                cardsNumbers.add(card.getFinalDigits());
            }
            view.AllowAddNewCard(cardsNumbers);
        }else {
            view.DenyAddNewCard();
        }
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