package com.example.calorie_calendar.ui.calendar


//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.CalendarView
//import android.widget.CalendarView.OnDateChangeListener
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import com.example.calorie_calendar.R
//import com.example.calorie_calendar.databinding.FragmentCalendarBinding
//import java.io.BufferedReader
//import java.io.InputStream
//import java.io.InputStreamReader
//import java.text.SimpleDateFormat
//import java.util.*
//
//class CalendarFragment : Fragment() {
//    private var _binding: FragmentCalendarBinding? = null
//    private val binding get() = _binding!!
//
//    lateinit var calendarView: CalendarView
//    lateinit var dateTV: TextView
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        dateTV = root.findViewById(R.id.text_calendar)
//        calendarView = root.findViewById(R.id.calendarView)
//
//        calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
//            val selectedDate = Calendar.getInstance()
//            selectedDate.set(year, month, dayOfMonth)
//            val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(selectedDate.time)
//            dateTV.text = formattedDate
//
//            // Call function to update nutrition text based on selected date
//            updateNutritionText(formattedDate)
//        })
//
//        return root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    private fun updateNutritionText(selectedDate: String) {
//        val inputStream: InputStream = resources.openRawResource(R.raw.nutrition_data) // assuming the CSV file is in res/raw folder
//        val reader = BufferedReader(InputStreamReader(inputStream))
//        var line: String?
//
//        // Read the CSV file line by line
//        while (reader.readLine().also { line = it } != null) {
//            val data = line!!.split(",").map { it.trim() }
//            val date = data[0]
//            if (date == selectedDate) {
//                val calories = data[1]
//                val protein = data[2]
//                val fat = data[3]
//                val carbs = data[4]
//                val sugar = data[5]
//                val sodium = data[6]
//                val fiber = data[7]
//
//                // Update the TextViews with nutrition data
//                binding.caloriesTextView.text = calories
//                binding.proteinTextView.text = protein
//                binding.fatTextView.text = fat
//                binding.carbsTextView.text = carbs
//                binding.sugarTextView.text = sugar
//                binding.sodiumTextView.text = sodium
//                binding.fiberTextView.text = fiber
//
//                // Assuming TextView IDs are defined in your layout file
//                break // exit the loop after finding the matching date
//            }
//        }
//        reader.close()
//    }
//}

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
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

data class Nutrition(val calories: Int, val protein: Int, val fat: Int, val carbs: Int, val sugar: Int, val sodium: Int, val fiber: Int)

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val nutritionMap = mutableMapOf<String, Nutrition>()

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

        loadNutritionData()

        calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            val date = "$dayOfMonth-${month + 1}-$year"
            val nutrition = nutritionMap[date]
            dateTV.text = nutrition?.let {
                "Calories: ${it.calories}, Protein: ${it.protein}, Fat: ${it.fat}, Carbs: ${it.carbs}, Sugar: ${it.sugar}, Sodium: ${it.sodium}, Fiber: ${it.fiber}"
            } ?: "No data available for $date"
        })

        return root
    }

    private fun loadNutritionData() {
        try {
            val inputStream: InputStream = resources.openRawResource(R.raw.nutrition_data) // assuming the CSV file is in res/raw folder
            val reader = BufferedReader(InputStreamReader(inputStream))
            reader.readLine() // skip header
            var line = reader.readLine()
            while (line != null) {
                val tokens = line.split(",")
                if (tokens.size > 7) {
                    val date = tokens[0]
                    val nutrition = Nutrition(tokens[1].toInt(), tokens[2].toInt(), tokens[3].toInt(), tokens[4].toInt(), tokens[5].toInt(), tokens[6].toInt(), tokens[7].toInt())
                    nutritionMap[date] = nutrition
                }
                line = reader.readLine()
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


//class CalendarFragment : Fragment() {
//    private var _binding: FragmentCalendarBinding? = null
//
//    private val binding get() = _binding!!
//
//    lateinit var calendarView: CalendarView
//    lateinit var dateTV: TextView
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        dateTV = root.findViewById(R.id.text_calendar)
//        calendarView = root.findViewById(R.id.calendarView)
//
//        calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
//            val Date = (dayOfMonth.toString() + "-" +
//                    (month + 1) + "-" + year)
//
//            dateTV.setText(Date)
//        })
//
//        return root
//
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}