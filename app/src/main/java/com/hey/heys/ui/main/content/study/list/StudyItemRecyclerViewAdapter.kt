package com.hey.heys.ui.main.content.study.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hey.heys.App
import com.hey.heys.R
import com.hey.heys.databinding.StudyItemViewBinding
import com.hey.heys.model.network.ChannelList

class StudyItemRecyclerViewAdapter(
   private val study: MutableList<ChannelList>,
   private val onClickListener: (Int) -> Unit) :
   RecyclerView.Adapter<StudyItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: StudyItemViewBinding

   inner class ViewHolder(private val binding: StudyItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(study: ChannelList) {
         binding.apply {
            tvTitle.text = study.name
            tvPastday.text = "개설한지 ${study.pastDay}일"
            tvView.text = "${study.viewCount}"

            Glide.with(com.hey.heys.App.getInstance().applicationContext)
               .load(study.thumbnailUri)
               .error(R.drawable.bg_thumbnail_default).into(imgThumbnail)
         }

         // 모집 마감일 지나지 않음
         if (study.dday >= 0) {
            when {
               // 최대 참여정원 4명 이상
               4 <= study.joinRemainCount -> {
                  binding.tvStatus.text = "참여가능"
                  binding.tvStatus.setBackgroundResource(R.drawable.bg_status_available)
               }

               1 <= study.joinRemainCount -> {
                  binding.tvStatus.text = "${study.joinRemainCount}명 참여가능"
                  binding.tvStatus.setBackgroundResource(R.drawable.bg_status_almost_closed)
               }

               else -> {
                  binding.tvStatus.text = "마감"
                  binding.tvStatus.setBackgroundResource(R.drawable.bg_status_closed)
               }
            }
         } else {
            binding.tvStatus.text = "마감"
            binding.tvStatus.setBackgroundResource(R.drawable.bg_status_closed)
         }

         binding.root.setOnClickListener { onClickListener.invoke(study.id) }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = StudyItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(study[position])
   }

   override fun getItemCount(): Int {
      return study.size
   }
}