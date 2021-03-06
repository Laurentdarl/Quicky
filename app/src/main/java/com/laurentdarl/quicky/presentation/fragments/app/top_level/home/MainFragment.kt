package com.laurentdarl.quicky.presentation.fragments.app.top_level.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.laurentdarl.quicky.databinding.FragmentMainBinding
import com.laurentdarl.quicky.presentation.fragments.registration.container.HomeFragmentDirections

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater)


        // Inflate the layout for this fragment
        return binding.root
    }
}