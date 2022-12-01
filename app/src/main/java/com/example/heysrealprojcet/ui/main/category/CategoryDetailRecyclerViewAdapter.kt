package com.example.heysrealprojcet.ui.main.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.CategoryDetailItemViewBinding
import kotlinx.coroutines.flow.MutableStateFlow

class CategoryDetailRecyclerViewAdapter(
   private val type: MutableList<Category>) :
   RecyclerView.Adapter<CategoryDetailRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: CategoryDetailItemViewBinding
   private var choiceImage: View? = null
   private val totalMax = 1
   private var total = MutableStateFlow(0)

   inner class ViewHolder(private val binding: CategoryDetailItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(type: Category) {
         binding.type.text = type.textFilter
         binding.typeImage.setImageResource(type.image)

         if (type.isSelected) {
            binding.typeImage.isSelected = true
            total.value += 1
            choiceImage = binding.typeImage
         }

         // 이미지 클릭 효과
         binding.typeImage.setOnClickListener {
            if (total.value < totalMax) {
               if (binding.typeImage.isSelected) {
                  binding.typeImage.isSelected = false
                  total.value -= 1
               } else {
                  binding.typeImage.isSelected = true
                  total.value += 1
                  choiceImage = binding.typeImage
               }
            } else {
               if (binding.typeImage.isSelected) {
                  binding.typeImage.isSelected = false
                  total.value -= 1
               } else {
                  choiceImage?.isSelected = false
                  binding.typeImage.isSelected = true
                  total.value += 1
                  choiceImage = binding.typeImage
               }
            }
         }
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