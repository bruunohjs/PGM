package com.dev.marcellocamara.pgm.Presenter;

import com.dev.marcellocamara.pgm.Contract.INewExpense;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Helper.NumberHelper;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;

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
    public void OnAddExpense(String date, String title, String description, String price, int installments) {

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
            model.DoAddExpense(date, title, description, Double.parseDouble(price), installments);
        }

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