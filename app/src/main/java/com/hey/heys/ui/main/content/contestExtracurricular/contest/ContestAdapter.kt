package com.hey.heys.ui.main.content.contestExtracurricular.contest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hey.heys.App
import com.hey.heys.R
import com.hey.heys.databinding.ContestExtracurricularItemViewBinding
import com.hey.heys.model.network.Content

class ContestAdapter : PagingDataAdapter<Content, ContestAdapter.ViewHolder>(diffUtil) {
   var onClickListener: ((Int) -> Unit)? = null
   private lateinit var binding: ContestExtracurricularItemViewBinding

   inner class ViewHolder(private val binding: ContestExtracurricularItemViewBinding) : RecyclerView.ViewHolder
      (binding.root) {
      fun bind(content: Content) {
         binding.apply {
            title.text = content.title
            company.text = content.company
            viewCount.text = content.viewCount.toString()
            channelCount.text = "${content.channelCount}팀 빌딩"
            dday.text = "D-${content.dday}"
            image.clipToOutline = true
            Glide.with(App.getInstance().applicationContext).load(content.previewImgUri).error(R.drawable.bg_thumbnail_default).into(binding.image)
         }

         when {
            content.dday >= 7 -> {
               binding.dday.setBackgroundResource(R.drawable.bg_status_available)
            }

            1 <= content.dday -> {
               binding.dday.setBackgroundResource(R.drawable.bg_status_almost_closed)
            }

            // 마감 당일
            0 == content.dday -> {
               binding.dday.setBackgroundResource(R.drawable.bg_status_closed)
            }

            else -> {
               binding.dday.text = "마감"
               binding.dday.setBackgroundResource(R.drawable.bg_status_closed)
            }
         }
         binding.root.setOnClickListener { onClickListener?.invoke(content.id) }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = ContestExtracurricularItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val currentItem = getItem(position)
      currentItem?.let { holder.bind(it) }
   }

   companion object {
      val diffUtil = object : DiffUtil.ItemCallback<Content>() {
         override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem.id == newItem.id
         }

         override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem == newItem
         }
      }
   }
}