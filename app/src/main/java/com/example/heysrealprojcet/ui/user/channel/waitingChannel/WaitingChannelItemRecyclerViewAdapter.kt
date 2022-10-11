package com.example.heysrealprojcet.ui.user.channel.waitingChannel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.WaitingChannelItemViewBinding
import com.example.heysrealprojcet.model.Channel
import com.example.heysrealprojcet.ui.user.channel.ChannelDiffCallback

class WaitingChannelItemRecyclerViewAdapter(private val channel: MutableList<Channel>, private val onClickListener: () -> Unit) :
   ListAdapter<Channel, WaitingChannelItemRecyclerViewAdapter.ViewHolder>(ChannelDiffCallback) {
   private lateinit var binding: WaitingChannelItemViewBinding

   inner class ViewHolder(private val binding: WaitingChannelItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(channel: Channel) {
         binding.channelName.text = channel.title
         binding.profile.setImageResource(channel.resId)
         binding.cancelButton.setOnClickListener { onClickListener.invoke() }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = WaitingChannelItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(channel[position])
   }

   override fun getItemCount(): Int {
      return channel.size
   }
}