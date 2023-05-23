package com.example.heysrealprojcet.ui.channel.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.heysrealprojcet.App
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelItemViewBinding
import com.example.heysrealprojcet.model.network.ChannelList

class ChannelItemRecyclerViewAdapter(
   private val type: MutableList<ChannelList>,
   private val onClickListener: () -> Unit) :
   RecyclerView.Adapter<ChannelItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: ChannelItemViewBinding

   inner class ViewHolder(private val binding: ChannelItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(channel: ChannelList) {
         binding.apply {
            tvTitle.text = channel.name
            tvPastDay.text = "개설한지 ${channel.dday}일"
            tvViewCount.text = "${channel.viewCount}"
            Glide.with(App.getInstance().applicationContext).load(channel.thumbnailUri).into(imgThumbnail)
            root.setOnClickListener { onClickListener.invoke() }
         }

         // 모집 마감일 지나지 않음
         if (channel.dday > 0) {
            when {
               // 최대 참여정원 4명 이상
               4 <= channel.joinRemainCount -> {
                  binding.tvStatus.text = "참여가능"
                  binding.tvStatus.setBackgroundResource(R.drawable.bg_34d676_radius_4)
               }

               1 <= channel.joinRemainCount -> {
                  binding.tvStatus.text = "${channel.joinRemainCount}명 참여가능"
                  binding.tvStatus.setBackgroundResource(R.drawable.bg_fd494a_radius_4)
               }

               else -> {
                  binding.tvStatus.text = "마감"
                  binding.tvStatus.setBackgroundResource(R.drawable.bg_e1e1e1_radius_4)
               }
            }
         } else {
            binding.tvStatus.text = "마감"
            binding.tvStatus.setBackgroundResource(R.drawable.bg_e1e1e1_radius_4)
         }

      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = ChannelItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(type[position])
   }

   override fun getItemCount(): Int {
      return type.size
   }
}