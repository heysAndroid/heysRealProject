package com.example.heysrealprojcet.ui.user.myChannel.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MyChannelItemViewBinding
import com.example.heysrealprojcet.model.network.MyChannel

class MyChannelItemRecyclerViewAdapter(private val type: MutableList<MyChannel>, private val onClickListener: (Int) -> Unit) :
   RecyclerView.Adapter<MyChannelItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: MyChannelItemViewBinding

   inner class ViewHolder(private val binding: MyChannelItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(channel: MyChannel) {
         binding.root.setOnClickListener { onClickListener.invoke(channel.id) }
         Log.i("channel: ", channel.type)
//         binding.channelType.text = ChannelType.valueOf(channel.type).type
         binding.channelTitle.text = channel.name

         // 마감/남은 일수 표시
         if (channel.isClosed) {
            binding.dday.text = "마감"
            binding.dday.setBackgroundResource(R.drawable.bg_e1e1e1_radius_4)
         } else {
            binding.dday.text = "D-${channel.dday}"
            when (channel.dday) {
               0 -> {
                  binding.dday.setBackgroundResource(R.drawable.bg_e1e1e1_radius_4)
               }

               in 1..6 -> {
                  binding.dday.setBackgroundResource(R.drawable.bg_fd494a_radius_4)
               }

               else -> {
                  binding.dday.setBackgroundResource(R.drawable.bg_34d676_radius_4)
               }
            }
         }
         /*
                  when (channel.type) {
                     ChannelType.Contest.typeEng -> {
                        // 카드 배경
                        binding.card.setBackgroundResource(R.drawable.bg_channel_contest_card)
                        // 채널 타입
                        binding.channelType.setBackgroundResource(R.drawable.bg_7b61ff_radius_30)

                        // 리더 표시
                        if (channel.isLeader) {
                           binding.isLeader.setImageResource(R.drawable.ic_crown_contest)
                           binding.isLeader.visibility = View.VISIBLE
                        } else {
                           binding.isLeader.visibility = View.GONE
                        }
                     }

                     ChannelType.Extracurricular.typeEng -> {
                        binding.card.setBackgroundResource(R.drawable.bg_channel_extracurricular_card)
                        binding.channelType.setBackgroundResource(R.drawable.bg_fd494a_radius_30)

                        if (channel.isLeader) {
                           binding.isLeader.setImageResource(R.drawable.ic_crown_activity)
                           binding.isLeader.visibility = View.VISIBLE
                        } else {
                           binding.isLeader.visibility = View.GONE
                        }
                     }

                     ChannelType.Study.typeEng -> {
                        binding.card.setBackgroundResource(R.drawable.bg_channel_study_card)
                        binding.channelType.setBackgroundResource(R.drawable.bg_34d676_radius_30)

                        if (channel.isLeader) {
                           binding.isLeader.setImageResource(R.drawable.ic_crown_study)
                           binding.isLeader.visibility = View.VISIBLE
                        } else {
                           binding.isLeader.visibility = View.GONE
                        }
                     }
                  }
                  */
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