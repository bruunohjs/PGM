package com.dev.marcellocamara.pgm.Presenter;

import com.dev.marcellocamara.pgm.Contract.IHome;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Helper.NumberHelper;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;
import com.dev.marcellocamara.pgm.Model.ExpenseModel;

import java.util.List;

/***
    marcellocamara@id.uff.br
            2019
***/

public class HomePresenter implements IHome.Presenter, ITaskListener {

    private IHome.View view;
    private IHome.Model model;
    private List<ExpenseModel> list;

    public HomePresenter(IHome.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnRequestExpenses(String monthYear) {
        view.ShowProgress();
        this.list = model.DoRecoverExpenses(monthYear);
    }

    @Override
    public void OnTotalCalculate(List<ExpenseModel> list, String sum) {

        String result = sum;
        double total = 0.0;

        if ( !(list.isEmpty()) ){
            for (ExpenseModel expenseModel : list){
                total += ( (expenseModel.getPrice()) / (Double.parseDouble(expenseModel.getInstallments())) );
            }

            result = NumberHelper.GetDecimal(total);
        }

        view.OnRequestTotalCalculateResult(result);
    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

    @Override
    public void OnSuccess() {
        if (view != null){
            view.HideProgress();
            view.OnRequestExpensesResult(list);
        }
    }

    @Override
    public void OnStop() {
        model.RemoveEventListener();
    }

    @Override
    public void OnError(String message) {
        if (view != null){
            view.HideProgress();
        }
    }
}
