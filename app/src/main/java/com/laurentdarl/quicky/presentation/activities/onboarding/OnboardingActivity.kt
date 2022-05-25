package com.laurentdarl.quicky.presentation.activities.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.laurentdarl.quicky.R
import com.laurentdarl.quicky.databinding.ActivityOnboardingBinding
import com.laurentdarl.quicky.presentation.activities.main.MainActivity
import com.laurentdarl.quickyfood.data.onboarding.adapter.OnboardingItemsAdapter
import com.laurentdarl.quickyfood.data.onboarding.model.OnboardingItem

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var onBoardingItemsAdapter: OnboardingItemsAdapter
    private lateinit var indicatorsContainer: LinearLayout
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        button = binding.btnCta
        setUpOnBoardItems()
        setUpIndicators()
        currentIndicator(0)
    }

    private fun setUpOnBoardItems() {
        onBoardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
                    image = R.drawable.quicky_app_eat,
                    title = "Healthy meals for you",
                    message =
                    "You came here because you want to have a good meal. " +
                            "\n We prepare our meals hygienically, specially for you. " +
                            "\n \n Come and eat!"
                ),
                OnboardingItem(
                    image = R.drawable.quicky_app_environment,
                    title = "Care for the environment",
                    message = "We only have one earth that sustains us. " +
                            "\n We prepare your meals with environmentally friendly processes. " +
                            "\n \n Let us protect our earth!"
                ),
                OnboardingItem(
                    image = R.drawable.quicky_app1_paperbag,
                    title = "Creative packaging",
                    message = "You are special and deserve a special treatment. " +
                            "\n We understand that the way your meals are served is" +
                            " just as important as the processes that make them. " +
                            "\n \n Enjoy the treat!"
                )
            )
        )
        val onBoardViewpager = binding.viewpagerOnboarding
        onBoardViewpager.apply {
            adapter = onBoardingItemsAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentIndicator(position)
                }
            })
        }
        (onBoardViewpager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        binding.btnSkip.setOnClickListener {
            navigateToLoginActivity()
        }

        binding.btnCta.setOnClickListener {
            if (onBoardViewpager.currentItem +1 < onBoardingItemsAdapter.itemCount) {
                onBoardViewpager.currentItem += 1
            } else navigateToLoginActivity()
        }
    }

    private fun setUpIndicators() {
        indicatorsContainer = binding.linearIndicator
        val indicators = arrayOfNulls<ImageView>(onBoardingItemsAdapter.itemCount)
        val layOutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layOutParams.setMargins(8,0,8,0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layOutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun currentIndicator(position: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background)
                )
                if (i == 0 || i == 1) {
                    button.text = "Next"
                    val drawable = ContextCompat.getDrawable(this, R.drawable.ic_next)
                    button.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
                } else if (i == 2) {
                    button.text = "Get Started"
                    val drawable = ContextCompat.getDrawable(this, R.drawable.ic_next)
                    button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                }
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background)
                )
            }
        }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}