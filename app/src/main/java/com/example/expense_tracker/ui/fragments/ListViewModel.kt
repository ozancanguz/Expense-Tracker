package com.example.expense_tracker.ui.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.expense_tracker.data.entity.Expense
import com.example.expense_tracker.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel@Inject constructor(private var repository: Repository,application: Application):AndroidViewModel(application) {


    // init get all expense list livedata
    val expenseList:LiveData<List<Expense>> = repository.local.getAllExpense()


    // insert expense
   fun insertExpense(expense: Expense){
        viewModelScope.launch {
            repository.local.insertExpense(expense)
        }
    }

    // delete expense
     fun deleteExpense(expense: Expense){
        viewModelScope.launch {
            repository.local.deleteExpense(expense)
        }
    }

    // delete all expenses
     fun deleteAllExpenses(){
        viewModelScope.launch {
            repository.local.deleteAllExpenses()
        }
    }


}