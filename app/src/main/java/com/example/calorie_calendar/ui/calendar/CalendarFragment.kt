package com.example.calorie_calendar.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.calorie_calendar.R
import com.example.calorie_calendar.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null

    private val binding get() = _binding!!

    lateinit var calendarView: CalendarView
    lateinit var dateTV: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dateTV = root.findViewById(R.id.text_calendar)
        calendarView = root.findViewById(R.id.calendarView)

        calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            val Date = (dayOfMonth.toString() + "-" +
                    (month + 1) + "-" + year)

            dateTV.setText(Date)
        })

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}