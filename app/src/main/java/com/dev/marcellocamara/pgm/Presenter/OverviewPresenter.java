package com.dev.marcellocamara.pgm.Presenter;

import com.dev.marcellocamara.pgm.Contract.IOverview;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Helper.NumberHelper;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;
import com.dev.marcellocamara.pgm.Model.ExpenseModel;

/***
    marcellocamara@id.uff.br
            2019
***/

public class OverviewPresenter implements IOverview.Presenter, ITaskListener {

    private IOverview.View view;
    private IOverview.Model model;

    public OverviewPresenter(IOverview.View view) {
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
        //TODO : model.DoDeleteExpense
    }

    @Override
    public void OnSuccess() {
        //TODO : OnDeleteSuccess
    }

    @Override
    public void OnError(String message) {
        //TODO : OnDeleteFailure
    }

    @Override
    public void OnDestroy() { view = null; }
}
