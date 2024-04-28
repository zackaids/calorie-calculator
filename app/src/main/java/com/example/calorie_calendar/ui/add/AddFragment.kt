package com.example.calorie_calendar.ui.add

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.calorie_calendar.R
import com.example.calorie_calendar.databinding.FragmentAddBinding
import com.opencsv.CSVWriter
import java.io.File
import java.io.FileWriter
import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val spinner: Spinner = root.findViewById(R.id.spinner_meals)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.Meals,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this

        val submitButton = root.findViewById<Button>(R.id.button)
        submitButton.setOnClickListener {
            val addCalories = binding.editTextTextCalories.text.toString()
            val addProtein = binding.editTextTextProtein.text.toString()
            val addFat = binding.editTextTextFat.text.toString()
            val addCarbs = binding.editTextTextCarbohydrates.text.toString()
            val addSugar = binding.editTextTextSugar.text.toString()
            val addSodium = binding.editTextTextSodium.text.toString()
            val addFiber = binding.editTextTextFiber.text.toString()

            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val currentDate = sdf.format(Date())

            val data = listOf("date", addCalories, addProtein, addFat, addCarbs, addSugar, addSodium, addFiber)

            try {
                val fileOutputStream = requireContext().openFileOutput("nutrition_data.csv", Context.MODE_PRIVATE)
                val outputStreamWriter = OutputStreamWriter(fileOutputStream)

                outputStreamWriter.write(data.joinToString(","))
                outputStreamWriter.close()

                Toast.makeText(requireContext(), "Data saved to CSV file", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            binding.editTextTextCalories.text.clear()
            binding.editTextTextProtein.text.clear()
            binding.editTextTextFat.text.clear()
            binding.editTextTextCarbohydrates.text.clear()
            binding.editTextTextSugar.text.clear()
            binding.editTextTextSodium.text.clear()
            binding.editTextTextFiber.text.clear()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedMeal = parent?.getItemAtPosition(position).toString()
        Toast.makeText(requireContext(), "Selected meal: $selectedMeal", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}



//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.fragment.app.Fragment
//import com.example.calorie_calendar.R
//import com.example.calorie_calendar.databinding.FragmentAddBinding
//import com.opencsv.CSVWriter
//import java.io.File
//import java.io.FileWriter
//import java.io.IOException
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//
//data class NutritionData(
//    val calories: String,
//    val protein: String,
//    val fat: String,
//    val carbs: String,
//    val sugar: String,
//    val sodium: String,
//    val fiber: String
//)
//
//class AddFragment : Fragment(), AdapterView.OnItemSelectedListener {
//
//    private var _binding: FragmentAddBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentAddBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        setupSpinner()
//        setupSubmitButton()
//
//        return root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    private fun setupSpinner() {
//        val spinner: Spinner = binding.spinnerMeals
//        ArrayAdapter.createFromResource(
//            requireContext(),
//            R.array.Meals,
//            android.R.layout.simple_spinner_dropdown_item
//        ).also { adapter ->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinner.adapter = adapter
//        }
//        spinner.onItemSelectedListener = this
//    }
//
//    private fun setupSubmitButton() {
//        binding.button.setOnClickListener {
//            val nutritionData = NutritionData(
//                binding.editTextTextCalories.text.toString(),
//                binding.editTextTextProtein.text.toString(),
//                binding.editTextTextFat.text.toString(),
//                binding.editTextTextCarbohydrates.text.toString(),
//                binding.editTextTextSugar.text.toString(),
//                binding.editTextTextSodium.text.toString(),
//                binding.editTextTextFiber.text.toString()
//            )
//            appendToCSV(nutritionData)
//            clearEditTexts()
//        }
//    }
//
//    private fun appendToCSV(nutritionData: NutritionData) {
//
//        try {
//            val file = File(requireContext().filesDir, "final.csv")
//            if (!file.exists()) {
//                file.createNewFile()
//            }
//            val csvWriter = CSVWriter(FileWriter(file, true))
//            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
//            val currentDate = sdf.format(Date())
//
//            csvWriter.writeNext(arrayOf(currentDate, nutritionData.calories, nutritionData.protein, nutritionData.fat, nutritionData.carbs, nutritionData.sugar, nutritionData.sodium, nutritionData.fiber))
//            csvWriter.close()
//            Toast.makeText(requireContext(), "Data saved to CSV", Toast.LENGTH_SHORT).show()
//        } catch (e: IOException) {
//            Toast.makeText(requireContext(), "Error writing to CSV: ${e.message}", Toast.LENGTH_SHORT).show()
//            e.printStackTrace()
//        }
//    }
//
//    private fun clearEditTexts() {
//        binding.editTextTextCalories.text.clear()
//        binding.editTextTextProtein.text.clear()
//        binding.editTextTextFat.text.clear()
//        binding.editTextTextCarbohydrates.text.clear()
//        binding.editTextTextSugar.text.clear()
//        binding.editTextTextSodium.text.clear()
//        binding.editTextTextFiber.text.clear()
//    }
//
//    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//        val selectedMeal = parent.getItemAtPosition(position).toString()
//        Toast.makeText(requireContext(), "Selected meal: $selectedMeal", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onNothingSelected(parent: AdapterView<*>) {
//        // Do nothing
//    }
//}
//
