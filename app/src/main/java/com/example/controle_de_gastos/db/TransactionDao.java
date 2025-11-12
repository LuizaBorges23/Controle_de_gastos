package com.example.controle_de_gastos.db;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.controle_de_gastos.model.Transaction;

import java.util.List;
@Dao
public interface TransactionDao {
    @Insert
    void insert(Transaction transaction);

    @Query("SELECT * FROM transactions ORDER BY data DESC")
    LiveData<List<Transaction>> getAllTransactions();

    // Query para calcular o saldo total
    @Query("SELECT SUM(CASE WHEN tipo = 'Receita' THEN valor ELSE -valor END) FROM transactions")
    LiveData<Double> getTotalBalance();
}
