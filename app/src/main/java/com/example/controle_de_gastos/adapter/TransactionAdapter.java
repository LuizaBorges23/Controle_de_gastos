package com.example.controle_de_gastos.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controle_de_gastos.R;
import com.example.controle_de_gastos.model.Transaction;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private List<Transaction> mTransactions = new ArrayList<>();
    private final LayoutInflater mInflater;
    private final Context mContext;
    private final Locale mLocale = new Locale("pt", "BR");
    private final NumberFormat mCurrencyFormat = NumberFormat.getCurrencyInstance(mLocale);
    private final SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy", mLocale);


    public TransactionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction current = mTransactions.get(position);

        holder.textCategoryName.setText(current.getCategoria());
        holder.textTransactionDate.setText(mDateFormat.format(new java.util.Date(current.getData())));

        String formattedAmount = mCurrencyFormat.format(current.getValor());
        int color;

        if ("Receita".equals(current.getTipo())) {
            holder.textTransactionAmount.setText(formattedAmount);
            color = Color.parseColor("#4CAF50"); // Verde
        } else {
            holder.textTransactionAmount.setText("- " + formattedAmount);
            color = Color.parseColor("#F44336"); // Vermelho
        }
        holder.textTransactionAmount.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    public void setTransactions(List<Transaction> transactions) {
        this.mTransactions = transactions;
        notifyDataSetChanged(); // Em um app real, use DiffUtil para melhor performance
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final TextView textCategoryName;
        private final TextView textTransactionDate;
        private final TextView textTransactionAmount;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            textCategoryName = itemView.findViewById(R.id.text_category_name);
            textTransactionDate = itemView.findViewById(R.id.text_transaction_date);
            textTransactionAmount = itemView.findViewById(R.id.text_transaction_amount);


        }
    }


}
