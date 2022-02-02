package com.example.heysrealprojcet.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.ContestItemViewBinding

class ContestRecyclerViewAdapter(private val type: MutableList<String>) : RecyclerView.Adapter<ContestRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: ContestItemViewBinding

   inner class ViewHolder(private val binding: ContestItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(type: String) {
         binding.typeText.text = type
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = ContestItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(type[position])
   }

   override fun getItemCount(): Int {
      return type.size
   }
}