package com.example.expense_tracker.data.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Expense::class], version = 1,
    exportSchema = false,)

@TypeConverters(TypeConverter::class)
abstract class ExpenseDatabase: RoomDatabase(){
    abstract fun expenseDao():ExpenseDao


}