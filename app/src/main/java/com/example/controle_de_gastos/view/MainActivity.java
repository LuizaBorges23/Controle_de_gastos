package com.example.controle_de_gastos.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.controle_de_gastos.viewmodel.TransactionViewmodel;
import com.example.controle_de_gastos.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.controle_de_gastos.adapter.TransactionAdapter;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private TransactionViewmodel mTransactionViewModel;
    private TextView mTextTotalBalance;
    private final Locale mLocale = new Locale("pt", "BR");
    private final NumberFormat mCurrencyFormat = NumberFormat.getCurrencyInstance(mLocale);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview_transactions);
        final TransactionAdapter adapter = new TransactionAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configurar TextView do Saldo
        mTextTotalBalance = findViewById(R.id.text_total_balance);

        // Configurar ViewModel
        mTransactionViewModel = new ViewModelProvider(this).get(TransactionViewmodel.class);

        // Observar a lista de transações
        mTransactionViewModel.getAllTransactions().observe(this, transactions -> {
            // Atualizar a lista no adapter
            adapter.setTransactions(transactions);
        });

        // Observar o saldo total
        mTransactionViewModel.getTotalBalance().observe(this, balance -> {
            if (balance == null) {
                mTextTotalBalance.setText(mCurrencyFormat.format(0));
            } else {
                mTextTotalBalance.setText(mCurrencyFormat.format(balance));
            }
        });

        // Configurar botões
        FloatingActionButton fab = findViewById(R.id.fab_add_transaction);
        fab.setOnClickListener(view -> openAddTransactionActivity(null));

        Button buttonAddReceita = findViewById(R.id.button_add_receita);
        buttonAddReceita.setOnClickListener(view -> openAddTransactionActivity("Receita"));

        Button buttonAddDespesa = findViewById(R.id.button_add_despesa);
        buttonAddDespesa.setOnClickListener(view -> openAddTransactionActivity("Despesa"));
    }

    private void openAddTransactionActivity(String tipo) {
        Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
        if (tipo != null) {
            intent.putExtra(AddTransactionActivity.EXTRA_TIPO, tipo);
        }
        startActivity(intent);
    }
}