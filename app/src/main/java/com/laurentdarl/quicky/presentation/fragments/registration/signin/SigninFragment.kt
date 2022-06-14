package com.laurentdarl.quicky.presentation.fragments.registration.signin

import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.laurentdarl.quicky.R
import com.laurentdarl.quicky.data.login.User
import com.laurentdarl.quicky.databinding.FragmentSigninBinding
import com.laurentdarl.quicky.presentation.fragments.registration.container.HomeFragmentDirections

class SigninFragment : Fragment() {

    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: AlertDialog
    private lateinit var databaseReference: DatabaseReference
    private var MAIN_DB = "Users"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSigninBinding.inflate(layoutInflater)

        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        dialog = AlertDialog.Builder(requireContext())
            .setTitle("Verify Email")
            .setMessage("Send verification to email?")
            .setCancelable(true)
            .setPositiveButton("Send") { _, _ ->
                sendEmailVerification()
            }
            .setNegativeButton("Cancel") { d, _ ->
                d.dismiss()
            }
            .create()

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.tvSignup.setOnClickListener {
            signupScreen()
        }

        binding.navigateToAdminLogin.setOnClickListener {
            binding.btnLogin.text = "Admin login"
            binding.forgotPassword.text = "Not an Admin?"
            binding.forgotPassword.isClickable = false
            binding.tvSignup.visibility = View.GONE
            binding.tvAdminBack.visibility = View.VISIBLE
            MAIN_DB = "Admin"
        }

        binding.tvAdminBack.setOnClickListener {
            binding.btnLogin.text = "Login"
            binding.forgotPassword.text = "Forgot password?"
            binding.forgotPassword.isClickable = true
            binding.tvSignup.visibility = View.VISIBLE
            binding.tvAdminBack.visibility = View.GONE
            MAIN_DB = "Users"
        }

        binding.forgotPassword.setOnClickListener {
            val actions = HomeFragmentDirections.actionNavHomeToForgotPasswordFragment()
            findNavController().navigate(actions)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun login() {
        val email = binding.etLoginEmail.text.toString()
        val password = binding.etLoginPassword.text.toString()
        validateCredentials(email, password)
    }

    private fun validateCredentials(email: String, password: String) {
        val passwordStrength = password.any { it.isDigit() } &&
                password.any { it.isLetter() } && password.contains(Regex(getString(R.string.regex_patterns)))

        if (email.isBlank()) {
            Toast.makeText(context, "Email field is compulsory", Toast.LENGTH_SHORT).show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Please input a valid email address", Toast.LENGTH_SHORT).show()
        } else if (password.isBlank()) {
            Toast.makeText(context, "Password field is compulsory", Toast.LENGTH_SHORT).show()
        } else if (password.length < 8) {
            Toast.makeText(
                context,
                "Password length should be at least 8 characters",
                Toast.LENGTH_SHORT
            ).show()
        } else if (!passwordStrength) {
            Toast.makeText(
                context,
                "Your password must contain at least one " +
                        "letter, number and special character",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            saveUserDetails(
                email, password
            )
        }
    }

    private fun signupScreen() {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.pager)
        viewPager?.currentItem = 1
    }

    private fun sendEmailVerification() {
        val user = auth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    requireContext(),
                    "Please check your email to verify your account!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    task.exception!!.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun saveUserDetails(
        email: String,
        password: String
    ) {
        if (MAIN_DB == "Users") {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(), "Logged in successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        navigateToHome()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else if (MAIN_DB == "Admin" && password
            == "AIESECer@2012"
        ) {
            navigateToAdminLogin()
        }
    }

    private fun navigateToHome() {
        val actions = HomeFragmentDirections.actionNavHomeToMainFragment()
        findNavController().navigate(actions)
    }

    private fun navigateToAdminLogin() {
        val actions = HomeFragmentDirections.actionNavHomeToAdminCategoriesFragment()
        findNavController().navigate(actions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}