package com.hey.heys.ui.channel.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hey.heys.App
import com.hey.heys.R
import com.hey.heys.databinding.ChannelItemViewBinding
import com.hey.heys.model.network.ChannelList

class ChannelItemRecyclerViewAdapter :
   PagingDataAdapter<ChannelList, ChannelItemRecyclerViewAdapter.ViewHolder>(diffUtil) {
   var onClickListener: ((Int) -> Unit)? = null
   private lateinit var binding: ChannelItemViewBinding

   inner class ViewHolder(private val binding: ChannelItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(channel: ChannelList) {
         binding.apply {
            tvTitle.text = channel.name
            tvPastDay.text = "개설한지 ${channel.pastDay+1}일"
            tvViewCount.text = "${channel.viewCount}"

            Glide.with(App.getInstance().applicationContext)
               .load(channel.thumbnailUri)
               .error(R.drawable.bg_thumbnail_default).into(imgThumbnail)
            root.setOnClickListener { onClickListener?.invoke(channel.id) }
         }

         // 모집 마감일 지나지 않음
         if (channel.dday >= 0) {
            when {
               // 최대 참여정원 4명 이상
               4 <= channel.capacityCount -> {
                  binding.tvStatus.text = "참여가능"
                  binding.tvStatus.setBackgroundResource(R.drawable.bg_status_available)
               }

               2 <= channel.capacityCount -> {
                  binding.tvStatus.text = "${channel.joinRemainCount}명 참여가능"
                  binding.tvStatus.setBackgroundResource(R.drawable.bg_status_almost_closed)
               }

               else -> {
                  binding.tvStatus.text = "마감"
                  binding.tvStatus.setBackgroundResource(R.drawable.bg_status_closed)
               }
            }
         } else {
            binding.tvStatus.text = "마감"
            binding.tvStatus.setBackgroundResource(R.drawable.bg_status_closed)
         }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = ChannelItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val currentItem = getItem(position)
      currentItem?.let { holder.bind(it) }
   }

   companion object {
      val diffUtil = object : DiffUtil.ItemCallback<ChannelList>() {
         override fun areItemsTheSame(oldItem: ChannelList, newItem: ChannelList): Boolean {
            return oldItem.id == newItem.id
         }

         override fun areContentsTheSame(oldItem: ChannelList, newItem: ChannelList): Boolean {
            return oldItem == newItem
         }
      }
   }
}