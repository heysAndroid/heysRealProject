package com.example.heysrealprojcet.ui.main.category

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.CategoryItemViewBinding
import com.example.heysrealprojcet.model.ContestMain

class CategoryRecyclerViewAdapter(
   private val list: MutableList<ContestMain>,
   private val onclick: (item: String) -> Unit) :
   RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: CategoryItemViewBinding

   inner class ViewHolder(private val binding: CategoryItemViewBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: ContestMain) {
         binding.typeText.text = item.type
         binding.functionText.text = item.function
         binding.image.setImageResource(item.resId)
         binding.root.setOnClickListener { onclick.invoke(item.type) }

         // 텍스트 일부 스타일 적용
         var content = item.function
         var word = listOf("#개발", "마감", "많이찾는", "새로열린")

         var start = content.indexOf(word[position])
         var end = start + word[position].length

         val builder = SpannableStringBuilder(content)
         builder.setSpan(ForegroundColorSpan(Color.parseColor("#F7BC26")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
         binding.functionText.text = builder
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
}