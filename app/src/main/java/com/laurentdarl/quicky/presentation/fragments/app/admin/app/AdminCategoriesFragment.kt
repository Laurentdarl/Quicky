package com.laurentdarl.quicky.presentation.fragments.app.admin.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.laurentdarl.quicky.data.categories.Categories
import com.laurentdarl.quicky.databinding.FragmentAdminCategoriesBinding

class AdminCategoriesFragment : Fragment() {

    private var _binding: FragmentAdminCategoriesBinding? = null
    private val binding get() = _binding!!
    private val categories = Categories()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminCategoriesBinding.inflate(layoutInflater)
        val breakfast = categories.breakfast
        val pastries = categories.pastries
        val meats = categories.meats
        val fries = categories.fries
        val grills = categories.grills
        val drinks = categories.drinks
        val desserts = categories.desserts
        val soups = categories.soups
        val events = categories.events
        val cakes = categories.cakes

        binding.cvBreakfast.setOnClickListener {
            category(breakfast)
        }

        binding.cvPastries.setOnClickListener {
            category(pastries)
        }

        binding.cvMeats.setOnClickListener {
            category(meats)
        }

        binding.cvFries.setOnClickListener {
            category(fries)
        }

        binding.cvGrills.setOnClickListener {
            category(grills)
        }

        binding.cvDrinks.setOnClickListener {
            category(drinks)
        }

        binding.cvDesserts.setOnClickListener {
            category(desserts)
        }

        binding.cvSoups.setOnClickListener {
            category(soups)
        }

        binding.cvCakes.setOnClickListener {
            category(cakes)
        }

        binding.cvEvents.setOnClickListener {
            category(events)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun category(category: String) {
        val direction =
            AdminCategoriesFragmentDirections.actionAdminCategoriesFragmentToAddNewItemFragment()
                .setCategory(category)
        findNavController().navigate(direction)
    }
}