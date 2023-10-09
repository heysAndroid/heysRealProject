package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.extracurricular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.heysrealprojcet.App
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ContestExtracurricularItemViewBinding
import com.example.heysrealprojcet.model.network.Content

class ExtracurricularItemRecyclerViewAdapter(
   private val content: MutableList<Content>,
   private val onClickListener: (Int) -> Unit) :
   RecyclerView.Adapter<ExtracurricularItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: ContestExtracurricularItemViewBinding

   inner class ViewHolder(private val binding: ContestExtracurricularItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(content: Content) {
         binding.apply {
            title.text = content.title
            company.text = content.company
            viewCount.text = content.viewCount.toString()
            channelCount.text = "${content.channelCount}팀 빌딩"
            dday.text = "D-${content.dday}"
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
         binding.root.setOnClickListener { onClickListener.invoke(content.id) }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = ContestExtracurricularItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(content[position])
   }

   override fun getItemCount(): Int {
      return content.size
   }
}