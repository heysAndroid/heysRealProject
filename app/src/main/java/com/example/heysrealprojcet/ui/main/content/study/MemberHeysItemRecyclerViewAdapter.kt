package com.example.heysrealprojcet.ui.main.content.study

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.MemberHeysItemViewBinding
import com.example.heysrealprojcet.model.UserProfile
import com.example.heysrealprojcet.ui.channel.profile.ProfileDiffCallback

class MemberHeysItemRecyclerViewAdapter(private val userProfile: MutableList<UserProfile>, private val onClickListener: (UserProfile) -> Unit) :
   ListAdapter<UserProfile, MemberHeysItemRecyclerViewAdapter.ViewHolder>(ProfileDiffCallback) {
   private lateinit var binding: MemberHeysItemViewBinding

   inner class ViewHolder(private val binding: MemberHeysItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(userProfile: UserProfile) {
         binding.name.text = userProfile.name
         binding.goToProfile.setOnClickListener { onClickListener.invoke(userProfile) }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = MemberHeysItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(userProfile[position])
   }

   override fun getItemCount(): Int {
      return userProfile.size
   }
}