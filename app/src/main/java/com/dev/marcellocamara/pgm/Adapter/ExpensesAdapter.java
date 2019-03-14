package com.dev.marcellocamara.pgm.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.Contract.IAdapter;
import com.dev.marcellocamara.pgm.Helper.NumberHelper;
import com.dev.marcellocamara.pgm.Model.ExpenseModel;
import com.dev.marcellocamara.pgm.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.MyViewHolder> {

    private List<ExpenseModel> list;
    private IAdapter onRecyclerViewClick;

    public ExpensesAdapter(List<ExpenseModel> list, IAdapter onRecyclerViewClick) {
        this.list = list;
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

        ExpenseModel expenseModel = list.get(position);

        double value = ( (expenseModel.getPrice()) / (Double.parseDouble(expenseModel.getInstallments())) );

        holder.textViewTitle.setText(expenseModel.getTitle());
        holder.textViewDescription.setText(expenseModel.getDescription());
        holder.textViewPrice.setText(NumberHelper.GetDecimal(value));
        holder.textViewInstallment.setText(expenseModel.getCurrentInstallment());
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.textViewTitle) protected TextView textViewTitle;
        @BindView(R.id.textViewDescription) protected TextView textViewDescription;
        @BindView(R.id.textViewPrice) protected TextView textViewPrice;
        @BindView(R.id.textViewInstallment) protected TextView textViewInstallment;

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