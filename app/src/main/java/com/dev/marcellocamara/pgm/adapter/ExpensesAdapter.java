package com.dev.marcellocamara.pgm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.utils.CardUtils;
import com.dev.marcellocamara.pgm.utils.NumberFormat;
import com.dev.marcellocamara.pgm.utils.SpecificExpenseCard;
import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.model.ExpenseModel;
import com.dev.marcellocamara.pgm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.MyViewHolder> {

    private List<ExpenseModel> expensesList;
    private ArrayList<CardModel> cards;
    private IAdapter onRecyclerViewClick;
    private Context context;

    public ExpensesAdapter(Context context, List<ExpenseModel> expensesList, ArrayList<CardModel> cards, IAdapter onRecyclerViewClick) {
        this.context = context;
        this.expensesList = expensesList;
        this.cards = cards;
        this.onRecyclerViewClick = onRecyclerViewClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_expenses, parent, false);
        return new MyViewHolder(viewItem, onRecyclerViewClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ExpenseModel expenseModel = expensesList.get(position);
        CardModel card = SpecificExpenseCard.getCard(expenseModel.getCreditCard(), cards);

        double value = ( (expenseModel.getPrice()) / (Double.parseDouble(expenseModel.getInstallments())) );

        holder.textViewTitle.setText(expenseModel.getTitle());
        holder.textViewDescription.setText(expenseModel.getDescription());
        holder.textViewPrice.setText(NumberFormat.getDecimal(value));
        holder.textViewFinalDigits.setText(Objects.requireNonNull(card).getFinalDigits());
        holder.imageViewCreditCard.setColorFilter(CardUtils.getColor(context, card.getCardColor()));
        holder.textViewInstallment.setText(expenseModel.getCurrentInstallment());
    }

    @Override
    public int getItemCount() { return expensesList.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.textViewTitle) protected TextView textViewTitle;
        @BindView(R.id.textViewDescription) protected TextView textViewDescription;
        @BindView(R.id.textViewPrice) protected TextView textViewPrice;
        @BindView(R.id.textViewFinalDigits) protected TextView textViewFinalDigits;
        @BindView(R.id.textViewInstallment) protected TextView textViewInstallment;

        @BindView(R.id.imageViewCreditCard) protected ImageView imageViewCreditCard;

        private IAdapter onRecyclerViewClick;

        public MyViewHolder(View itemView, IAdapter onRecyclerViewClick) {
            super(itemView);
            this.onRecyclerViewClick = onRecyclerViewClick;

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecyclerViewClick.OnItemClick(getAdapterPosition());
        }
    }

}