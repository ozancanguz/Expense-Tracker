package com.example.expense_tracker.data

import com.example.expense_tracker.data.local.LocalDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class Repository@Inject constructor(private var localDataSource: LocalDataSource) {

    var local=localDataSource
}