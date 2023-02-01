package com.example.heysrealprojcet.ui.main.category

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.CategoryItemViewBinding
import com.example.heysrealprojcet.model.ContestType

class CategoryRecyclerViewAdapter(
   private val list: MutableList<ContestType>,
   private val onclick: (item: String) -> Unit
): RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: CategoryItemViewBinding
   private var a = 0f

   init {
      setHasStableIds(true)
   }

   inner class ViewHolder(private val binding: CategoryItemViewBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: ContestType) {
         binding.type.text = item.type
         binding.image.setImageResource(item.resId)
         binding.root.setOnClickListener { onclick.invoke(item.type) }

         // 텍스트 일부 스타일 적용
         val content = item.description
         val word = listOf("#개발", "마감", "많이찾는", "새로열린")
         val start = content.indexOf(word[position])
         val end = start + word[position].length

         if (item.bool) {
            binding.itemView.alpha = 1F
            if (a != 0F) {
               binding.card.cardElevation = a
            }
         } else {
            binding.itemView.alpha = 0.3F
            if (binding.card.cardElevation != 0F) {
               a = binding.card.cardElevation
            }
            binding.card.cardElevation = 0F
         }

         val builder = SpannableStringBuilder(content)
         builder.setSpan(ForegroundColorSpan(Color.parseColor("#1BC47D")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
         binding.description.text = builder
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = CategoryItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(list[position])
   }

   override fun getItemCount(): Int {
      return list.size
   }

   override fun getItemId(position: Int): Long {
      return position.toLong()
   }
}