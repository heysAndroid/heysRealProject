package com.example.heysrealprojcet.ui.channel.profile

import androidx.recyclerview.widget.DiffUtil
import com.example.heysrealprojcet.model.UserProfile

object ProfileDiffCallback : DiffUtil.ItemCallback<UserProfile>() {
   override fun areItemsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
      return oldItem == newItem
   }

   override fun areContentsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
      return oldItem.name == newItem.name
   }
}