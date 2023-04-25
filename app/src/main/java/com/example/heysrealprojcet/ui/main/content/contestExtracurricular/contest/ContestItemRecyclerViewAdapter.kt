package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.contest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.heysrealprojcet.App
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ContestExtracurricularItemViewBinding
import com.example.heysrealprojcet.model.network.Content

class ContestItemRecyclerViewAdapter(
   private val contest: MutableList<Content>,
   private val onClickListener: () -> Unit) :
   RecyclerView.Adapter<ContestItemRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: ContestExtracurricularItemViewBinding

   inner class ViewHolder(private val binding: ContestExtracurricularItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(contest: Content) {
         binding.apply {
            title.text = contest.title
            company.text = contest.company
            viewCount.text = contest.viewCount.toString()
            channelCount.text = "${contest.channelCount}팀 빌딩"
            Glide.with(App.getInstance().applicationContext).load(contest.previewImgUri).into(binding.image)
         }

         when {
            contest.dday >= 7 -> {
               binding.dday.text = "D-${contest.dday}"
               binding.dday.setBackgroundResource(R.drawable.bg_34d676_radius_4)
            }

            1 <= contest.dday -> {
               binding.dday.text = "D-${contest.dday}"
               binding.dday.setBackgroundResource(R.drawable.bg_fd494a_radius_4)
            }

            else -> {
               binding.dday.text = "마감"
               binding.dday.setBackgroundResource(R.drawable.bg_e1e1e1_radius_4)
            }
         }
         binding.root.setOnClickListener { onClickListener.invoke() }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = ContestExtracurricularItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(contest[position])
   }

   override fun getItemCount(): Int {
      return contest.size
   }
}