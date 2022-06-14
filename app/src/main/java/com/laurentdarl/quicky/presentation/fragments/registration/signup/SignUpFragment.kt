package com.laurentdarl.quicky.presentation.fragments.registration.signup

import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.laurentdarl.quicky.R
import com.laurentdarl.quicky.data.login.User
import com.laurentdarl.quicky.databinding.FragmentSignUpBinding
import java.security.SecureRandom

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: AlertDialog
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        dialog = AlertDialog.Builder(requireContext())
            .setTitle("Signup Status")
            .setMessage("Please wait while your details are saved...")
            .setCancelable(true)
            .create()

        binding.btnSignup.setOnClickListener {
            signUp()
        }

        binding.tvSignin.setOnClickListener {
            navigateToLogin()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun signUp() {
        // create instance of SecureRandom class
        val rand = SecureRandom()
        // Generate random integers in range 0 to 9999
        val value = rand.nextInt(100000000)
        val uid = "Quicky$value"
        val name = binding.etSignupNames.text.toString()
        val email = binding.etSignupEmail.text.toString()
        val phone = binding.etSignupPhone.text.toString()
        val password = binding.etSignupPassword.text.toString()
        val repeatPassword = binding.etRepeatPassword.text.toString()

        validateCredentials(name, email, phone, password, repeatPassword, uid)
    }

    private fun validateCredentials(
        name: String,
        email: String,
        phone: String,
        password: String,
        repeatPassword: String,
        uid: String
    ) {
        val phoneCheck = phone.any { it.isDigit() }
        val passwordStrength = password.any { it.isDigit() } &&
               password.any { it.isLetter() } && password.contains(Regex(getString(R.string.regex_patterns))) //check
        // that password contains, alphabet, digit and special character
        val fullName = name.contains(Regex("([a-zA-Z\\-]+){3,}\\s+([a-zA-Z\\-]+){3,}")) //check for space between names
        when {
            name.isBlank() -> Toast.makeText(
                requireContext(),
                "Name field is compulsory",
                Toast.LENGTH_SHORT
            ).show()
            !fullName -> Toast.makeText(
                requireContext(),
                "Please input a surname and at least any other userName",
                Toast.LENGTH_SHORT
            ).show()
            email.isBlank() -> Toast.makeText(
                requireContext(),
                "Email field is compulsory",
                Toast.LENGTH_SHORT
            ).show()
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> Toast.makeText(
                requireContext(),
                "Please input a valid email address",
                Toast.LENGTH_SHORT
            ).show()
            phone.isBlank() -> Toast.makeText(
                requireContext(),
                "Phone field is compulsory",
                Toast.LENGTH_SHORT
            ).show()
            phone.length != 11 -> Toast.makeText(
                requireContext(),
                "Phone number length should be 11 digits",
                Toast.LENGTH_SHORT
            ).show()
            !phoneCheck -> Toast.makeText(
                requireContext(),
                "Please input a valid phone number",
                Toast.LENGTH_SHORT
            ).show()
            password.isBlank() -> Toast.makeText(
                requireContext(),
                "Password field is compulsory",
                Toast.LENGTH_SHORT
            ).show()
            !passwordStrength -> Toast.makeText(
                requireContext(), "Your password must contain at least one " +
                        "letter, number and special character", Toast.LENGTH_SHORT
            ).show()
            password != repeatPassword -> Toast.makeText(
                requireContext(),
                "Passwords don't match",
                Toast.LENGTH_SHORT
            ).show()
            else -> {
                registerUser(
                    User(
                        name, email, phone, password, uid, repeatPassword
                    )
                )
            }
        }
    }

    private fun registerUser(
        users: User
    ) {
        val user = auth.currentUser
        dialog.show()
        auth.createUserWithEmailAndPassword(users.email!!, users.password!!)
            .addOnCompleteListener { task ->
                dialog.hide()
                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(), "Signed up successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Send email verification
                    user?.sendEmailVerification()?.addOnCompleteListener { verify ->
                        dialog.hide()
                        if (verify.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Please check your email to verify your account!",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                verify.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    saveUserDetails(
                        users
                    )
                    auth.signOut()

                } else {
                    dialog.hide()
                    Toast.makeText(
                        requireContext(),
                        task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun saveUserDetails(
        user: User
    ) {

        databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("Users").child("email").exists()) {
                    Toast.makeText(
                        requireContext(),
                        "This ${user.email} already exist. Try using a different email",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (snapshot.child("Users").child("phone").exists()) {
                    Toast.makeText(
                        requireContext(),
                        "This phone number ${user.phone} already exists. " +
                                "Try using a different number",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val hashMap: HashMap<String, Any?> = HashMap()
                    hashMap["userName"] = user.userName
                    hashMap["email"] = user.email
                    hashMap["phone"] = user.phone
                    hashMap["uid"] = user.uid

                    databaseReference.child("Users").child(user.uid!!)
                        .updateChildren(hashMap)
                        .addOnCompleteListener { task ->
                            auth.signOut()
                            if (task.isSuccessful) {
                                navigateToLogin()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Could not save you details",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Network error: Please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun navigateToLogin() {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.pager)
        viewPager?.currentItem = 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}