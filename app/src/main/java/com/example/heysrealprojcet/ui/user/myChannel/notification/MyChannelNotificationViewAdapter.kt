package com.example.heysrealprojcet.ui.user.myChannel.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.NotificationItemViewBinding
import com.example.heysrealprojcet.model.NotificationList

class MyChannelNotificationViewAdapter(
   private val list: MutableList<NotificationList>
   ) : RecyclerView.Adapter<MyChannelNotificationViewAdapter.ViewHolder>() {
   private lateinit var binding: NotificationItemViewBinding

   inner class ViewHolder(private val binding: NotificationItemViewBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: NotificationList) {
         binding.image.setImageResource(item.image)
         binding.title.text = item.title
         binding.content.text = item.content
         binding.time.text = item.time
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = NotificationItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(list[position])
   }

   override fun getItemCount(): Int = list.size
}