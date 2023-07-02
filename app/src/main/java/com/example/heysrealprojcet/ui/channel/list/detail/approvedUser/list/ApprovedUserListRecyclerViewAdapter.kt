package com.example.heysrealprojcet.ui.channel.list.detail.approvedUser.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.ApprovedUserItemViewBinding
import com.example.heysrealprojcet.model.network.ChannelFollower

class ApprovedUserListRecyclerViewAdapter(
   private val onClick: (Int) -> Unit
) : ListAdapter<ChannelFollower, ApprovedUserListRecyclerViewAdapter.ViewHolder>(DiffCallback) {
   private lateinit var binding: ApprovedUserItemViewBinding

   inner class ViewHolder(private val binding: ApprovedUserItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
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
         binding.name.text = user.username
         binding.goToProfile.setOnClickListener { onClick.invoke(user.id) }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = ApprovedUserItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(getItem(position))
   }

   private object DiffCallback : DiffUtil.ItemCallback<ChannelFollower>() {
      override fun areItemsTheSame(oldItem: ChannelFollower, newItem: ChannelFollower): Boolean {
         return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: ChannelFollower, newItem: ChannelFollower): Boolean {
         return oldItem == newItem
      }
   }
}