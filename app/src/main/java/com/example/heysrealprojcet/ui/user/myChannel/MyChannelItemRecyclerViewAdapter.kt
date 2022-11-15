package com.example.heysrealprojcet.ui.user.myChannel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.MyChannelItemViewBinding
import com.example.heysrealprojcet.model.Channel

class MyChannelItemRecyclerViewAdapter(private val type: MutableList<Channel>, private val onClickListener: () -> Unit) :
   RecyclerView.Adapter<MyChannelItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: MyChannelItemViewBinding

   inner class ViewHolder(private val binding: MyChannelItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(channel: Channel) {
         binding.card.setBackgroundResource(channel.resId)
         binding.channelTitle.text = channel.title
         binding.channelType.text = channel.type.type
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