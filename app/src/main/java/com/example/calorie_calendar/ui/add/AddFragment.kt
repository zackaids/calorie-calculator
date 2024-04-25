package com.example.calorie_calendar.ui.add

import android.app.Activity
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

class AddFragment : Fragment() {

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
        ).also { adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        return root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        val submitButton = findViewById<Button>(R.id.button)
        super.onCreate(savedInstanceState)
        submitButton.setOnClickListener {
            val addCalories = findViewById<TextView>(R.id.editTextTextCalories).text.toString()
            val addProtein = findViewById<TextView>(R.id.editTextTextProtein).text.toString()
            val addFat = findViewById<TextView>(R.id.editTextTextFat).text.toString()
            val addCarbs = findViewById<TextView>(R.id.editTextTextCarbohydrates).text.toString()
            val addSugar = findViewById<TextView>(R.id.editTextTextSugar).text.toString()
            val addSodium = findViewById<TextView>(R.id.editTextTextSodium).text.toString()
            val addFiber = findViewById<TextView>(R.id.editTextTextFiber).text.toString()

        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val spinner : Spinner = findViewById(R.id.spinner_meals)
        spinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}