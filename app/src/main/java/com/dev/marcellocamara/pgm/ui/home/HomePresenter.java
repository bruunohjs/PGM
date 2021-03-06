package com.dev.marcellocamara.pgm.ui.home;

import com.dev.marcellocamara.pgm.ui.ITaskListener;
import com.dev.marcellocamara.pgm.utils.NumberFormat;
import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.model.DatabaseModel;
import com.dev.marcellocamara.pgm.model.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

/***
    marcellocamara@id.uff.br
            2019
***/

public class HomePresenter implements IHome.Presenter, ITaskListener {

    private IHome.View view;
    private IHome.Model model;
    private List<ExpenseModel> expensesList;
    private ArrayList<CardModel> cardsList;

    public HomePresenter(IHome.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnRequestExpenses(String monthYear) {
        view.ShowProgress();
        this.cardsList = model.DoRecoverCards();
        this.expensesList = model.DoRecoverExpenses(monthYear);
    }

    @Override
    public void OnTotalCalculate(List<ExpenseModel> list) {
        double result = NumberFormat.getTotalExpenses(list);
        view.OnRequestTotalCalculateResult(NumberFormat.getDecimal(result));
    }

    @Override
    public void OnCheckUserCards(ArrayList<CardModel> cards) {
        if (!cardsList.isEmpty()){
            view.AllowAddNewExpense();
        }else {
            view.DenyAddNewExpense();
        }
    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

    @Override
    public void OnSuccess() {
        if (view != null){
            view.HideProgress();
            view.OnRequestExpensesResult(expensesList, cardsList);
        }
    }

    @Override
    public void OnStop() {
        model.RemoveExpensesEventListener();
    }

    @Override
    public void OnError(String message) {
        if (view != null){
            view.HideProgress();
        }
    }

}
