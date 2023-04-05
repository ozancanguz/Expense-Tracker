package com.example.expense_tracker.data.entity

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {

    var gson = Gson()

    @androidx.room.TypeConverter
    fun ProductListToString(expense:List<Expense>): String {
        return gson.toJson(expense)
    }

    @androidx.room.TypeConverter
    fun stringToProductList(data: String): List<Expense> {
        val listType = object : TypeToken<List<Expense>>() {}.type
        return gson.fromJson(data, listType)
    }
}