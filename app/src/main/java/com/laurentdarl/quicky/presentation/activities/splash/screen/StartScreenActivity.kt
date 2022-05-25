package com.laurentdarl.quicky.presentation.activities.splash.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.laurentdarl.quicky.R
import com.laurentdarl.quicky.presentation.activities.onboarding.OnboardingActivity
import java.util.*

class StartScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                val intent = Intent(applicationContext, OnboardingActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 1500)
    }
}