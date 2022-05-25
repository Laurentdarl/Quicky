package com.laurentdarl.quicky.presentation.fragments.registration.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.laurentdarl.quicky.data.login.adapters.RegistrationViewPager
import com.laurentdarl.quicky.databinding.FragmentHomeBinding
import com.laurentdarl.quicky.presentation.fragments.registration.signin.SigninFragment
import com.laurentdarl.quicky.presentation.fragments.registration.signup.SignUpFragment

class HomeFragment : Fragment() {

    private lateinit var pagerAdapter: RegistrationViewPager
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        val fragmentList = arrayListOf(
            SigninFragment(),
            SignUpFragment()
        )
        pagerAdapter = RegistrationViewPager(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        binding.pager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) {tab,position ->

            when(position) {
                0 -> {
                    tab.text = "Signin"
                }
                1 -> {
                    tab.text = "Signup"
                }
            }
        }.attach()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}