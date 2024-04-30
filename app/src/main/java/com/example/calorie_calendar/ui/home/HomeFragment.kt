package com.example.calorie_calendar.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.calorie_calendar.R
import com.example.calorie_calendar.databinding.FragmentHomeBinding
import com.example.calorie_calendar.ui.add.AddFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btnAdd = root.findViewById<ImageButton>(R.id.imageButton)
        btnAdd.setOnClickListener {
            val navController = Navigation.findNavController(root)
            navController.navigate(R.id.navigation_add)
        }

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


//imagebutton = breakfast
//imagebutton2 = lunch
//imagebutton3 = dinner
//imagebutton4 = snack

//        val lunchButton = root.findViewById<Button>(R.id.imageButton2)
//        lunchButton.setOnClickListener {
//            val fragmentManager = parentFragmentManager.beginTransaction()
//            fragmentManager.replace(R.id.container, AddFragment())
//            fragmentManager.addToBackStack(null)
//            fragmentManager.commit()
//
//        }