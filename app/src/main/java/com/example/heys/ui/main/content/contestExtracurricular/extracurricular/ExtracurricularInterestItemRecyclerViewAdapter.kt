package com.example.heys.ui.main.content.contestExtracurricular.extracurricular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heys.databinding.CategoryItemInterestViewBinding
import com.example.heys.model.ExtracurricularType

class ExtracurricularInterestItemRecyclerViewAdapter(
   private val list: MutableList<ExtracurricularType>,
   private val onclick: (item: String) -> Unit) :
   RecyclerView.Adapter<ExtracurricularInterestItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: CategoryItemInterestViewBinding

   init {
      setHasStableIds(true)
   }

   inner class ViewHolder(private val binding: CategoryItemInterestViewBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: ExtracurricularType) {
         binding.type.text = item.description
         binding.typeText2.text = item.type
         binding.image.setImageResource(item.resId)
         binding.root.setOnClickListener { onclick.invoke(item.order) }
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

   override fun getItemId(position: Int): Long {
      return position.toLong()
   }
}