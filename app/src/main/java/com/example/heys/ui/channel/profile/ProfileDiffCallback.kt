package com.example.heys.ui.channel.profile

import androidx.recyclerview.widget.DiffUtil
import com.example.heys.model.UserProfile

object ProfileDiffCallback : DiffUtil.ItemCallback<UserProfile>() {
   override fun areItemsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
      return oldItem == newItem
   }

   override fun areContentsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
      return oldItem.name == newItem.name
   }
}