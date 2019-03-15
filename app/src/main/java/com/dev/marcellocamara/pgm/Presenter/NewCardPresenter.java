package com.dev.marcellocamara.pgm.Presenter;

import com.dev.marcellocamara.pgm.Contract.INewCard;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;

/***
    marcellocamara@id.uff.br
            2019
***/

public class NewCardPresenter implements INewCard.Presenter, ITaskListener {

    private INewCard.View view;
    private INewCard.Model model;

    public NewCardPresenter(INewCard.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnRequestUserData() {
        view.OnRequestUserDataSuccessful(model.GetUserDisplayName());
    }

    @Override
    public void OnAddCard(String title, String numbers, String date, int cardColor, int cardFlag) {

        if (title.isEmpty() || numbers.isEmpty() || date.isEmpty()){

            if (title.isEmpty()){
                view.OnTitleEmpty();
            }else if (numbers.isEmpty()){
                view.OnFinalNumbersInvalid();
            }else {
                view.OnDateInvalid();
            }

        }else if ( (numbers.length() < 4) || (date.length() < 2) ){

            if (numbers.length() < 4){
                view.OnFinalNumbersInvalid();
            }else {
                view.OnDateInvalid();
            }

        }else if ( date.equals("00") || (Integer.parseInt(date) > 31) ){

            view.OnDateInvalidValue();

        }else {
            view.ShowProgress();
            model.DoAddCard(title, numbers, date, cardColor, cardFlag);
        }

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
        if ( view != null){
            view.HideProgress();
            view.OnAddCardSuccess();
        }
    }

    @Override
    public void OnError(String message) {
        if ( view != null){
            view.HideProgress();
            view.OnAddCardFailure(message);
        }
    }
}