package com.example.calorie_calendar.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.calorie_calendar.R
import com.example.calorie_calendar.databinding.FragmentHomeBinding
import com.example.calorie_calendar.ui.calendar.Nutrition
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val nutritionMap = mutableMapOf<String, Nutrition>()
    private val binding get() = _binding!!

//    change this value to whatever your limit is
    private var calorieLimit = 2200

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val progressText = root.findViewById<TextView>(R.id.progress_text)

        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())
        root.findViewById<TextView>(R.id.dateText).text = currentDate
        root.findViewById<TextView>(R.id.calories_text).text = calorieLimit.toString()

        loadNutritionData(currentDate)
        val nutrition = nutritionMap[currentDate]
        val caloriesText = nutrition?.calories.toString() ?: "0"

        var progressBar = root.findViewById<ProgressBar>(R.id.progress_bar)
        progressBar.progressDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.circular_progress_bar)
        progressText.text = caloriesText
        progressBar.max = calorieLimit
        progressBar.progress = nutrition?.calories ?: 0

        val calorieLimitThird = calorieLimit / 3
        val calorieLimitTwoThird = calorieLimit - calorieLimitThird
        if (nutrition?.calories ?: 0 <= calorieLimitThird) {
            progressText.setTextColor(Color.RED)
        } else if (nutrition?.calories ?: 0 <= calorieLimitTwoThird) {
            progressText.setTextColor(Color.YELLOW)
        } else {
            progressText.setTextColor(Color.GREEN)
        }

        val nutrientsText = root.findViewById<TextView>(R.id.text_home)
        nutrientsText.text = nutrition?.let {
            "Protein: ${it.protein} g\nFat: ${it.fat} g\nCarbs: ${it.carbs} g\nSugar: ${it.sugar} g\nSodium: ${it.sodium} mg\nFiber: ${it.fiber} g"
        } ?: "No data available for $currentDate"
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
    override fun onResume() {
        super.onResume()
        // Reload data and update progress bar when fragment is resumed
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())
        loadNutritionData(currentDate)
        updateProgressBar()
    }

    private fun updateProgressBar() {
        val currentDate = getCurrentDate()
        val nutrition = nutritionMap[currentDate]
        val progressBar = binding.progressBar
        val progressText = binding.progressText

        progressText.text = nutrition?.calories.toString()
        progressBar.progress = nutrition?.calories ?: 0

        // Update progress bar color based on calorie limit
        val calorieLimitThird = calorieLimit / 3
        val calorieLimitTwoThird = calorieLimit - calorieLimitThird
        progressText.setTextColor(
            when {
                nutrition?.calories ?: 0 <= calorieLimitThird -> Color.RED
                nutrition?.calories ?: 0 <= calorieLimitTwoThird -> Color.YELLOW
                else -> Color.GREEN
            }
        )
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return sdf.format(Date())
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}