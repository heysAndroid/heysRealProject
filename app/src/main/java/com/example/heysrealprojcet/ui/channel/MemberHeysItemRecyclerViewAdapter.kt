package com.example.heysrealprojcet.ui.channel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.MemberHeysItemViewBinding

class MemberHeysItemRecyclerViewAdapter(private val name: MutableList<String>, private val onClickListener: () -> Unit) :
   RecyclerView.Adapter<MemberHeysItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: MemberHeysItemViewBinding

   inner class ViewHolder(private val binding: MemberHeysItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(name: String) {
         binding.name.text = name
         binding.goToProfile.setOnClickListener { onClickListener.invoke() }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = MemberHeysItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(name[position])
   }

   override fun getItemCount(): Int {
      return name.size
   }
}