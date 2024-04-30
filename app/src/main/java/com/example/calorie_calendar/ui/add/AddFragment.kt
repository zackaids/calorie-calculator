package com.example.calorie_calendar.ui.add


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.calorie_calendar.R
import com.example.calorie_calendar.databinding.FragmentAddBinding
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

            val data = listOf(addCalories, addProtein, addFat, addCarbs, addSugar, addSodium, addFiber)

            try {
                val fileName = "nutrition_data_$currentDate.csv"
                val fileOutputStream = requireContext().openFileOutput(fileName, Context.MODE_APPEND)
                val outputStreamWriter = OutputStreamWriter(fileOutputStream)

                outputStreamWriter.write(data.joinToString(",") + "\n")
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


