package com.dev.marcellocamara.pgm.Presenter;

import com.dev.marcellocamara.pgm.Contract.INewExpense;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Helper.NumberHelper;
import com.dev.marcellocamara.pgm.Model.CardModel;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;

import java.util.ArrayList;

/***
    marcellocamara@id.uff.br
            2019
***/

public class NewExpensePresenter implements INewExpense.Presenter, ITaskListener {

    private INewExpense.View view;
    private INewExpense.Model model;

    public NewExpensePresenter(INewExpense.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnAddExpense(String date, String title, String description, String price, int installments, String creditCard) {

        if (title.isEmpty() || description.isEmpty() || price.isEmpty()){

            if (title.isEmpty()){
                view.OnEmptyTitle();
            }else if (description.isEmpty()){
                view.OnEmptyDescription();
            }else {
                view.OnEmptyPrice();
            }

        }else if (Double.parseDouble(price) == 0){

            view.OnEmptyPrice();

        }else if (Double.parseDouble(price) > 99999){
            view.OnMaxPrice();
        }else{
            view.ShowProgress();
            model.DoAddExpense(date, title, description, Double.parseDouble(price), installments, creditCard);
        }

    }

    @Override
    public void OnRequestCardSequence(ArrayList<CardModel> cards) {
        CharSequence charSequence[] = new String[cards.size()];
        for (int i = 0 ; i < cards.size() ; i++){
            charSequence[i] = cards.get(i).getCardTitle() + " - " + cards.get(i).getFinalDigits();
        }
        view.OnRequestCharSequenceResult(charSequence);
    }


    @Override
    public void OnCalculateDate(int day, int month, int year) {
        view.OnCalculatedDate(NumberHelper.GetMonth(day), NumberHelper.GetMonth(month+1), String.valueOf(year));
    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

    @Override
    public void OnSuccess() {
        if (view != null){
            view.HideProgress();
            view.OnAddExpenseSuccessful();
        }
    }

    @Override
    public void OnError(String message) {
        if (view != null){
            view.HideProgress();
            view.OnAddExpenseFailure(message);
        }
    }
}