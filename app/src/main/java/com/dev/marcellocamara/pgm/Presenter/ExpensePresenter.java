package com.dev.marcellocamara.pgm.Presenter;

import android.content.Context;

import com.dev.marcellocamara.pgm.Contract.IExpense;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;
import com.dev.marcellocamara.pgm.R;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ExpensePresenter implements IExpense.Presenter, ITaskListener {

    private IExpense.View view;
    private IExpense.Model model;
    private Context context;

    public ExpensePresenter(IExpense.View view, Context context) {
        this.view = view;
        this.model = new DatabaseModel(this);
        this.context = context;
    }

    @Override
    public void OnAddExpense(String date, String title, String description, String price, int installments) {

        if (title.isEmpty() || description.isEmpty() || price.isEmpty()){

            if (title.isEmpty()){
                view.OnInvalidField(context.getString(R.string.presenter_expense_invalid_title));
            }else if (description.isEmpty()){
                view.OnInvalidField(context.getString(R.string.presenter_expense_invalid_description));
            }else {
                view.OnInvalidField(context.getString(R.string.presenter_expense_invalid_price));
            }

        }else if (Double.parseDouble(price) == 0){

            view.OnInvalidField(context.getString(R.string.presenter_expense_invalid_price));

        }else{
            view.ShowProgress();
            model.DoAddExpense(date, title, description, Double.parseDouble(price), installments);
        }

    }

    @Override
    public void OnCalculateDate(int day, int month, int year) {

        String calculateDay = String.valueOf(day);
        String calculateMonth = String.valueOf(month+1);

        if (calculateDay.length() < 2){
            calculateDay = "0" + calculateDay;
        }
        if (calculateMonth.length() < 2){
            calculateMonth = "0" + calculateMonth;
        }

        view.OnCalculatedDate(calculateDay, calculateMonth, String.valueOf(year));
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
