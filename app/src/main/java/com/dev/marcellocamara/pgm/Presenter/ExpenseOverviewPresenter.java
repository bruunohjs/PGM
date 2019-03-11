package com.dev.marcellocamara.pgm.Presenter;

import com.dev.marcellocamara.pgm.Contract.IExpenseOverview;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Helper.NumberHelper;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;
import com.dev.marcellocamara.pgm.Model.ExpenseModel;

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
            String value = NumberHelper.GetDecimal((price) / Integer.parseInt(installments) );
            view.OnSetInstallments(value);
        }
    }

    @Override
    public void OnDeleteExpense(ExpenseModel expenseModel) {
        view.ShowProgress();
        model.DoDeleteExpense(
                expenseModel.getPaymentDate(),
                Integer.parseInt(expenseModel.getInstallments()),
                expenseModel.getUniqueKey()
        );
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
