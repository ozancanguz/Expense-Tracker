package com.example.expense_tracker.ui.fragments

import android.app.Activity
import android.graphics.Color
import android.graphics.PorterDuff
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expense_tracker.R
import com.example.expense_tracker.adapters.ExpenseListAdapter
import com.example.expense_tracker.adapters.SwipeToDelete
import com.example.expense_tracker.data.entity.Expense
import com.example.expense_tracker.databinding.FragmentListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    private val binding get() = _binding!!

    private val listviewmodel:ListViewModel by viewModels()

    private val expenseListadapter=ExpenseListAdapter()

    private var totalExpense: Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val darkModeSwitch = binding.darkModeSwitch
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        // current day dot color change function
       currentDayChangeDot()

        // expand - collapse card
        expandCardView()


      // seek bar functionality
        seekBar()

        // setting up recyclerview
        setupRv()

        // observe live data
        observeExpenseLiveData()


        binding.totalExpenseText.setOnClickListener {
            listviewmodel.deleteAllExpenses()
        }





        return view


    }



    private fun observeExpenseLiveData() {
        listviewmodel.expenseList.observe(viewLifecycleOwner, Observer {
            expenseListadapter.setData(it)

            totalExpense = it.sumOf { it.price }
            binding.totalExpenseText.text = "Total Expense:"+ "$$totalExpense"
        })
    }

    private fun setupRv() {
        binding.listRv.layoutManager=LinearLayoutManager(requireContext())
        binding.listRv.adapter=expenseListadapter

        // swipe to delete
        swipeToDelete(binding.listRv)

    }




    private fun seekBar() {
        //-------------------------------
        val seekbar = binding.seekbar
        val seekbarTextView = binding.seekbarTextView
        val addIcon=binding.addIcon

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Update the text view with the seekbar progress
                seekbarTextView.text = "$"+"$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }
        })

        addIcon.setOnClickListener {
            // Get the current value of the seekbar
            val currentProgress = seekbar.progress
             // insert expense list to db
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d")
            val formattedDate = currentDateTime.format(formatter)
            println(formattedDate)
            val expense=Expense(0,formattedDate,currentProgress.toInt())
            listviewmodel.insertExpense(expense)
            Log.d("viewmodel",""+expense)

            totalExpense += expense.price
            binding.totalExpenseText.text = "$$totalExpense"
            Log.d("viewmodel", "$expense, totalExpense: $$totalExpense")

        }

    }

    private fun expandCardView(){
        val cardView = binding.cardview
        val seekBar = binding.seekbar
        val seekBarTextView = binding.seekbarTextView
        val addIcon = binding.addIcon

        // Set initial visibility of the seek bar and add icon to gone
        seekBar.visibility = View.GONE
        seekBarTextView.visibility = View.GONE
        addIcon.visibility = View.GONE

        // Set OnClickListener to the card view
        cardView.setOnClickListener {
            if (seekBar.visibility == View.GONE) {
                // Expand the card view
                seekBar.translationY = seekBar.height.toFloat()
                seekBar.visibility = View.VISIBLE
                seekBar.animate().translationY(0f).setDuration(200).start()

                seekBarTextView.translationY = seekBarTextView.height.toFloat()
                seekBarTextView.visibility = View.VISIBLE
                seekBarTextView.animate().translationY(0f).setDuration(200).start()

                addIcon.translationY = addIcon.height.toFloat()
                addIcon.visibility = View.VISIBLE
                addIcon.animate().translationY(0f).setDuration(200).start()
            } else {
                // Collapse the card view
                seekBar.animate().translationY(seekBar.height.toFloat()).setDuration(200).withEndAction {
                    seekBar.visibility = View.GONE
                }.start()

                seekBarTextView.animate().translationY(seekBarTextView.height.toFloat()).setDuration(200).withEndAction {
                    seekBarTextView.visibility = View.GONE
                }.start()

                addIcon.animate().translationY(addIcon.height.toFloat()).setDuration(200).withEndAction {
                    addIcon.visibility = View.GONE
                }.start()
            }
        }
    }


    private fun currentDayChangeDot() {
        // Get the current day of the week (1 = Sunday, 2 = Monday, etc.)
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

// Set the background of the View associated with the current day to a red circle drawable
        when (dayOfWeek) {
            Calendar.SUNDAY -> binding.sundayDot.setBackgroundResource(R.drawable.red_circle)
            Calendar.MONDAY -> binding.mondayDot.setBackgroundResource(R.drawable.red_circle)
            Calendar.TUESDAY -> binding.tuesdayDot.setBackgroundResource(R.drawable.red_circle)
            Calendar.WEDNESDAY -> binding.wednesdayDot.setBackgroundResource(R.drawable.red_circle)
            Calendar.THURSDAY -> binding.thursdayDot.setBackgroundResource(R.drawable.red_circle)
            Calendar.FRIDAY -> binding.fridayDot.setBackgroundResource(R.drawable.red_circle)
            Calendar.SATURDAY -> binding.saturdayDot.setBackgroundResource(R.drawable.red_circle)
        } }


    //swipe to delete
    private fun swipeToDelete(recyclerView: RecyclerView) {

        val swipeToDeleteCallback=object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemtoDelete=expenseListadapter.expenselist[viewHolder.adapterPosition]
                listviewmodel.deleteExpense(itemtoDelete)
                Snackbar.make(requireView(), "Removed successfully", Snackbar.LENGTH_LONG).show();



            }
        }
        val itemTouchHelper= ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


}