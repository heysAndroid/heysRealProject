package com.example.heysrealprojcet.ui.main.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.CategoryItemViewBinding

class CategoryRecyclerViewAdapter(
   private val type: MutableList<String>,
   private val onclick: (item: String) -> Unit) :
   RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: CategoryItemViewBinding

   inner class ViewHolder(private val binding: CategoryItemViewBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: String) {
         binding.typeText.text = item
         binding.root.setOnClickListener { onclick.invoke(item) }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = CategoryItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(type[position])
   }

   override fun getItemCount(): Int {
      return type.size
   }
}