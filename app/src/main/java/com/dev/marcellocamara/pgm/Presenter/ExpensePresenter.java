package com.dev.marcellocamara.pgm.Presenter;

import android.content.Context;

import com.dev.marcellocamara.pgm.Contract.IExpense;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;
import com.dev.marcellocamara.pgm.R;

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
    public void OnAddExpense(String title, String description, String price, int parcel) {

        if (title.isEmpty() || description.isEmpty() || price.isEmpty()){

            if (title.isEmpty()){
                view.OnInvalidField(context.getString(R.string.presenter_expense_invalid_title));
            }else if (description.isEmpty()){
                view.OnInvalidField(context.getString(R.string.presenter_expense_invalid_description));
            }else {
                view.OnInvalidField(context.getString(R.string.presenter_expense_invalid_price));
            }

        }else{
            model.DoAddExpense();
        }//TODO : others if's and model

    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

    @Override
    public void OnSuccess() {
        if (view != null){
            view.OnAddExpenseSuccessful();
        }
    }

    @Override
    public void OnError(String message) {
        if (view != null){
            view.OnAddExpenseFailure("Failure.");
        }
    }
}
