package com.example.heysrealprojcet.ui.main.content.study.list

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.StudyItemViewBinding
import com.example.heysrealprojcet.model.network.StudyList

class StudyItemRecyclerViewAdapter(
   private val study: MutableList<StudyList>,
   private val onClickListener: () -> Unit) :
   RecyclerView.Adapter<StudyItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: StudyItemViewBinding

   inner class ViewHolder(private val binding: StudyItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(study: StudyList) {
         //binding.startDate.text = "D-${type.startDate}"
         //binding.image.setImageResource(type.image)
         binding.title.text = study.name
         //binding.see.text = type.see.toString()

         var bgShape = binding.startDate.background as GradientDrawable
         /*
         if (type.startDate == 0) {
            binding.startDate.text = "마감"
            bgShape.setColor(Color.parseColor("#828282"))
         }

         if (type.startDate in 2..5) {
            bgShape.setColor(Color.parseColor("#53C740"))
         }

         if (type.startDate in 6..10) {
            bgShape.setColor(Color.parseColor("#F7BC26"))
         }
          */

         binding.root.setOnClickListener { onClickListener.invoke() }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = StudyItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(study[position])
   }

   override fun getItemCount(): Int {
      return study.size
   }
}