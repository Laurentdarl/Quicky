package com.laurentdarl.quicky.presentation.fragments.app.admin.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.laurentdarl.quicky.databinding.FragmentAddNewItemBinding

class AddNewItemFragment : Fragment() {
    private val args: AddNewItemFragmentArgs by navArgs()
    private var _binding: FragmentAddNewItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNewItemBinding.inflate(layoutInflater)

        val bae = args.category.toString()
        binding.tvAddItem.text = bae

        // Inflate the layout for this fragment
        return binding.root
    }
}