package com.example.heysrealprojcet.ui.channel.list.detail.waitingUser.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.WaitingUserItemViewBinding
import com.example.heysrealprojcet.model.network.ChannelFollower
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

class WaitingUserListRecyclerViewAdapter(
   private val isLeader: Boolean,
   private val listener: onClickListener
) : ListAdapter<ChannelFollower, WaitingUserListRecyclerViewAdapter.ViewHolder>(DiffCallback) {
   private lateinit var binding: WaitingUserItemViewBinding

   inner class ViewHolder(private val binding: WaitingUserItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(user: ChannelFollower) {
         /*
         when (user.percentage) {
            in 0..49 -> {
               when (user.gender) {
                  Gender.Male.genderEnglish -> binding.profile.setImageResource(R.drawable.ic_male_0)
                  Gender.Female.genderEnglish -> binding.profile.setImageResource(R.drawable.ic_female_0)
                  else -> binding.profile.setImageResource(R.drawable.ic_none_0)
               }

            }

            in 50..99 -> {
               when (user.gender) {
                  Gender.Male.genderEnglish -> binding.profile.setImageResource(R.drawable.ic_male_50)
                  Gender.Female.genderEnglish -> binding.profile.setImageResource(R.drawable.ic_female_50)
                  else -> binding.profile.setImageResource(R.drawable.ic_none_50)
               }
            }

            100 -> {
               when (user.gender) {
                  Gender.Male.genderEnglish -> binding.profile.setImageResource(R.drawable.ic_male_100)
                  Gender.Female.genderEnglish -> binding.profile.setImageResource(R.drawable.ic_female_100)
                  else -> binding.profile.setImageResource(R.drawable.ic_none_100)
               }
            }
         }
          */

         // 요청시간 표시
         val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
         val requestTime = LocalDateTime.parse(user.requestedAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
         val current = LocalDateTime.now()
         val compareDays = compareDay(current, requestTime)
         if (compareDays == 0) {
            val compareHour = compareHour(current, requestTime)
            binding.tvRequestDay.text = "요청한지 ${compareHour}시간"
         } else {
            binding.tvRequestDay.text = "요청한지 ${compareDays}일"
         }

         binding.tvName.text = user.username
         binding.tvProfile.setOnClickListener { listener.onClickProfile(user.id) }

         if (isLeader) {
            binding.llRejectAccept.visibility = View.VISIBLE
            binding.btnReject.setOnClickListener { listener.onClickReject(user.id) }
            binding.btnAccept.setOnClickListener { listener.onClickAccept(user.id) }
         } else {
            binding.llRejectAccept.visibility = View.GONE
         }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = WaitingUserItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(getItem(position))
   }

   private fun compareDay(date1: LocalDateTime, date2: LocalDateTime): Int {
      val dayDate1 = date1.truncatedTo(ChronoUnit.DAYS)
      val dayDate2 = date2.truncatedTo(ChronoUnit.DAYS)
      return dayDate1.compareTo(dayDate2)
   }

   private fun compareHour(date1: LocalDateTime, date2: LocalDateTime): Int {
      val dayDate1 = date1.truncatedTo(ChronoUnit.HOURS)
      val dayDate2 = date2.truncatedTo(ChronoUnit.HOURS)
      return dayDate1.compareTo(dayDate2)
   }

   private object DiffCallback : DiffUtil.ItemCallback<ChannelFollower>() {
      override fun areItemsTheSame(oldItem: ChannelFollower, newItem: ChannelFollower): Boolean {
         return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: ChannelFollower, newItem: ChannelFollower): Boolean {
         return oldItem == newItem
      }
   }

   interface onClickListener {
      fun onClickProfile(userId: Int)
      fun onClickReject(userId: Int)
      fun onClickAccept(userId: Int)
   }
}