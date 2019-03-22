package com.dev.marcellocamara.pgm.Presenter;

import com.dev.marcellocamara.pgm.Contract.ICardExpenses;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Helper.SpecificExpenseCard;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;
import com.dev.marcellocamara.pgm.Model.ExpenseModel;

import java.util.List;

/***
    marcellocamara@id.uff.br
            2019
***/

public class CardExpensesPresenter implements ICardExpenses.Presenter, ITaskListener {

    private ICardExpenses.View view;
    private ICardExpenses.Model model;
    private List<ExpenseModel> list;
    private String cardUniqueId;

    public CardExpensesPresenter(ICardExpenses.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnRequestExpenses(String monthYear, String cardUniqueId) {
        this.cardUniqueId = cardUniqueId;
        this.list = model.DoRecoverExpenses(monthYear);
    }

    @Override
    public void OnStop() {
        model.RemoveExpensesEventListener();
    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

    @Override
    public void OnSuccess() {
        if (view != null){
            if (list != null){
                view.OnRequestExpensesResult(
                        SpecificExpenseCard.getExpensesList(this.list, this.cardUniqueId)
                );
            }
        }
    }

    @Override
    public void OnError(String message) {
        if (view != null){
            view.OnRequestExpensesFailure(message);
        }
    }
}