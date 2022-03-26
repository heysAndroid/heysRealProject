package com.example.heysrealprojcet.ui.main.content.contest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.ContestItemViewBinding

class ContestItemRecyclerViewAdapter(private val host: MutableList<String>) : RecyclerView.Adapter<ContestItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: ContestItemViewBinding

   inner class ViewHolder(private val binding: ContestItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(host: String) {
         binding.host.text = host
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = ContestItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(host[position])
   }

   override fun getItemCount(): Int {
      return host.size
   }
}