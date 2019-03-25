package com.dev.marcellocamara.pgm.ui.card_expenses;

import com.dev.marcellocamara.pgm.ui.ITaskListener;
import com.dev.marcellocamara.pgm.utils.SpecificExpenseCard;
import com.dev.marcellocamara.pgm.model.DatabaseModel;
import com.dev.marcellocamara.pgm.model.ExpenseModel;

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