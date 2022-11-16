package com.example.heysrealprojcet.ui.user.myChannel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MyChannelItemViewBinding
import com.example.heysrealprojcet.enums.ChannelType
import com.example.heysrealprojcet.model.Channel

class MyChannelItemRecyclerViewAdapter(private val type: MutableList<Channel>, private val onClickListener: () -> Unit) :
   RecyclerView.Adapter<MyChannelItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: MyChannelItemViewBinding

   inner class ViewHolder(private val binding: MyChannelItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(channel: Channel) {
         binding.card.setBackgroundResource(channel.resId)
         binding.channelTitle.text = channel.title
         binding.channelType.text = channel.type.type

         when (channel.type) {
            ChannelType.Contest -> binding.channelType.setBackgroundResource(R.drawable.bg_7b61ff_radius_30)
            ChannelType.Activity -> binding.channelType.setBackgroundResource(R.drawable.bg_fd494a_radius_30)
            else -> binding.channelType.setBackgroundResource(R.drawable.bg_262626_radius_30)
         }
         binding.goToChat.setOnClickListener { onClickListener.invoke() }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = MyChannelItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(type[position])
   }

   override fun getItemCount(): Int {
      return type.size
   }
}