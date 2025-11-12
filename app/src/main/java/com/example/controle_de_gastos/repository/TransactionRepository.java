package com.example.controle_de_gastos.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.controle_de_gastos.db.AppDataBase;
import com.example.controle_de_gastos.db.TransactionDao;
import com.example.controle_de_gastos.model.Transaction;

import java.util.List;
public class TransactionRepository {
    private TransactionDao mTransactionDao;
    private LiveData<List<Transaction>> mAllTransactions;
    private LiveData<Double> mTotalBalance;

    public TransactionRepository(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        mTransactionDao = db.transactionDao();
        mAllTransactions = mTransactionDao.getAllTransactions();
        mTotalBalance = mTransactionDao.getTotalBalance();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return mAllTransactions;
    }

    public LiveData<Double> getTotalBalance() {
        return mTotalBalance;
    }

    public void insert(Transaction transaction) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            mTransactionDao.insert(transaction);
        });
    }
}
