package com.example.calorie_calendar.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.calorie_calendar.R
import com.example.calorie_calendar.databinding.FragmentCalendarBinding
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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


        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())

        loadNutritionData(currentDate)
        val nutrition = nutritionMap[currentDate]
        dateTV.text = nutrition?.let {
            "Calories: ${it.calories}\nProtein: ${it.protein} g\nFat: ${it.fat} g\nCarbs: ${it.carbs} g\nSugar: ${it.sugar} g\nSodium: ${it.sodium} mg\nFiber: ${it.fiber} g"
        } ?: "No data available for $currentDate"

        calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            val date = String.format("%02d-%02d-%d", dayOfMonth, month + 1, year)
            loadNutritionData(date)
            val nutrition = nutritionMap[date]
            dateTV.text = nutrition?.let {
                "Calories: ${it.calories}\nProtein: ${it.protein} g\nFat: ${it.fat} g\nCarbs: ${it.carbs} g\nSugar: ${it.sugar} g\nSodium: ${it.sodium} mg\nFiber: ${it.fiber} g"
            } ?: "No data available for $date"
        })

        return root
    }

    private fun loadNutritionData(date: String) {
        // Open the file for the selected date
        val fileName = "nutrition_data_$date.csv"
        val file = File(requireContext().filesDir, fileName)

        if (file.exists()) {
            val fileInputStream = requireContext().openFileInput(fileName)
            val reader = BufferedReader(InputStreamReader(fileInputStream))

            var totalCalories = 0
            var totalProtein = 0
            var totalFat = 0
            var totalCarbs = 0
            var totalSugar = 0
            var totalSodium = 0
            var totalFiber = 0

            var line = reader.readLine()
            while (line != null) {
                val tokens = line.split(",")
                if (tokens.size > 6) {
                    totalCalories += tokens[0].toInt()
                    totalProtein += tokens[1].toInt()
                    totalFat += tokens[2].toInt()
                    totalCarbs += tokens[3].toInt()
                    totalSugar += tokens[4].toInt()
                    totalSodium += tokens[5].toInt()
                    totalFiber += tokens[6].toInt()
                }
                line = reader.readLine()
            }
            reader.close()

            val nutrition = Nutrition(totalCalories, totalProtein, totalFat, totalCarbs, totalSugar, totalSodium, totalFiber)
            nutritionMap[date] = nutrition
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}