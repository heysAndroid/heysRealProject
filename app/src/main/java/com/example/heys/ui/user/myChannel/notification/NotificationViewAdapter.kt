package com.example.heys.ui.user.myChannel.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.heys.R
import com.example.heys.databinding.NotificationItemViewBinding
import com.example.heys.model.network.Notification

class NotificationViewAdapter(private val context: Context) : ListAdapter<Notification, NotificationViewAdapter.ViewHolder>(DiffCallback) {
   private lateinit var binding: NotificationItemViewBinding

   inner class ViewHolder(private val binding: NotificationItemViewBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(notification: Notification) {
         binding.tvTitle.text = notification.title
         binding.tvContent.text = notification.content.replace("[", "").replace("]", "")
         binding.tvTime.text = notification.createdAt

         // 읽음 여부에 따라 background color 설정
         if (notification.isRead) {
            binding.llNoti.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
         } else {
            binding.llNoti.setBackgroundColor(ContextCompat.getColor(context, R.color.color_f8f9fc))
         }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = NotificationItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(getItem(position))
   }

   object DiffCallback : DiffUtil.ItemCallback<Notification>() {
      override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
         return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
         return oldItem.channelId == newItem.channelId
      }
   }
}