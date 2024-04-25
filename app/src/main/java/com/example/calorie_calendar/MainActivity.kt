package com.example.calorie_calendar

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.calorie_calendar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_add, R.id.navigation_calendar
            )
        )
        val spinner: Spinner = findViewById(R.id.spinner_meals)
        ArrayAdapter.createFromResource(
            this,
            R.array.Meals,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }
}
