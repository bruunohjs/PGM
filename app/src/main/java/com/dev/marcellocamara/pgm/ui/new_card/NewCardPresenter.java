package com.dev.marcellocamara.pgm.ui.new_card;

import com.dev.marcellocamara.pgm.ui.ITaskListener;
import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.model.DatabaseModel;

import java.util.ArrayList;

import static com.dev.marcellocamara.pgm.utils.InternetConnection.hasInternet;

/***
    marcellocamara@id.uff.br
            2019
***/

public class NewCardPresenter implements INewCard.Presenter, ITaskListener {

    private INewCard.View view;
    private INewCard.Model model;
    private ArrayList<String> cardsNumbers;

    public NewCardPresenter(INewCard.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnRequestUserData(ArrayList<String> cardsNumbers) {
        view.OnRequestUserDataSuccessful(model.GetUserDisplayName());
        this.cardsNumbers = cardsNumbers;
    }

    @Override
    public void OnCheckCardDataUpdate(CardModel card) {
        if (card != null){
            view.OnCheckCardDataUpdateSuccessful();
        }
    }

    private boolean OnCheckFields(String title, String numbers, String date){

        if (title.isEmpty() || numbers.isEmpty() || date.isEmpty()){

            if (title.isEmpty()){
                view.OnTitleEmpty();
            }else if (numbers.isEmpty()){
                view.OnFinalNumbersInvalid();
            }else {
                view.OnDateInvalid();
            }
            return false;

        }else if ( (numbers.length() < 4) || (date.length() < 2) ){

            if (numbers.length() < 4){
                view.OnFinalNumbersInvalid();
            }else {
                view.OnDateInvalid();
            }
            return false;

        }else if ( date.equals("00") || (Integer.parseInt(date) > 31) ){

            view.OnDateInvalidValue();
            return false;

        }else {
            return true;
        }

    }

    private boolean checkExistingCard(String numbers){
        if (cardsNumbers != null){
            for (String card : this.cardsNumbers){
                if (card.equals(numbers)){
                    return true;
                }
            }
            return false;
        }else {
            return false;
        }
    }

    @Override
    public void OnAddCard(String title, String numbers, String date, int cardColor, int cardFlag) {
        if (OnCheckFields(title, numbers, date)){
            if (checkExistingCard(numbers)){
                view.OnFinalNumbersAlreadyExists();
            }else {
                if (hasInternet()){
                    view.ShowProgress();
                    model.DoAddCard(title, numbers, date, cardColor, cardFlag);
                }else {
                    view.OnInternetFailure();
                }
            }
        }
    }

    @Override
    public void OnUpdateCard(CardModel card, String title, String numbers, String date, int cardColor, int cardFlag) {
        if (OnCheckFields(title, numbers, date)){
            if (card.getCardTitle().equals(title) &&
                    card.getFinalDigits().equals(numbers) &&
                    card.getBetterDayToBuy().equals(date) &&
                    card.getCardColor() == cardColor &&
                    card.getCardFlag() == cardFlag){
                view.OnUpdateCardFailure();
            }else {
                if (checkExistingCard(numbers)){
                    view.OnFinalNumbersAlreadyExists();
                }else {
                    if (hasInternet()){
                        view.ShowProgress();
                        CardModel newCard = new CardModel(title, numbers, date, cardColor, cardFlag);
                        newCard.setPoints(card.getPoints());
                        newCard.setUniqueId(card.getUniqueId());
                        model.DoUpdateCard(newCard);
                    }else {
                        view.OnInternetFailure();
                    }
                }
            }
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