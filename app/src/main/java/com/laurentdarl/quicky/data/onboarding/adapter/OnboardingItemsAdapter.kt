package com.laurentdarl.quickyfood.data.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laurentdarl.quicky.databinding.OnboardingItemBinding
import com.laurentdarl.quickyfood.data.onboarding.model.OnboardingItem

class OnboardingItemsAdapter(private val onboards: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingItemsAdapter.OnboardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = OnboardingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnboardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(onboards[position])
    }

    override fun getItemCount(): Int = onboards.size

    inner class OnboardingViewHolder(private val itemBinding: OnboardingItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(onboardingItem: OnboardingItem) {
            itemBinding.apply {
                Glide.with(onboardingImage).load(onboardingItem.image).into(itemBinding.onboardingImage)
                tvOnboardingMessage.text = onboardingItem.message
                tvOnboardingTitle.text = onboardingItem.title
            }
        }
    }
}