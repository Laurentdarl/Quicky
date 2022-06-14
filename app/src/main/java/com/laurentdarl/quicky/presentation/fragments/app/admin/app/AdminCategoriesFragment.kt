package com.laurentdarl.quicky.presentation.fragments.app.admin.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.laurentdarl.quicky.R
import com.laurentdarl.quicky.databinding.FragmentAdminCategoriesBinding

class AdminCategoriesFragment : Fragment() {

    private var _binding: FragmentAdminCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminCategoriesBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        return binding.root
    }
}