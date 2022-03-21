package com.example.heysrealprojcet.ui.main.content

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.StudyItemViewBinding

class StudyItemRecyclerViewAdapter(private val startDate: MutableList<String>) : RecyclerView.Adapter<StudyItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: StudyItemViewBinding

   inner class ViewHolder(private val binding: StudyItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(startDate: String) {
         binding.startDate.text = startDate
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyItemRecyclerViewAdapter.ViewHolder {
      binding = StudyItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(startDate[position])
   }

   override fun getItemCount(): Int {
      return startDate.size
   }
}