package com.laurentdarl.quicky.presentation.activities.splash.screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.laurentdarl.quicky.R
import com.laurentdarl.quicky.presentation.activities.main.MainActivity
import com.laurentdarl.quicky.presentation.activities.onboarding.OnboardingActivity
import com.laurentdarl.quicky.presentation.fragments.registration.container.HomeFragment
import java.util.*


class StartScreenActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null) {
            val intent = Intent(this@StartScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    val intent = Intent(applicationContext, OnboardingActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }, 1500)
        }
        }


}