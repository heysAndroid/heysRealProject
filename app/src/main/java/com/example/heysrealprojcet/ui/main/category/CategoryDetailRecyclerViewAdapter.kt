package com.example.heysrealprojcet.ui.main.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.CategoryDetailItemViewBinding

class CategoryDetailRecyclerViewAdapter(private val type: MutableList<String>) : RecyclerView.Adapter<CategoryDetailRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: CategoryDetailItemViewBinding

   inner class ViewHolder(private val binding: CategoryDetailItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(type: String) {
         binding.typeText.text = type
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = CategoryDetailItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(type[position])
   }

   override fun getItemCount(): Int {
      return type.size
   }
}