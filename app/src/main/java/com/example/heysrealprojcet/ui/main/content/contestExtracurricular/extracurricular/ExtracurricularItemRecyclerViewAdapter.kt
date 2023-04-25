package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.extracurricular

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.ContestExtracurricularItemViewBinding
import com.example.heysrealprojcet.model.Extracurricular

class ExtracurricularItemRecyclerViewAdapter(
   private val host: MutableList<Extracurricular>,
   private val onClickListener: () -> Unit) :
   RecyclerView.Adapter<ExtracurricularItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: ContestExtracurricularItemViewBinding

   inner class ViewHolder(private val binding: ContestExtracurricularItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(type: Extracurricular) {
         binding.dday.text = "D-${type.startDate}"
         binding.image.setImageResource(type.image)
         binding.title.text = type.title

         binding.viewCount.text = type.see.toString()

         var bgShape = binding.dday.background as GradientDrawable
         if (type.startDate == 0) {
            binding.dday.text = "마감"
            bgShape.setColor(Color.parseColor("#828282"))
         }

         if (type.startDate in 2..5) {
            bgShape.setColor(Color.parseColor("#53C740"))
         }

         if (type.startDate in 6..10) {
            bgShape.setColor(Color.parseColor("#F7BC26"))
         }

         binding.root.setOnClickListener { onClickListener.invoke() }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = ContestExtracurricularItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(host[position])
   }

   override fun getItemCount(): Int {
      return host.size
   }
}