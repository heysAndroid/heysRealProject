package com.example.heysrealprojcet.ui.main.content.study

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.StudyItemViewBinding

class StudyItemRecyclerViewAdapter(private val startDate: MutableList<String>, private val onClickListener: () -> Unit) : RecyclerView.Adapter<StudyItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: StudyItemViewBinding

   inner class ViewHolder(private val binding: StudyItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(startDate: String) {
         binding.startDate.text = startDate
         binding.root.setOnClickListener { onClickListener.invoke() }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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