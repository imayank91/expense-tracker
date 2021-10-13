package com.mayank.expensetracker.internal.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.DateFormat

@Entity(tableName = "all_transactions")
data class Transaction(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "amount")
    var amount: Double,
    @ColumnInfo(name = "transactionType")
    var transactionType: String,
    @ColumnInfo(name = "transactionDate")
    var transactionDate: String,
    @ColumnInfo(name = "createdAt")
    var timestamp: Long ,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transactionId")
    var transactionId: Int = 0,
) {
}

//    : Serializable {
//    val createdAtDateFormat: String
//        get() = DateFormat.getDateTimeInstance()
//            .format(createdAt) // Date Format: Jan 11, 2021, 11:30 AM
//}
