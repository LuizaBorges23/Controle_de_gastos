package com.example.controle_de_gastos.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.controle_de_gastos.model.Transaction;
import com.example.controle_de_gastos.repository.TransactionRepository;

import java.util.List;

public class TransactionViewmodel extends AndroidViewModel {

    private TransactionRepository mRepository;
    private final LiveData<List<Transaction>> mAllTransactions;
    private final LiveData<Double> mTotalBalance;

    public TransactionViewmodel(@NonNull Application application) {
        super(application);
        // A inicialização deve acontecer AQUI, dentro do construtor
        mRepository = new TransactionRepository(application);
        mAllTransactions = mRepository.getAllTransactions();
        mTotalBalance = mRepository.getTotalBalance();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return mAllTransactions;
    }

    public LiveData<Double> getTotalBalance() {
        return mTotalBalance;
    }

    public void insert(Transaction transaction) {
        // Agora mRepository não será nulo
        mRepository.insert(transaction);
    }
}