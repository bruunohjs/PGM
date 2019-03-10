package com.dev.marcellocamara.pgm.Presenter;

import com.dev.marcellocamara.pgm.Contract.ICard;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;

/***
    marcellocamara@id.uff.br
            2019
***/

public class CardPresenter implements ICard.Presenter, ITaskListener {

    private ICard.View view;
    private ICard.Model model;

    public CardPresenter(ICard.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnRequestUserData() {
        view.OnRequestUserDataSuccessful(model.GetUserDisplayName());
    }

    @Override
    public void OnGetFinalNumbers(String numbers) {
        if (numbers.length() < 4){
            if (numbers.length() < 3){
                if (numbers.length() < 2){
                    if (numbers.length() < 1){
                        view.OnSetZeroFinalNumbers();
                    }else {
                        view.OnSetOneFinalNumber(numbers);
                    }
                }else {
                    view.OnSetTwoFinalNumbers(numbers);
                }
            }else {
                view.OnSetThreeFinalNumbers(numbers);
            }
        }else {
            view.OnSetFourFinalNumbers(numbers);
        }
    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

    @Override
    public void OnSuccess() {

    }

    @Override
    public void OnError(String message) {

    }
}