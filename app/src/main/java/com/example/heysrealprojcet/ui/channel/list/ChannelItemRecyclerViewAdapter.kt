package com.example.heysrealprojcet.ui.channel.list

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.ChannelItemViewBinding
import com.example.heysrealprojcet.enums.ChannelStatus
import com.example.heysrealprojcet.model.Channel

class ChannelItemRecyclerViewAdapter(
   private val type: MutableList<Channel>,
   private val onClickListener: () -> Unit) :
   RecyclerView.Adapter<ChannelItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: ChannelItemViewBinding

   inner class ViewHolder(private val binding: ChannelItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(channel: Channel) {
         binding.status.text = channel.status.status
         binding.image.setImageResource(channel.resId)
         binding.title.text = channel.title
         binding.period.text = "개설한지 ${channel.period}일"
         binding.view.text = channel.view.toString()
         binding.root.setOnClickListener { onClickListener.invoke() }

         val bgShape = binding.status.background as GradientDrawable
         when (channel.status) {
            ChannelStatus.New -> bgShape.setColor(Color.parseColor("#34D676"))
            ChannelStatus.Closed -> bgShape.setColor(Color.parseColor("#828282"))
            else -> {
               bgShape.setColor(Color.parseColor("#FD4158"))
               // 지금은 전체 인원으로 표기
               // TODO (전체 인원) - (신청 인원) 으로 수정 필요
               binding.status.text = if (channel.capacity != 0) {
                  "${channel.capacity}명 ${channel.status.status}"
               } else {
                  "마감"
               }
               binding.status.setPadding(6, 0, 6, 0)
            }
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