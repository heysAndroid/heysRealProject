package com.example.heysrealprojcet.ui.main.content.study.hey.waiting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.WaitingUserItemViewBinding
import com.example.heysrealprojcet.model.UserProfile
import com.example.heysrealprojcet.ui.channel.profile.ProfileDiffCallback

class WaitingHeyItemRecyclerViewAdapter(private val userProfile: MutableList<UserProfile>, private val onClickListener: () -> Unit) :
   ListAdapter<UserProfile, WaitingHeyItemRecyclerViewAdapter.ViewHolder>(ProfileDiffCallback) {
   private lateinit var binding: WaitingUserItemViewBinding

   inner class ViewHolder(private val binding: WaitingUserItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(userProfile: UserProfile) {
         binding.name.text = userProfile.name
         binding.introduction.text = userProfile.introduction
         binding.profile.setImageResource(userProfile.imageResId)
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = WaitingUserItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(userProfile[position])
   }

   override fun getItemCount(): Int {
      return userProfile.size
   }
}