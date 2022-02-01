package com.example.heysrealprojcet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.CustomPagerItemViewBinding

// 틀은 같고 내용만 다르기때문에 CustomPagerItemView 로 틀을 만들어놓고
// adapter 에서 내용을 갈아끼워주는 느낌? 이에요!
// diffutil 은 달라진 부분만 변경하기위한 라이브러리인데, 데이터 개수가 많을때 유용하다고 해요.(지금은 자세히 몰라도 딱히 상관없는,,?!)
class CustomPagerAdapter : ListAdapter<Int, CustomPagerAdapter.CustomItemViewHolder>(CustomDiffCallback) {
   private lateinit var binding: CustomPagerItemViewBinding

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomItemViewHolder {
      binding = CustomPagerItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return CustomItemViewHolder(binding)
   }

   override fun onBindViewHolder(holder: CustomItemViewHolder, position: Int) {
      holder.bind(getItem(position))
   }

   inner class CustomItemViewHolder(private val binding: CustomPagerItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(resId: Int) {
         // 이미지 파일만 바꿔주기
         binding.image.setImageResource(resId)
      }
   }

   object CustomDiffCallback : DiffUtil.ItemCallback<Int>() {
      override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
         return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
         return oldItem == newItem
      }
   }
}