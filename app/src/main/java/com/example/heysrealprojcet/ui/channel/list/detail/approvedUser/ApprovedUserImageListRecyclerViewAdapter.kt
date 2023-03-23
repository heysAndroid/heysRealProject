package com.example.heysrealprojcet.ui.channel.list.detail.approvedUser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ApprovedUserImageItemViewBinding
import com.example.heysrealprojcet.model.ApprovedUserList

class ApprovedUserImageListRecyclerViewAdapter(
   private val user: MutableList<ApprovedUserList>?
) : RecyclerView.Adapter<ApprovedUserImageListRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: ApprovedUserImageItemViewBinding

   inner class ViewHolder(private val binding: ApprovedUserImageItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(user: ApprovedUserList) {
         when (user.percentage) {
            in 0..49 -> {
               binding.profile.setImageResource(R.drawable.ic_male_0)
            }
            in 50..99 -> {
               binding.profile.setImageResource(R.drawable.ic_male_50)
            }
            100 -> {
               binding.profile.setImageResource(R.drawable.ic_male_100)
            }
         }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = ApprovedUserImageItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      user?.get(position)?.let { holder.bind(it) }
   }

   override fun getItemCount(): Int {
      return user?.size ?: 0
   }
}