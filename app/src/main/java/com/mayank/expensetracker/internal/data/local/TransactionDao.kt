package com.mayank.expensetracker.internal.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mayank.expensetracker.internal.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
internal interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM all_transactions ORDER by createdAt DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("DELETE FROM all_transactions WHERE transactionId = :transactionId")
    suspend fun deleteTransactionByID(transactionId: Int)
}
