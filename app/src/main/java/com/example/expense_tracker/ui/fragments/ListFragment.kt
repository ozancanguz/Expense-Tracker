package com.example.expense_tracker.ui.fragments

import android.graphics.Color
import android.graphics.PorterDuff
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.expense_tracker.R
import com.example.expense_tracker.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        // current day dot color change function
       currentDayChangeDot()



        return view


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
        }

    }


}