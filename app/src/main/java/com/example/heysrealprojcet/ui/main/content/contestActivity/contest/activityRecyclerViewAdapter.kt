package com.example.heysrealprojcet.ui.main.content.contestActivity.contest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.CategoryItemInterestViewBinding
import com.example.heysrealprojcet.model.ActivityMain

class activityRecyclerViewAdapter(
   private val list: MutableList<ActivityMain>,
   private val onclick: (item: String) -> Unit) :
   RecyclerView.Adapter<activityRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: CategoryItemInterestViewBinding

   inner class ViewHolder(private val binding: CategoryItemInterestViewBinding):
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: ActivityMain) {
         binding.typeText.text = item.type
         binding.typeText2.text = item.type2
         binding.image.setImageResource(item.resId)
         binding.root.setOnClickListener { onclick.invoke(item.type) }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = CategoryItemInterestViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(list[position])
   }

   override fun getItemCount(): Int {
      return list.size
   }
}