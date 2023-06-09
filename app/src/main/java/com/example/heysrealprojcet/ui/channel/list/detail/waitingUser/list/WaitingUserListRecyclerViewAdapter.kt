package com.example.heysrealprojcet.ui.channel.list.detail.waitingUser.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.WaitingUserItemViewBinding
import com.example.heysrealprojcet.enums.Gender
import com.example.heysrealprojcet.model.network.WaitingUserList

class WaitingUserListRecyclerViewAdapter(
   private val user: MutableList<WaitingUserList>?
) : RecyclerView.Adapter<WaitingUserListRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: WaitingUserItemViewBinding

   inner class ViewHolder(private val binding: WaitingUserItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(user: WaitingUserList) {
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

         // TODO
         // 유저 이름 표시
         binding.name.text = user.id.toString()
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = WaitingUserItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      user?.get(position)?.let { holder.bind(it) }
   }

   override fun getItemCount(): Int {
      return user?.size ?: 0
   }
}