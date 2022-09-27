package com.example.heysrealprojcet.ui.user.myChannel.privateNotification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.PrivateNotificationItemViewBinding
import com.example.heysrealprojcet.model.NotificationList

class MyChannelPrivateNotificationViewAdapter(
   private val list: MutableList<NotificationList>
   ) : RecyclerView.Adapter<MyChannelPrivateNotificationViewAdapter.ViewHolder>() {
   private lateinit var binding: PrivateNotificationItemViewBinding

   inner class ViewHolder(private val binding: PrivateNotificationItemViewBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: NotificationList) {
         binding.image.setImageResource(item.image)
         binding.title.text = item.title
         if (item.content.length >= 20) {
            binding.content.text = item.content.slice(IntRange(0, 19)) + "..."
         } else {
            binding.content.text = item.content
         }
         binding.time.text = item.time
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = PrivateNotificationItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(list[position])
   }

   override fun getItemCount(): Int = list.size

}