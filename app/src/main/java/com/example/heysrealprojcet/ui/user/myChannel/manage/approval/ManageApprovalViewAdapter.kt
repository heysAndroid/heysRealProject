package com.example.heysrealprojcet.ui.user.myChannel.manage.approval

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.ManageApprovalItemViewBinding
import com.example.heysrealprojcet.model.ApprovalList

class ManageApprovalViewAdapter(
   private val list: MutableList<ApprovalList>
   ) : RecyclerView.Adapter<ManageApprovalViewAdapter.ViewHolder>() {
   private lateinit var binding: ManageApprovalItemViewBinding

   inner class ViewHolder(private val binding: ManageApprovalItemViewBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: ApprovalList) {
         binding.image.setImageResource(item.image)
         binding.name.text = item.name
         binding.time.text = item.time
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = ManageApprovalItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(list[position])
   }

   override fun getItemCount(): Int = list.size
}