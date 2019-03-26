package com.dev.marcellocamara.pgm.ui.card_overview;

import com.dev.marcellocamara.pgm.ui.ITaskListener;
import com.dev.marcellocamara.pgm.utils.NumberFormat;
import com.dev.marcellocamara.pgm.utils.SpecificExpenseCard;
import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.model.DatabaseModel;
import com.dev.marcellocamara.pgm.model.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

/***
    marcellocamara@id.uff.br
            2019
***/

public class CardOverviewPresenter implements ICardOverview.Presenter, ITaskListener {

    private ICardOverview.View view;
    private ICardOverview.Model model;
    private ArrayList<CardModel> cardList;
    private CardModel specificCard;
    private List<ExpenseModel> expenseList;
    private String uniqueId;

    public CardOverviewPresenter(ICardOverview.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnRequestUserData(){
        view.OnRequestUserDataSuccessful(model.GetUserDisplayName());
    }

    @Override
    public void OnRequestCardExpenses(String uniqueId, String monthYear) {
        this.uniqueId = uniqueId;
        this.cardList = model.DoRecoverCards();
        this.expenseList = model.DoRecoverExpenses(monthYear);
    }

    @Override
    public ArrayList<String> GetCardsNumbers() {
        ArrayList<String> cardsNumbers = new ArrayList<>();
        for (CardModel card : cardList){
            if (!(card.getFinalDigits().equals(specificCard.getFinalDigits()))){
                cardsNumbers.add(card.getFinalDigits());
            }
        }
        return cardsNumbers;
    }

    @Override
    public void OnTotalCalculate(List<ExpenseModel> list) {
        String result = "0.00";
        double total = 0.0;

        if ( !(list.isEmpty()) ){
            for (ExpenseModel expenseModel : list){
                total += ( (expenseModel.getPrice()) / (Double.parseDouble(expenseModel.getInstallments())) );
            }

            result = NumberFormat.getDecimal(total);
        }

        view.OnRequestTotalCalculateResult(result);
    }

    @Override
    public void OnCheckExpenses(String price) {
        double value = Double.parseDouble(price.replace(",","."));
        if (value!=0){
            view.OnAllowViewExpenses();
        }else {
            view.OnDenyViewExpenses();
        }
    }


    private void GetSpecificCardExpenses() {
        OnTotalCalculate(SpecificExpenseCard.getExpensesList(expenseList, uniqueId));
    }

    private void checkCardUpdate() {
        ArrayList<CardModel> arrayList = new ArrayList<>(1);
        arrayList.add(SpecificExpenseCard.getCard(uniqueId, cardList));
        this.specificCard = arrayList.get(0);
        view.OnRequestCardSuccessful(arrayList);
    }

    @Override
    public void OnSuccess() {
        if (view != null){
            checkCardUpdate();
            GetSpecificCardExpenses();
        }
    }

    @Override
    public void OnStop() {
        model.RemoveCardsEventListener();
        model.RemoveExpensesEventListener();
    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

    @Override
    public void OnError(String message) {
        if (view != null){
            view.OnRequestCardExpensesFailure(message);
        }
    }
}