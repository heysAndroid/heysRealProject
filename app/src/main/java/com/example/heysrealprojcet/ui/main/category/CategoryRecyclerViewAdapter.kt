package com.example.heysrealprojcet.ui.main.category

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.CategoryItemViewBinding
import com.example.heysrealprojcet.model.ContestType

class CategoryRecyclerViewAdapter(
   private val list: MutableList<ContestType>,
   private val myInterestList: MutableList<String>,
   private val onclick: (item: String) -> Unit
) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {
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

         val content = when (layoutPosition) {
            1 -> {
               "서두르세요!\n곧 마감하는 공모전이에요!"
            }
            2 -> {
               "와글와글\n많이찾는 공모전들이에요!"
            }
            3 -> {
               "새로운게 뭐가있나~\n새로열린 공모전들이에요!"
            }
            else -> {
               when (myInterestList.size) {
                  0 -> "관심분야 #${myInterestList}과\n 관련있는 공모전이에요!"
                  else -> "관심분야 #${myInterestList[0]} 외 ${myInterestList.size - 1}개와 관련있는 공모전이에요!"
               }
            }
         }

         // 특정 문자 색상 변경
         val word = mutableListOf(myInterestList[0], "마감", "많이찾는", "새로열린")

         val start = if (layoutPosition == 0) {
            content.indexOf(word[layoutPosition]) - 1
         } else {
            content.indexOf(word[layoutPosition])
         }

         val end = if (layoutPosition == 0 && myInterestList.size != 1) {
            start + word[layoutPosition].length + 6
         } else {
            start + word[layoutPosition].length
         }

         val builder = SpannableStringBuilder(content)
         builder.setSpan(
            ForegroundColorSpan(
               ContextCompat.getColor(binding.root.context, R.color.color_34d676)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
         binding.description.text = builder

         // 투명도 설정
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