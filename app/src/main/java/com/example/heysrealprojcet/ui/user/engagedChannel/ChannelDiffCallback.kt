package com.example.heysrealprojcet.ui.user.engagedChannel

import androidx.recyclerview.widget.DiffUtil
import com.example.heysrealprojcet.model.Channel

object ChannelDiffCallback : DiffUtil.ItemCallback<Channel>() {
   override fun areItemsTheSame(oldItem: Channel, newItem: Channel): Boolean {
      return oldItem == newItem
   }

   override fun areContentsTheSame(oldItem: Channel, newItem: Channel): Boolean {
      return oldItem.title == newItem.title
   }
}