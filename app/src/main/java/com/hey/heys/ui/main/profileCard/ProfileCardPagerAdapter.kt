package com.hey.heys.ui.main.profileCard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hey.heys.databinding.ProfileCardPagerItemViewBinding
import com.hey.heys.model.ProfileCardDescription

class ProfileCardPagerAdapter : ListAdapter<ProfileCardDescription, ProfileCardPagerAdapter.ProfileCardItemViewHolder>(CustomDiffCallback) {
   private lateinit var binding: ProfileCardPagerItemViewBinding

   inner class ProfileCardItemViewHolder(private val binding: ProfileCardPagerItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(profileCard: ProfileCardDescription) {
         binding.description.text = profileCard.text
         binding.imageView.setImageDrawable(profileCard.image)
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileCardItemViewHolder {
      binding = ProfileCardPagerItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ProfileCardItemViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ProfileCardItemViewHolder, position: Int) {
      holder.bind(getItem(position))
   }

   object CustomDiffCallback : DiffUtil.ItemCallback<ProfileCardDescription>() {
      override fun areItemsTheSame(oldItem: ProfileCardDescription, newItem: ProfileCardDescription): Boolean {
         return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: ProfileCardDescription, newItem: ProfileCardDescription): Boolean {
         return oldItem == newItem
      }
   }
}