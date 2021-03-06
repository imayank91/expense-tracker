package com.mayank.expensetracker.internal.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mayank.expensetracker.internal.model.Transaction

@Database(
    entities = [Transaction::class],
    version = 1,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun getTransactionDao(): TransactionDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "transaction.db"
        ).build()
    }
}
