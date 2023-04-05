package com.example.expense_tracker.data.local

import androidx.lifecycle.LiveData
import com.example.expense_tracker.data.entity.Expense
import com.example.expense_tracker.data.entity.ExpenseDao
import javax.inject.Inject

class LocalDataSource@Inject constructor(private val expenseDao: ExpenseDao) {

    fun getAllExpense():LiveData<List<Expense>>{
        return expenseDao.getAllExpenseList()
    }

    suspend fun insertExpense(expense: Expense){
        return expenseDao.insertExpense(expense)
    }

    suspend fun deleteExpense(expense: Expense){
        return expenseDao.deleteExpense(expense)
    }

    suspend fun deleteAllExpenses(){
        return expenseDao.deleteAllExpenses()
    }
}