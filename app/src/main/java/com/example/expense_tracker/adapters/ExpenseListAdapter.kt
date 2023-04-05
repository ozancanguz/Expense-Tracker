package com.example.expense_tracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expense_tracker.R
import com.example.expense_tracker.data.entity.Expense
import com.example.expense_tracker.databinding.ExpenseRowLayoutBinding

class ExpenseListAdapter: RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder>() {

    inner class ExpenseViewHolder(private val binding: ExpenseRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(expense: Expense) {
            binding.apply {
                dateTv.text = expense.date
                itemPrice.text = expense.price.toInt().toString()
            }
        }
    }

    var expenselist = emptyList<Expense>()

    fun setData(newData: List<Expense>) {
        this.expenselist = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ExpenseRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return expenselist.size
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(expenselist[position])
    }
}
