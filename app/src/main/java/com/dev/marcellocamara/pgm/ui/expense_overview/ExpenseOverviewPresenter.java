package com.dev.marcellocamara.pgm.ui.expense_overview;

import com.dev.marcellocamara.pgm.ui.ITaskListener;
import com.dev.marcellocamara.pgm.utils.NumberFormat;
import com.dev.marcellocamara.pgm.model.DatabaseModel;
import com.dev.marcellocamara.pgm.model.ExpenseModel;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ExpenseOverviewPresenter implements IExpenseOverview.Presenter, ITaskListener {

    private IExpenseOverview.View view;
    private IExpenseOverview.Model model;

    public ExpenseOverviewPresenter(IExpenseOverview.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnVerifyInstallments(String installments, double price) {
        if (Double.parseDouble(installments) > 1){
            String value = NumberFormat.getDecimal((price) / Integer.parseInt(installments) );
            view.OnSetInstallments(value);
        }
    }

    @Override
    public void OnDeleteExpense(ExpenseModel expenseModel) {
        view.ShowProgress();
        model.DoDeleteExpense(expenseModel);
    }

    @Override
    public void OnSuccess() {
        if (view != null){
            view.HideProgress();
            view.OnDeleteExpenseSuccess();
        }
    }

    @Override
    public void OnError(String message) {
        if (view != null){
            view.HideProgress();
            view.OnDeleteExpenseFailure(message);
        }
    }

    @Override
    public void OnDestroy() { view = null; }
}
