package com.dev.marcellocamara.pgm.Adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.Helper.NumberHelper;
import com.dev.marcellocamara.pgm.Model.ExpenseModel;
import com.dev.marcellocamara.pgm.R;

import java.util.List;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.MyViewHolder> {

    private List<ExpenseModel> list;

    public ExpensesAdapter(List<ExpenseModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_expenses, parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ExpenseModel expenseModel = list.get(position);

        double value = ( (expenseModel.getPrice()) / (Double.parseDouble(expenseModel.getInstallments())) );

        holder.title.setText(expenseModel.getTitle());
        holder.description.setText(expenseModel.getDescription());
        holder.price.setText(NumberHelper.GetDecimal(value));
        if ( (Integer.parseInt(expenseModel.getInstallments())) > 1){
            holder.layoutInstallment.setVisibility(View.VISIBLE);
            holder.installment.setText(expenseModel.getCurrentInstallment());
        }
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title, description, price, installment;
        private ConstraintLayout layoutInstallment;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textViewTitle);
            description = itemView.findViewById(R.id.textViewDescription);
            price = itemView.findViewById(R.id.textViewPrice);
            installment = itemView.findViewById(R.id.textViewInstallment);
            layoutInstallment = itemView.findViewById(R.id.layoutInstallment);
        }
    }

}
