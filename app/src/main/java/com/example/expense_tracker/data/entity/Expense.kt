package com.example.expense_tracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "expense_table" )
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var date:String,
    var price:Int

)
