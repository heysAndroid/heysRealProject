package com.example.heysrealprojcet.ui.main.content.study

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.StudyItemViewBinding

class StudyItemRecyclerViewAdapter(
//   private val type: MutableList<CategoryViewData>,
   private val startDate: MutableList<Int>,
   private val onClickListener: () -> Unit) :
   RecyclerView.Adapter<StudyItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: StudyItemViewBinding

   inner class ViewHolder(private val binding: StudyItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(startDate: Int) {
         binding.startDate.text = "D-$startDate"

         binding.root.setOnClickListener { onClickListener.invoke() }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = StudyItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(startDate[position])

      if(startDate[position] == 0) {
         binding.startDate.text = "마감"
         var bgShape: GradientDrawable = binding.startDate.background as GradientDrawable
         bgShape.setColor(Color.parseColor("#828282"))
      }

      if(startDate[position] in 2..5) {
         var bgShape: GradientDrawable = binding.startDate.background as GradientDrawable
         bgShape.setColor(Color.parseColor("#53C740"))
      }

      if(startDate[position] in 6..10) {
         var bgShape: GradientDrawable = binding.startDate.background as GradientDrawable
         bgShape.setColor(Color.parseColor("#F7BC26"))
      }
   }

   override fun getItemCount(): Int {
      return startDate.size
   }
}