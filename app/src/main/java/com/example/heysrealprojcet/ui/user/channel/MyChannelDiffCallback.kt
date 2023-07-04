package com.example.heysrealprojcet.ui.user.channel

import androidx.recyclerview.widget.DiffUtil
import com.example.heysrealprojcet.model.network.MyChannel

object MyChannelDiffCallback : DiffUtil.ItemCallback<MyChannel>() {
   override fun areItemsTheSame(oldItem: MyChannel, newItem: MyChannel): Boolean {
      return oldItem == newItem
   }

   override fun areContentsTheSame(oldItem: MyChannel, newItem: MyChannel): Boolean {
      return oldItem.id == newItem.id
   }
}