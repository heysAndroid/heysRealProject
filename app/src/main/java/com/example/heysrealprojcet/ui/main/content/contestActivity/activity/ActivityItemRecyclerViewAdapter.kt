package com.example.heysrealprojcet.ui.main.content.contestActivity.activity

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.ContestActivityItemViewBinding
import com.example.heysrealprojcet.model.Activities

class ActivityItemRecyclerViewAdapter(
   private val host: MutableList<Activities>,
   private val onClickListener: () -> Unit) :
   RecyclerView.Adapter<ActivityItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: ContestActivityItemViewBinding

   inner class ViewHolder(private val binding: ContestActivityItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(type: Activities) {
         binding.startDate.text = "D-${type.startDate}"
         binding.image.setImageResource(type.image)
         binding.title.text = type.title
         binding.content.text = type.content
         binding.see.text = type.see.toString()

         var bgShape = binding.startDate.background as GradientDrawable
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