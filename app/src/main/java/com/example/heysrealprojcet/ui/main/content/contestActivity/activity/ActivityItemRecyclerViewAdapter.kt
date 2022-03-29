package com.example.heysrealprojcet.ui.main.content.contestActivity.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.ContestActivityItemViewBinding

class ActivityItemRecyclerViewAdapter(private val host: MutableList<String>, private val onClickListener: () -> Unit) :
   RecyclerView.Adapter<ActivityItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: ContestActivityItemViewBinding

   inner class ViewHolder(private val binding: ContestActivityItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(host: String) {
         binding.host.text = host
         binding.root.setOnClickListener { onClickListener.invoke() }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = ContestActivityItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(host[position])
   }

   override fun getItemCount(): Int {
      return host.size
   }
}