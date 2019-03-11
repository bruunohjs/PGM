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
    public void OnAddCard(String title, String numbers, String date, String annuity, int cardColor, int cardFlag) {

        if (title.isEmpty() || numbers.isEmpty() || date.isEmpty() || annuity.isEmpty()){

            if (title.isEmpty()){
                view.OnTitleEmpty();
            }else if (numbers.isEmpty()){
                view.OnFinalNumbersInvalid();
            }else if (date.isEmpty()){
                view.OnDateInvalid();
            }else {
                view.OnAnnuityInvalid();
            }

        }else if ( (numbers.length() < 4) || (date.length() < 2) || (annuity.length() < 2) ){

            if (numbers.length() < 4){
                view.OnFinalNumbersInvalid();
            }else if (date.length() < 2){
                view.OnDateInvalid();
            }else {
                view.OnAnnuityInvalid();
            }

        }else if ( date.equals("00") || (Integer.parseInt(date) > 31) ){

            view.OnDateInvalidValue();

        }else if ( annuity.equals("00") || (Integer.parseInt(annuity) > 12) ){

            view.OnAnnuityInvalidValue();

        }else {
            view.ShowProgress();
            model.DoAddCard(title, numbers, date, annuity, cardColor, cardFlag);
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
            //TODO : OnAddCardFailure
        }
    }
}